package com.bazzi.manager.service.impl;

import com.bazzi.common.ex.BusinessException;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.NotifyAnalysis;
import com.bazzi.common.generic.Page;
import com.bazzi.manager.mapper.MonitorMapper;
import com.bazzi.manager.mapper.MonitorTmpMapper;
import com.bazzi.manager.model.Monitor;
import com.bazzi.manager.model.MonitorTmp;
import com.bazzi.manager.model.User;
import com.bazzi.manager.service.MonitorService;
import com.bazzi.manager.service.NotifyService;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.MonitorTmpVO;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.MonitorVO;
import com.bazzi.manager.vo.response.StringResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class MonitorServiceImpl implements MonitorService {
    @Resource
    private MonitorMapper monitorMapper;

    @Resource
    private MonitorTmpMapper monitorTmpMapper;

    @Resource
    private NotifyService notifyService;

    public Page<MonitorVO> list(MonitorListReqVO monitorListReqVO) {
        int pageIdx = monitorListReqVO.getPageIdx();
        int pageSize = monitorListReqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        List<Monitor> list = monitorMapper.selectByParams(monitorListReqVO);
        List<MonitorVO> monitorVOList = new ArrayList<>();
        for (Monitor monitor : list) {
            MonitorVO monitorVO = new MonitorVO();
            BeanUtils.copyProperties(monitor, monitorVO);
            if (monitor.getStrategyType() == 2) {
                monitorVO.setMonitorTmpVOList(getMonitorTmpList(monitor.getId()));
            }
            monitorVOList.add(monitorVO);
        }
        return Page.of(monitorVOList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,
            rollbackFor = {BusinessException.class, Exception.class})
    public IdResponseVO add(MonitorReqVO monitorReqVO) {
        Monitor m = new Monitor();
        m.setMonitorStrategy(monitorReqVO.getMonitorStrategy());
        if (monitorReqVO.getStrategyType() != 2 && monitorMapper.selectOne(m) != null) {
            throw new BusinessException(LogStatusCode.CODE_121);
        }
        Integer strategyType = monitorReqVO.getStrategyType();
        List<MonitorTmpVO> monitorTmpVOList = monitorReqVO.getMonitorTmpVOList();
        BeanUtils.copyProperties(monitorReqVO, m);
        buildMonitorRegular(strategyType, monitorReqVO.getMonitorStrategy(), monitorTmpVOList, m);
        User user = ThreadLocalUtil.getUser();
        m.setVersion(0);
        m.setCreateUser(user.getUserName());
        m.setCreateTime(new Date());
        monitorMapper.insertUseGeneratedKeys(m);

        insertMonitorTmp(strategyType, monitorTmpVOList, user, m.getId());

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(m.getProjectId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofMonitor(m.getId()));
        return new IdResponseVO(m.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,
            rollbackFor = {BusinessException.class, Exception.class})
    public StringResponseVO update(MonitorUpdateReqVO monitorUpdateReqVO) {
        Monitor m = monitorMapper.selectByPrimaryKey(monitorUpdateReqVO.getId());
        if (m == null) {
            throw new BusinessException(LogStatusCode.CODE_122);
        }
        BeanUtils.copyProperties(monitorUpdateReqVO, m);
        Integer strategyType = monitorUpdateReqVO.getStrategyType();
        List<MonitorTmpVO> monitorTmpVOList = monitorUpdateReqVO.getMonitorTmpVOList();
        buildMonitorRegular(strategyType, monitorUpdateReqVO.getMonitorStrategy(), monitorTmpVOList, m);
        if (strategyType == 2) {
            m.setMonitorStrategy("");
        }
        User user = ThreadLocalUtil.getUser();
        m.setUpdateUser(user.getUserName());
        m.setUpdateTime(new Date());
        int i = monitorMapper.update(m);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);

        MonitorTmp monitorTmp = new MonitorTmp();
        monitorTmp.setMonitorId(m.getId());
        monitorTmpMapper.delete(monitorTmp);

        insertMonitorTmp(strategyType, monitorTmpVOList, user, m.getId());

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(m.getProjectId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofMonitor(m.getId()));
        return new StringResponseVO();
    }

    public StringResponseVO delete(IdReqVO idReqVO) {
        Integer id = idReqVO.getId();
        Monitor monitor = monitorMapper.selectByPrimaryKey(id);
        monitorMapper.deleteByPrimaryKey(id);

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(monitor.getProjectId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofMonitor(monitor.getId()));
        return new StringResponseVO();
    }

    public StringResponseVO updateStatus(StatusReqVO statusReqVO) {
        Monitor m = monitorMapper.selectByPrimaryKey(statusReqVO.getId());
        if (m == null)
            throw new BusinessException(LogStatusCode.CODE_109);

        m.setStatus(statusReqVO.getStatus());
        m.setUpdateUser(ThreadLocalUtil.getUser().getUserName());
        m.setUpdateTime(new Date());
        int i = monitorMapper.update(m);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(m.getProjectId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofMonitor(m.getId()));
        return new StringResponseVO();
    }

    /**
     * 查询监控策略对应的复杂策略内容
     *
     * @param monitorId 监控策略id
     * @return 复杂策略内容
     */
    private List<MonitorTmpVO> getMonitorTmpList(int monitorId) {
        List<MonitorTmpVO> list = new ArrayList<>();
        MonitorTmp monitorTmp = new MonitorTmp();
        monitorTmp.setMonitorId(monitorId);
        List<MonitorTmp> monitorTmpList = monitorTmpMapper.select(monitorTmp);
        for (MonitorTmp tmp : monitorTmpList) {
            MonitorTmpVO monitorTmpVO = new MonitorTmpVO();
            BeanUtils.copyProperties(tmp, monitorTmpVO);
            list.add(monitorTmpVO);
        }
        return list;
    }

    /**
     * 记录明细
     *
     * @param strategyType     策略内容类型
     * @param monitorTmpVOList 复杂策略内容
     * @param user             用户
     * @param monitorId        监控策略id
     */
    private void insertMonitorTmp(Integer strategyType,
                                  List<MonitorTmpVO> monitorTmpVOList, User user, Integer monitorId) {
        if (strategyType == 2) {
            List<MonitorTmp> list = new ArrayList<>();
            for (MonitorTmpVO monitorTmpVO : monitorTmpVOList) {
                MonitorTmp monitorTmp = new MonitorTmp();
                BeanUtils.copyProperties(monitorTmpVO, monitorTmp);
                monitorTmp.setMonitorId(monitorId);
                monitorTmp.setCreateUser(user.getUserName());
                monitorTmp.setCreateTime(new Date());
                list.add(monitorTmp);
            }
            monitorTmpMapper.insertList(list);
        }
    }

    /**
     * 根据策略内容类型来获取匹配正则
     *
     * @param strategyType     策略内容类型
     * @param monitorStrategy  策略内容
     * @param monitorTmpVOList 复杂策略内容
     * @param m                监控策略
     */
    private void buildMonitorRegular(Integer strategyType, String monitorStrategy,
                                     List<MonitorTmpVO> monitorTmpVOList, Monitor m) {
        String monitorRegular = null;
        if (strategyType == 0) {
            monitorRegular = monitorStrategy;
        } else if (strategyType == 1) {
            monitorRegular = "^[\\S\\s]*" + monitorStrategy + "[\\S\\s]*$";
        } else if (strategyType == 2) {
            StringBuilder sb = new StringBuilder("^[\\S\\s]*");
            handleCombination(monitorTmpVOList, sb);
            sb.append("$");
            monitorRegular = sb.toString();
        }
        m.setMonitorRegular(monitorRegular);
    }

    /**
     * 处理字符串组合
     *
     * @param monitorTmpVOList 策略内容
     * @param sb               正则字符串
     */
    private void handleCombination(List<MonitorTmpVO> monitorTmpVOList, StringBuilder sb) {
        monitorTmpVOList.sort(Comparator.comparingInt(MonitorTmpVO::getIdx));
        for (MonitorTmpVO monitorTmpVO : monitorTmpVOList) {
            Integer type = monitorTmpVO.getTmpType();
            String content = monitorTmpVO.getContent();
            if (content == null || "".equals(content))
                throw new BusinessException(LogStatusCode.CODE_127);
            if (type != null && type == 1) {
                validContent(content);
                content = content.replace("{#}", "|");
                content = "(" + content + ")";
            }
            sb.append(content).append("[\\S\\s]*");
        }
    }

    /**
     * 校验content，必须包含{#}，个数必须在2-10之前，且每个元素不能为空
     *
     * @param content 策略内容
     */
    private void validContent(String content) {
        String[] split = content.split("\\{#}");
        if (split.length < 2 || split.length > 6) {
            throw new BusinessException(LogStatusCode.CODE_128);
        }
        for (String s : split) {
            if (s == null || "".equals(s.trim()))
                throw new BusinessException(LogStatusCode.CODE_129);
        }
    }

}
