package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.entity.Application;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.domain.entity.Approval;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.domain.vo.ApprovalVO;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationMapper;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationTypeMapper;
import com.ruoyi.system.mapper.entrepreneurPark.ApprovalMapper;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import com.ruoyi.system.service.entrepreneurPark.ApprovalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {

    @Autowired
    private ApprovalMapper approvalMapper;

    @Autowired
    private ApprovalProcessService approvalProcessService;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationTypeMapper applicationTypeMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;


    @Override
    public Page<ApprovalVO> getApprovalPage(int pageNum, int pageSize) {
        // 分页查询审批记录
        Page<Approval> page = new Page<>(pageNum, pageSize);
        Page<Approval> approvalPage = approvalMapper.selectPage(page, null);

        // 将查询结果转换为 ApprovalVO 列表
        List<ApprovalVO> approvalVOList = approvalPage.getRecords().stream().map(approval -> {
            ApprovalVO approvalVO = new ApprovalVO();
            BeanUtils.copyProperties(approval, approvalVO);
            // 根据 application_id 获取 applicant_name 和 application_type_id
            Application application = applicationMapper.selectById(approval.getApplicationId());
            if (application != null) {
                approvalVO.setApplicantName(application.getApplicantName());
                approvalVO.setApplicationId(application.getApplicationTypeId());

                // 根据 application_type_id 获取 application_name
                ApplicationType applicationType = applicationTypeMapper.selectById(application.getApplicationTypeId());
                if (applicationType != null) {
                    approvalVO.setApplicationType(applicationType.getApplicationName());
                }
            }

            if (approval.getDepartmentId() != null) {
                SysDept department = sysDeptMapper.selectByIds(Long.valueOf(approval.getDepartmentId()));
                if (department != null) {
                    approvalVO.setDepartment(department.getDeptName());
                }
            }

            return approvalVO;
        }).collect(Collectors.toList());

        // 创建一个新的 Page 对象，将转换后的 ApprovalVO 列表放入
        Page<ApprovalVO> approvalVOPage = new Page<>(approvalPage.getCurrent(), approvalPage.getSize(), approvalPage.getTotal());
        approvalVOPage.setRecords(approvalVOList);

        return approvalVOPage;
    }

    @Override
    public List<Approval> list(QueryWrapper<Approval> queryWrapper) {
        return approvalMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateById(Approval approval) {
        return approvalMapper.updateById(approval) > 0;
    }

    @Override
    public Page<ApprovalVO> getPage1(int pageNum, int pageSize, LoginUser loginUser) {
            Page<Approval> page = new Page<>(pageNum, pageSize);
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

            if (processIds.isEmpty()) {
                // 如果 processIds 为空，直接返回一个空的分页结果
                return new Page<>();
            }

            // 构建查询条件
            QueryWrapper<Approval> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.in("process_id", processIds);

            Page<Approval> approvalPage =approvalMapper.selectPage(page, queryWrapper1);

        // 将查询结果转换为 ApprovalVO 列表
        List<ApprovalVO> approvalVOList = approvalPage.getRecords().stream().map(approval -> {
            ApprovalVO approvalVO = new ApprovalVO();
            BeanUtils.copyProperties(approval, approvalVO);
            // 根据 application_id 获取 applicant_name 和 application_type_id
            Application application = applicationMapper.selectById(approval.getApplicationId());
            if (application != null) {
                approvalVO.setApplicantName(application.getApplicantName());
                approvalVO.setApplicationId(application.getApplicationTypeId());

                // 根据 application_type_id 获取 application_name
                ApplicationType applicationType = applicationTypeMapper.selectById(application.getApplicationTypeId());
                if (applicationType != null) {
                    approvalVO.setApplicationType(applicationType.getApplicationName());
                }
            }

            if (approval.getDepartmentId() != null) {
                SysDept department = sysDeptMapper.selectByIds(Long.valueOf(approval.getDepartmentId()));
                if (department != null) {
                    approvalVO.setDepartment(department.getDeptName());
                }
            }

            return approvalVO;
        }).collect(Collectors.toList());

        // 创建一个新的 Page 对象，将转换后的 ApprovalVO 列表放入
        Page<ApprovalVO> approvalVOPage = new Page<>(approvalPage.getCurrent(), approvalPage.getSize(), approvalPage.getTotal());
        approvalVOPage.setRecords(approvalVOList);

        return approvalVOPage;

    }



}
