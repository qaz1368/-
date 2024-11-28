package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.DTO.ApprovalProcessDTO;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.domain.vo.ApprovalProcessVO;

import java.util.List;

public interface ApprovalProcessService extends IService<ApprovalProcess> {


    List<ApprovalProcess> list(QueryWrapper<ApprovalProcess> queryWrapper);

    ApprovalProcess getApprovalProcessByProcessId(Integer processId);
    ApprovalProcess getApprovalProcessByProcessId2(Integer processId);

    Integer getMaxStepOrderByApplicationTypeId(Integer applicationTypeId);

    Page<ApprovalProcessVO> getApprovalProcessPage(int pageNum, int pageSize);

    boolean addApprovalProcess(ApprovalProcessDTO approvalProcessDTO);

    boolean updateApprovalProcess(ApprovalProcessDTO approvalProcessDTO);

    ApprovalProcessVO getApprovalProcessById(Long processId);
}
