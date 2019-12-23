package com.bazzi.manager.service.impl;

import com.bazzi.common.ex.BusinessException;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.NotifyAnalysis;
import com.bazzi.common.generic.Page;
import com.bazzi.manager.config.DefinitionProperties;
import com.bazzi.manager.mapper.MonitorMapper;
import com.bazzi.manager.mapper.ProjectFieldMapper;
import com.bazzi.manager.mapper.ProjectHostMapper;
import com.bazzi.manager.mapper.ProjectMapper;
import com.bazzi.manager.model.*;
import com.bazzi.manager.service.NotifyService;
import com.bazzi.manager.service.ProjectService;
import com.bazzi.manager.service.RedisService;
import com.bazzi.manager.util.Constant;
import com.bazzi.manager.util.LogPatternUtil;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectHostMapper projectHostMapper;
    @Resource
    private ProjectFieldMapper projectFieldMapper;
    @Resource
    private MonitorMapper monitorMapper;

    @Resource
    private NotifyService notifyService;

    @Resource
    private RedisService redisService;

    @Resource
    private DefinitionProperties definitionProperties;

    public Page<ProjectVO> list(ProjectListReqVO projectListReqVO) {
        int pageIdx = projectListReqVO.getPageIdx();
        int pageSize = projectListReqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        List<Project> list = projectMapper.selectByParams(projectListReqVO);
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project : list) {
            ProjectVO projectVO = new ProjectVO();
            BeanUtils.copyProperties(project, projectVO);
            projectVOList.add(projectVO);
        }
        return Page.of(projectVOList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,
            rollbackFor = {BusinessException.class, Exception.class})
    public IdResponseVO add(ProjectReqVO projectReqVO) {
        //项目名查重
        Project p = new Project();
        p.setProjectName(projectReqVO.getProjectName());
        if (projectMapper.selectOne(p) != null) {
            throw new BusinessException(LogStatusCode.CODE_108);
        }

        //日志文件名查重
        Project p1 = new Project();
        p1.setLogFileName(projectReqVO.getLogFileName());
        if (projectMapper.selectOne(p1) != null) {
            throw new BusinessException(LogStatusCode.CODE_125);
        }

        User user = ThreadLocalUtil.getUser();
        Date createDate = new Date();
        String createUser = user.getUserName();

        BeanUtils.copyProperties(projectReqVO, p);
        p.setVersion(0);
        p.setCreateUser(createUser);
        p.setCreateTime(createDate);

        List<ProjectField> list = new ArrayList<>();
        //需要解析捕获正则
        if (p.getCaptureType() == 1) {
            p.setCaptureRegular(LogPatternUtil.getCaptureRegular(p.getLogRegular(), p.getDelimiter(), list));
        }
        projectMapper.insertUseGeneratedKeys(p);

        if (list.size() > 0) {//记录解析正则相关信息到project_field
            List<ProjectField> projectFieldList = new ArrayList<>();
            for (ProjectField projectField : list) {
                projectField.setProjectId(p.getId());
                projectField.setCreateUser(createUser);
                projectField.setCreateTime(createDate);
                projectFieldList.add(projectField);
            }
            projectFieldMapper.insertList(projectFieldList);
        }

        List<ProjectHostReqVO> hostList = projectReqVO.getHostList();
        List<ProjectHost> projectHostList = new ArrayList<>();
        //记录host相关信息到project_host
        for (ProjectHostReqVO projectHostReqVO : hostList) {
            ProjectHost projectHost = new ProjectHost();
            BeanUtils.copyProperties(projectHostReqVO, projectHost);
            projectHost.setProjectId(p.getId());
            projectHost.setCreateUser(createUser);
            projectHost.setCreateTime(createDate);
            projectHostList.add(projectHost);
        }
        projectHostMapper.insertList(projectHostList);

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(p.getId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofProject(p.getId()));

        invalidProjectCache(p.getId());
        return new IdResponseVO(p.getId());
    }

    public List<ProjectHostVO> findHostById(IdReqVO idReqVO) {
        ProjectHost ph = new ProjectHost();
        ph.setProjectId(idReqVO.getId());
        List<ProjectHost> selectList = projectHostMapper.select(ph);
        List<ProjectHostVO> list = new ArrayList<>();
        for (ProjectHost projectHost : selectList) {
            ProjectHostVO projectHostVO = new ProjectHostVO();
            BeanUtils.copyProperties(projectHost, projectHostVO);
            list.add(projectHostVO);
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,
            rollbackFor = {BusinessException.class, Exception.class})
    public StringResponseVO update(ProjectUpdateReqVO projectUpdateReqVO) {
        Project p = projectMapper.selectByPrimaryKey(projectUpdateReqVO.getId());
        if (p == null)
            throw new BusinessException(LogStatusCode.CODE_109);

        //日志文件名有修改时，需要进行查重
        String logFileName = projectUpdateReqVO.getLogFileName();
        if (!p.getLogFileName().equals(logFileName)) {
            Project p1 = new Project();
            p1.setLogFileName(logFileName);
            if (projectMapper.selectOne(p1) != null) {
                throw new BusinessException(LogStatusCode.CODE_125);
            }
        }

        User user = ThreadLocalUtil.getUser();
        Date updateDate = new Date();
        String updateUser = user.getUserName();

        BeanUtils.copyProperties(projectUpdateReqVO, p);
        p.setUpdateUser(updateUser);
        p.setUpdateTime(updateDate);

        List<ProjectField> list = new ArrayList<>();
        //需要解析捕获正则
        if (p.getCaptureType() == 1) {
            p.setCaptureRegular(LogPatternUtil.getCaptureRegular(p.getLogRegular(), p.getDelimiter(), list));
        }
        int i = projectMapper.update(p);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);

        if (list.size() > 0) {//记录解析正则相关信息到project_field，记录之前先删除历史记录
            List<ProjectField> projectFieldList = new ArrayList<>();
            for (ProjectField projectField : list) {
                projectField.setProjectId(p.getId());
                projectField.setCreateUser(updateUser);
                projectField.setCreateTime(updateDate);
                projectFieldList.add(projectField);
            }
            ProjectField pf = new ProjectField();
            pf.setProjectId(p.getId());
            projectFieldMapper.delete(pf);
            projectFieldMapper.insertList(projectFieldList);
        }

        List<ProjectHostReqVO> hostList = projectUpdateReqVO.getHostList();
        List<ProjectHost> projectHostList = new ArrayList<>();
        //记录host相关信息到project_host，记录之前先删除历史记录
        for (ProjectHostReqVO projectHostReqVO : hostList) {
            ProjectHost projectHost = new ProjectHost();
            BeanUtils.copyProperties(projectHostReqVO, projectHost);
            projectHost.setProjectId(p.getId());
            projectHost.setCreateUser(updateUser);
            projectHost.setCreateTime(updateDate);
            projectHostList.add(projectHost);
        }
        ProjectHost ph = new ProjectHost();
        ph.setProjectId(p.getId());
        projectHostMapper.delete(ph);
        projectHostMapper.insertList(projectHostList);

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(p.getId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofProject(p.getId()));

        invalidProjectCache(p.getId());
        return new StringResponseVO();
    }

    public StringResponseVO delete(IdReqVO idReqVO) {
        Integer id = idReqVO.getId();

        Monitor m = new Monitor();
        m.setProjectId(id);

        int i = monitorMapper.selectCount(m);
        if (i > 0)
            throw new BusinessException(LogStatusCode.CODE_124);

        Project p = projectMapper.selectByPrimaryKey(id);
        projectMapper.deleteByPrimaryKey(id);

        ProjectHost ph = new ProjectHost();
        ph.setProjectId(id);
        projectHostMapper.delete(ph);

        ProjectField pf = new ProjectField();
        pf.setProjectId(id);
        projectFieldMapper.delete(pf);

//        Monitor m = new Monitor();
//        m.setProjectId(id);
//        monitorMapper.delete(m);

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(p.getId(), p.getLogFileName()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofProject(p.getId()));

        invalidProjectCache(p.getId());
        return new StringResponseVO();
    }

    public List<ProjectSimpleVO> findAllProject() {
        List<ProjectSimpleVO> list = new ArrayList<>();
        List<Project> allList = projectMapper.selectAll();
        for (Project project : allList) {
            ProjectSimpleVO projectSimpleVO = new ProjectSimpleVO();
            BeanUtils.copyProperties(project, projectSimpleVO);
            list.add(projectSimpleVO);
        }
        return list;
    }

    public StringResponseVO updateStatus(StatusReqVO statusReqVO) {
        Project p = projectMapper.selectByPrimaryKey(statusReqVO.getId());
        if (p == null)
            throw new BusinessException(LogStatusCode.CODE_109);

        p.setStatus(statusReqVO.getStatus());
        p.setUpdateUser(ThreadLocalUtil.getUser().getUserName());
        p.setUpdateTime(new Date());
        int i = projectMapper.update(p);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);

        notifyService.sendNotifyAnalysis(new NotifyAnalysis(p.getId()));

        notifyService.sendNotifyAlarm(NotifyAlarm.ofProject(p.getId()));

        invalidProjectCache(p.getId());
        return new StringResponseVO();
    }

    public Project findByIdWithCache(Integer id) {
        String redisKey = String.format(Constant.PROJECT_CACHE_KEY,
                definitionProperties.getPermCachePrefix(), id);
        Project project = (Project) redisService.get(redisKey);

        if (project != null)
            return project;

        synchronized (redisKey.intern()) {
            project = (Project) redisService.get(redisKey);
            if (project != null)
                return project;

            project = projectMapper.selectByPrimaryKey(id);
            if (project == null) {
                project = new Project();
            }

            redisService.set(redisKey, project);
        }

        return project;
    }

    /**
     * 根据ID删除redis缓存里的项目信息
     *
     * @param id ID
     */
    private void invalidProjectCache(Integer id) {
        if (id == null || id <= 0)
            return;
        String redisKey = String.format(Constant.PROJECT_CACHE_KEY,
                definitionProperties.getPermCachePrefix(), id);
        redisService.delete(redisKey);
    }

}
