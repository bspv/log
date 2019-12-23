package com.bazzi.manager.service.impl;

import com.bazzi.common.generic.Page;
import com.bazzi.common.util.DateUtil;
import com.bazzi.manager.bean.StatisticalMonitorDO;
import com.bazzi.manager.bean.StatisticalProjectDO;
import com.bazzi.manager.mapper.StatisticalDetailMapper;
import com.bazzi.manager.mapper.StatisticalMapper;
import com.bazzi.manager.mapper.StatisticalMonitorMapper;
import com.bazzi.manager.mapper.StatisticalProjectMapper;
import com.bazzi.manager.model.Statistical;
import com.bazzi.manager.model.StatisticalDetail;
import com.bazzi.manager.service.StatisticalService;
import com.bazzi.manager.util.ByteConvertUtil;
import com.bazzi.manager.vo.request.TotalByDayReqVO;
import com.bazzi.manager.vo.request.TotalByHourReqVO;
import com.bazzi.manager.vo.request.TotalByMonitorReqVO;
import com.bazzi.manager.vo.request.TotalByProjectReqVO;
import com.bazzi.manager.vo.response.TotalByDayResponseVO;
import com.bazzi.manager.vo.response.TotalByHourResponseVO;
import com.bazzi.manager.vo.response.TotalByMonitorResponseVO;
import com.bazzi.manager.vo.response.TotalByProjectResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class StatisticalServiceImpl implements StatisticalService {

    @Resource
    private StatisticalMapper statisticalMapper;
    @Resource
    private StatisticalDetailMapper statisticalDetailMapper;
    @Resource
    private StatisticalProjectMapper statisticalProjectMapper;
    @Resource
    private StatisticalMonitorMapper statisticalMonitorMapper;

    public Page<TotalByDayResponseVO> listTotalByDay(TotalByDayReqVO reqVO) {
        int pageIdx = reqVO.getPageIdx();
        int pageSize = reqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        List<Statistical> list = statisticalMapper.selectByParams(reqVO);
        List<TotalByDayResponseVO> resultList = new ArrayList<>();
        for (Statistical statistical : list) {
            TotalByDayResponseVO totalByDayResponseVO = new TotalByDayResponseVO();
            BeanUtils.copyProperties(statistical, totalByDayResponseVO);
            totalByDayResponseVO.setTotalLogTraffic(ByteConvertUtil.byteToKB(statistical.getTotalLogTraffic() + ""));
            resultList.add(totalByDayResponseVO);
        }
        return Page.of(resultList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    public Page<TotalByHourResponseVO> listTotalByHour(TotalByHourReqVO reqVO) {
        int pageIdx = reqVO.getPageIdx();
        int pageSize = reqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        modifyTotalByHourReqVO(reqVO);
        List<StatisticalDetail> list = statisticalDetailMapper.selectByParams(reqVO);
        List<TotalByHourResponseVO> resultList = new ArrayList<>();
        for (StatisticalDetail statisticalDetail : list) {
            TotalByHourResponseVO totalByHourResponseVO = new TotalByHourResponseVO();
            BeanUtils.copyProperties(statisticalDetail, totalByHourResponseVO);
            totalByHourResponseVO.setTotalLogTraffic(ByteConvertUtil.byteToKB(statisticalDetail.getTotalLogTraffic() + ""));
            resultList.add(totalByHourResponseVO);
        }
        return Page.of(resultList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    public Page<TotalByProjectResponseVO> listTotalByProject(TotalByProjectReqVO reqVO) {
        int pageIdx = reqVO.getPageIdx();
        int pageSize = reqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        modifyTotalByProjectReqVO(reqVO);
        List<StatisticalProjectDO> list = statisticalProjectMapper.selectByParams(reqVO);
        List<TotalByProjectResponseVO> resultList = new ArrayList<>();
        for (StatisticalProjectDO statisticalProjectDO : list) {
            TotalByProjectResponseVO totalByProjectResponseVO = new TotalByProjectResponseVO();
            BeanUtils.copyProperties(statisticalProjectDO, totalByProjectResponseVO);
            totalByProjectResponseVO.setLogTraffic(ByteConvertUtil.byteToKB(statisticalProjectDO.getLogTraffic() + ""));
            resultList.add(totalByProjectResponseVO);
        }
        return Page.of(resultList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    public Page<TotalByMonitorResponseVO> listTotalByMonitor(TotalByMonitorReqVO reqVO) {
        int pageIdx = reqVO.getPageIdx();
        int pageSize = reqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        modifyTotalByMonitorReqVO(reqVO);
        List<StatisticalMonitorDO> list = statisticalMonitorMapper.selectByParams(reqVO);
        List<TotalByMonitorResponseVO> resultList = new ArrayList<>();
        for (StatisticalMonitorDO statisticalMonitorDO : list) {
            TotalByMonitorResponseVO totalByMonitorResponseVO = new TotalByMonitorResponseVO();
            BeanUtils.copyProperties(statisticalMonitorDO, totalByMonitorResponseVO);
            resultList.add(totalByMonitorResponseVO);
        }
        return Page.of(resultList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    /**
     * 将VO中的时间区间进行处理
     *
     * @param reqVO 查询条件
     */
    private void modifyTotalByHourReqVO(TotalByHourReqVO reqVO) {
        if (reqVO == null)
            return;
        String totalTimeStart = reqVO.getTotalTimeStart();
        if (totalTimeStart != null && !"".equals(totalTimeStart)) {
            reqVO.setTotalTimeStart(convertDate(totalTimeStart));
        }
        String totalTimeEnd = reqVO.getTotalTimeEnd();
        if (totalTimeEnd != null && !"".equals(totalTimeEnd)) {
            reqVO.setTotalTimeEnd(convertDate(totalTimeEnd));
        }
    }

    /**
     * 将VO中的时间区间进行处理
     *
     * @param reqVO 查询条件
     */
    private void modifyTotalByProjectReqVO(TotalByProjectReqVO reqVO) {
        if (reqVO == null)
            return;
        String totalTimeStart = reqVO.getTotalTimeStart();
        if (totalTimeStart != null && !"".equals(totalTimeStart)) {
            reqVO.setTotalTimeStart(convertDate(totalTimeStart));
        }
        String totalTimeEnd = reqVO.getTotalTimeEnd();
        if (totalTimeEnd != null && !"".equals(totalTimeEnd)) {
            reqVO.setTotalTimeEnd(convertDate(totalTimeEnd));
        }
    }

    /**
     * 将VO中的时间区间进行处理
     *
     * @param reqVO 查询条件
     */
    private void modifyTotalByMonitorReqVO(TotalByMonitorReqVO reqVO) {
        if (reqVO == null)
            return;
        String totalTimeStart = reqVO.getTotalTimeStart();
        if (totalTimeStart != null && !"".equals(totalTimeStart)) {
            reqVO.setTotalTimeStart(convertDate(totalTimeStart));
        }
        String totalTimeEnd = reqVO.getTotalTimeEnd();
        if (totalTimeEnd != null && !"".equals(totalTimeEnd)) {
            reqVO.setTotalTimeEnd(convertDate(totalTimeEnd));
        }
    }

    /**
     * 将"yyyy-MM-dd HH:00:00"转换为"yyyyMMddHH"格式字符串日期
     *
     * @param dateStr "yyyy-MM-dd HH:00:00"格式日期
     * @return yyyyMMddHH"格式日期
     */
    private String convertDate(String dateStr) {
        Date date = DateUtil.getDate(dateStr, DateUtil.DEFAULT_FORMAT);
        return DateUtil.formatDate(date, "yyyyMMdd") + DateUtil.getHH(date);
    }

}
