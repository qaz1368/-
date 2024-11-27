package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.DTO.ApprovalProcessDTO;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.vo.ApprovalProcessVO;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.entrepreneurPark.ApprovalProcessMapper;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import com.ruoyi.system.service.impl.SysDeptServiceImpl;
import com.ruoyi.system.service.impl.SysRoleServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalProcessServiceImpl extends ServiceImpl<ApprovalProcessMapper, ApprovalProcess> implements ApprovalProcessService {

    @Autowired
    private ApprovalProcessMapper approvalProcessMapper;
    @Autowired
    private ApplicationTypeServiceImpl applicationTypeService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Page<ApprovalProcessVO> getApprovalProcessPage(int pageNum, int pageSize) {
        // 构建分页对象
        Page<ApprovalProcess> page = new Page<>(pageNum, pageSize);
        Page<ApprovalProcess> approvalProcessPage = approvalProcessMapper.selectPage(page, null);  // 查询所有记录

        List<ApprovalProcess> approvalProcesses = approvalProcessPage.getRecords();

        List<ApprovalProcessVO> approvalProcessVOS = approvalProcesses.stream().map(approvalProcess -> {
            ApprovalProcessVO vo = new ApprovalProcessVO();
            BeanUtils.copyProperties(approvalProcess, vo);

            ApplicationType applicationType = applicationTypeService.getById(approvalProcess.getApplicationTypeId());
            if (applicationType != null) {
                vo.setApplicationType(applicationType.getApplicationName());
            }

            SysDept sysDept = sysDeptMapper.selectDeptById(Long.valueOf(approvalProcess.getDepartmentId()));  // 使用 SysDeptMapper 查询
            if (sysDept != null) {
                vo.setDepartment(sysDept.getDeptName());
            }

            SysRole sysRole = sysRoleMapper.selectRoleById(Long.valueOf(approvalProcess.getRoleId()));  // 使用 SysRoleMapper 查询
            if (sysRole != null) {
                vo.setRole(sysRole.getRoleName());
            }

            return vo;
        }).collect(Collectors.toList());

        Page<ApprovalProcessVO> resultPage = new Page<>(pageNum, pageSize, approvalProcessPage.getTotal());
        resultPage.setRecords(approvalProcessVOS);

        return resultPage;
    }

    @Override
    public boolean addApprovalProcess(ApprovalProcessDTO approvalProcessDTO) {
        if (approvalProcessDTO != null) {
            ApprovalProcess approvalProcess = new ApprovalProcess();
            BeanUtils.copyProperties(approvalProcessDTO, approvalProcess);
            approvalProcess.setCreatedAt(LocalDateTime.now());
            approvalProcess.setUpdatedAt(LocalDateTime.now());

            QueryWrapper<ApplicationType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("application_name", approvalProcessDTO.getApplicationType());
            ApplicationType applicationType = applicationTypeService.getOne(queryWrapper);
            if (applicationType != null) {
                approvalProcess.setApplicationTypeId(applicationType.getApplicationTypeId());
            }

            if (approvalProcessDTO.getRole() != null){
                SysRole sysRole = sysRoleMapper.getRoleByName(approvalProcessDTO.getRole());
                if (sysRole != null) {
                    approvalProcess.setRoleId(Math.toIntExact(sysRole.getRoleId()));
                }
            }

            if (approvalProcessDTO.getDepartment() != null){
                SysDept sysDept = sysDeptMapper.getDeptByName(approvalProcessDTO.getDepartment());
                if (sysDept != null) {
                    approvalProcess.setDepartmentId(Math.toIntExact(sysDept.getDeptId()));;
                }
            }
            return save(approvalProcess);
        }
        return false;
    }

    @Override
    public boolean updateApprovalProcess(ApprovalProcessDTO approvalProcessDTO) {
        if (approvalProcessDTO != null) {
            ApprovalProcess approvalProcess = new ApprovalProcess();
            BeanUtils.copyProperties(approvalProcessDTO, approvalProcess);
            approvalProcess.setUpdatedAt(LocalDateTime.now());

            QueryWrapper<ApplicationType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("application_name", approvalProcessDTO.getApplicationType());
            ApplicationType applicationType = applicationTypeService.getOne(queryWrapper);
            if (applicationType != null) {
                approvalProcess.setApplicationTypeId(applicationType.getApplicationTypeId());
            }

            if (approvalProcessDTO.getRole() != null){
                SysRole sysRole = sysRoleMapper.getRoleByName(approvalProcessDTO.getRole());
                if (sysRole != null) {
                    approvalProcess.setRoleId(Math.toIntExact(sysRole.getRoleId()));
                }
            }

            if (approvalProcessDTO.getDepartment() != null){
                SysDept sysDept = sysDeptMapper.getDeptByName(approvalProcessDTO.getDepartment());
                if (sysDept != null) {
                    approvalProcess.setDepartmentId(Math.toIntExact(sysDept.getDeptId()));;
                }
            }
            return updateById(approvalProcess);
        }
        return false;
    }

    @Override
    public ApprovalProcessVO getApprovalProcessById(Long processId) {
        ApprovalProcess approvalProcess = baseMapper.selectById(processId);
        if (approvalProcess != null) {
            ApprovalProcessVO vo = new ApprovalProcessVO();
            BeanUtils.copyProperties(approvalProcess, vo);
            ApplicationType applicationType = applicationTypeService.getById(approvalProcess.getApplicationTypeId());
            if (applicationType != null) {
                vo.setApplicationType(applicationType.getApplicationName());
            }
            SysDept sysDept = sysDeptMapper.selectDeptById(Long.valueOf(approvalProcess.getDepartmentId()));
            if (sysDept != null) {
                vo.setDepartment(sysDept.getDeptName());
            }
            SysRole sysRole = sysRoleMapper.selectRoleById(Long.valueOf(approvalProcess.getRoleId()));
            if (sysRole != null) {
                vo.setRole(sysRole.getRoleName());
            }
            return vo;

        }
        return null;
    }

    @Override
    public List<ApprovalProcess> list(QueryWrapper<ApprovalProcess> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ApprovalProcess getApprovalProcessByProcessId(Integer processId) {
        return baseMapper.selectById(processId);
    }
    @Override
    public ApprovalProcess getApprovalProcessByProcessId2(Integer processId) {
        return baseMapper.selectById(processId);
    }


    @Override
    public Integer getMaxStepOrderByApplicationTypeId(Integer applicationTypeId) {
        QueryWrapper<ApprovalProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_type_id", applicationTypeId);
        queryWrapper.orderByDesc("step_order");
        List<ApprovalProcess> approvalProcesses = baseMapper.selectList(queryWrapper);
        if (approvalProcesses.isEmpty()) {
            throw new RuntimeException("无法找到对应的审批流程");
        }
        return approvalProcesses.get(0).getStepOrder();
    }

}
