package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.entity.Application;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationMapper;
import com.ruoyi.system.service.entrepreneurPark.ApplicationService;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApprovalProcessService approvalProcessService;


    @Override
    public Page<Application> getPage(int pageNum, int pageSize) {
        // 创建分页对象
        Page<Application> page = new Page<>(pageNum, pageSize);
        return applicationMapper.selectPage(page, null);  // 查询所有记录
    }

    @Override
    public Page<Application> getPage1(int pageNum, int pageSize, LoginUser loginUser) {

        Page<Application> page = new Page<>(pageNum, pageSize);
        Long deptId = loginUser.getDeptId();
        // 构造查询条件
        QueryWrapper<ApprovalProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", deptId);

        // 执行查询
        List<ApprovalProcess> approvalProcesses = approvalProcessService.list(queryWrapper);

          // 获取所有审批流程的 id
        List<Integer> processIds = approvalProcesses.stream()
                .map(ApprovalProcess::getProcessId)
                .collect(Collectors.toList());
         // 构建查询条件
        QueryWrapper<Application> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("process_id", processIds);

        Page<Application> resultPage = applicationMapper.selectPage(page, queryWrapper1);

        // 返回分页结果
        return resultPage;
    }

    @Override
    public boolean save(Application application) {
//        application.setProcessId(2);
//        application.setApplicationTypeId(2);
//        application.setIndustryId(3);
//        applicationMapper.save(application);
        return false;
    }
}
