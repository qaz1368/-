package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.EmailUtil;
import com.ruoyi.system.domain.DTO.ApplicationDTO;
import com.ruoyi.system.domain.DTO.PassApplicationDTO;
import com.ruoyi.system.domain.entity.*;
import com.ruoyi.system.mapper.entrepreneurPark.*;
import com.ruoyi.system.service.entrepreneurPark.ApplicationService;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import com.ruoyi.system.service.entrepreneurPark.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApprovalProcessService approvalProcessService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ApplicationTypeMapper applicationTypeMapper;

    @Autowired
    private IndustryMapper industryMapper;

    @Autowired
    private ApprovalProcessMapper approvalProcessMapper;

    @Autowired
    private ApprovalMapper approvalMapper;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public List<Application> getPage(int pageNum, int pageSize, String applicantName, String applicantPhone) {
        PageHelper.startPage(pageNum, pageSize);
        List<Application> result = applicationMapper.selectPage(applicantName, applicantPhone);
        return result;
    }

   @Override
public Page<Application> getPage1(int pageNum, int pageSize, LoginUser loginUser, String applicantName, String applicantPhone) {
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

    if (processIds.isEmpty()) {
        // 如果 processIds 为空，直接返回一个空的分页结果
        return new Page<>();
    }
    PageHelper.startPage(pageNum, pageSize);
    Page<Application> resultPage = (Page<Application>) applicationMapper.selectPage1(page,processIds, applicantName, applicantPhone);
    return resultPage;

}

public boolean save(ApplicationDTO applicationDTO) {
        try {
            // 1. 通过 applicationDTO 的 applicationType 从 application_type 表中获取 application_type_id
            String applicationType = String.valueOf(applicationDTO.getApplicationType());
            ApplicationType applicationTypeRecord = applicationTypeMapper.selectByApplicationType(applicationType);
            if (applicationTypeRecord == null) {
                throw new RuntimeException("无法找到对应的申请类型");
            }
            Integer applicationTypeId = applicationTypeRecord.getApplicationTypeId();

            // 2. 通过 applicationDTO 的 industry 从 industry 表中获取 industry_id
            String industryName = String.valueOf(applicationDTO.getIndustryName());
            Industry industryRecord = industryMapper.selectByIndustry(industryName);
            if (industryRecord == null) {
                throw new RuntimeException("无法找到对应的行业");
            }
            Integer industryId = industryRecord.getIndustryId();

            // 3. 通过 application_type_id 获取 approval_process 表里流程步骤顺序为 1 的那行数据
            QueryWrapper<ApprovalProcess> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("application_type_id", applicationTypeId)
                        .eq("step_order", 1);
            ApprovalProcess approvalProcessRecord = approvalProcessMapper.selectOne(queryWrapper);
            if (approvalProcessRecord == null) {
                throw new RuntimeException("无法找到对应的审批流程");
            }
            Integer processId = approvalProcessRecord.getProcessId();

            // 4. 结合所有数据字段插入 application 表中，状态为 'pending'
            Application application = new Application();
            application.setApplicationTypeId(applicationTypeId);
            application.setIndustryId(industryId);
            application.setProcessId(processId);
            application.setStatus("pending");
            application.setCreatedAt(new Date());
            application.setUpdatedAt(new Date());
            application.setEnterpriseDescription(applicationDTO.getEnterpriseDescription());
            application.setApplicantName(applicationDTO.getApplicantName());
            application.setApplicantPhone(applicationDTO.getApplicantPhone());
            application.setApplicantEmail(applicationDTO.getApplicantEmail());
            application.setApplicationDate(new Date());
            // 其他字段根据需要设置
            boolean applicationInsertResult = save(application);
            if (!applicationInsertResult) {
                throw new RuntimeException("申请插入失败");
            }
            Integer applicationId = application.getApplicationId();

            // 5. 插入成功后，将数据插入 approval 表中，审批状态为 2
            Approval approval = new Approval();
            approval.setApplicationId(applicationId);
            approval.setProcessId(processId);
            approval.setSequence(1);
            approval.setDepartmentId(approvalProcessRecord.getDepartmentId());
            approval.setApprovalStatus(String.valueOf(2));
            approval.setCreatedAt(LocalDateTime.now());
            approval.setUpdatedAt(LocalDateTime.now());
            approval.setComments("");
            approval.setApprovalDate(LocalDateTime.now());

            int approvalInsertResult = approvalMapper.insert(approval);
            if (approvalInsertResult <= 0) {
                throw new RuntimeException("审批插入失败");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 通过申请
    public void approveApplication(PassApplicationDTO passApplicationDTO) {
        Integer applicationId = passApplicationDTO.getApplicationId();
        String reason = passApplicationDTO.getReason();
        Application application = applicationMapper.findApplicationById(applicationId);
        if (application != null && "pending".equals(application.getStatus())) {
            Integer processId = application.getProcessId();
            Integer applicationTypeId = application.getApplicationTypeId();

            // 通过 process_id 查找 approval_process 表的对应数据的流程步骤顺序
            ApprovalProcess currentProcessStep = approvalProcessService.getApprovalProcessByProcessId(processId);
            if (currentProcessStep == null) {
                throw new RuntimeException("无法找到对应的审批流程");
            }
            Integer currentStepOrder = currentProcessStep.getStepOrder();

            // 通过 application 里的申请类型 id 获取 approval_process 表相同申请类型 id 的流程步骤最大的值
            Integer maxStepOrder = approvalProcessService.getMaxStepOrderByApplicationTypeId(applicationTypeId);

            // 比较两个流程步骤顺序
            if (currentStepOrder.equals(maxStepOrder)) {
                // 更新 application 表的申请状态为 "approved"
                applicationMapper.updateApplicationStatus(applicationId, processId, "approved", new Date());

                // 修改 approval 表的审批状态为 '1'
                updateApprovalStatus(applicationId, 1);

                // 修改流程 id、流程顺序、审批部门 ID
                updateApprovalDetails(applicationId, currentProcessStep);

                // 发送通过通知邮件
                String to = application.getApplicantEmail();
                if (to == null || to.isEmpty()) {
                    throw new RuntimeException("申请人邮箱地址为空");
                }
                String title = "申请已通过";

                emailUtil.sendMessage(to, title, reason);

            } else {
                // 查找下一个流程步骤
                QueryWrapper<ApprovalProcess> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("application_type_id", applicationTypeId)
                        .eq("step_order", currentStepOrder + 1);

                ApprovalProcess nextProcessStep = approvalProcessService.getOne(queryWrapper);
                if (nextProcessStep == null) {
                    throw new RuntimeException("无法找到下一个审批流程步骤");
                }

                // 更新 application 表的申请状态为 "pending"
                applicationMapper.updateApplicationStatus(applicationId, nextProcessStep.getProcessId(), "pending", new Date());

                // 修改 approval 表的审批状态为 '2'
                updateApprovalStatus(applicationId, 2);

                // 修改流程 id、流程顺序、审批部门 ID
                updateApprovalDetails(applicationId, nextProcessStep);

            }
        }
    }


    // 拒绝申请
    public void rejectApplication(PassApplicationDTO passApplicationDTO) {
        Integer applicationId = passApplicationDTO.getApplicationId();
        String reason = passApplicationDTO.getReason();
        Application application = applicationMapper.findApplicationById(applicationId);
        if (application != null && "pending".equals(application.getStatus())) {
            Integer processId = application.getProcessId();

            // 修改 application 表的申请状态
            applicationMapper.updateApplicationStatus(applicationId, processId, "rejected", new Date());

            // 通过 process_id 查找 approval_process 表的对应数据的流程步骤顺序
            ApprovalProcess currentProcessStep = approvalProcessService.getApprovalProcessByProcessId(processId);
            if (currentProcessStep == null) {
                throw new RuntimeException("无法找到对应的审批流程");
            }

            // 修改 approval 表的审批状态为 '2'
            updateApprovalStatus(applicationId, 0);

            // 修改流程 id、流程顺序、审批部门 ID
            updateApprovalDetails(applicationId, currentProcessStep);

            // 发送拒绝通知邮件
            String to = application.getApplicantEmail();

            if (to == null || to.isEmpty()) {
                throw new RuntimeException("申请人邮箱地址为空");
            }
            String title = "申请被拒绝";

            emailUtil.sendMessage(to, title, reason);
        }
    }




    // 修改 approval 表的审批状态和其他相关信息
    private void updateApprovalStatus(Integer applicationId, int status) {
        QueryWrapper<Approval> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_id", applicationId);

        List<Approval> approvals = approvalService.list(queryWrapper);
        if (!approvals.isEmpty()) {
            for (Approval approval : approvals) {
                approval.setApprovalStatus(String.valueOf(status));
                approval.setUpdatedAt(LocalDateTime.now());
                approval.setApprovalDate(LocalDateTime.now());
                approvalService.updateById(approval);
            }
        }
    }

    // 修改 approval 表的流程 id、流程顺序、审批部门 ID
    private void updateApprovalDetails(Integer applicationId, ApprovalProcess currentProcessStep) {
        QueryWrapper<Approval> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_id", applicationId);

        List<Approval> approvals = approvalService.list(queryWrapper);
        if (!approvals.isEmpty()) {
            for (Approval approval : approvals) {
                approval.setProcessId(currentProcessStep.getProcessId());
                approval.setApprovalStatus(String.valueOf(currentProcessStep.getStepOrder()));
                approval.setDepartmentId(currentProcessStep.getDepartmentId());
                approvalService.updateById(approval);
            }
        }
    }




}
