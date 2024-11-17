package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApprovalProcess;

import java.util.List;

public interface ApprovalProcessService extends IService<ApprovalProcess> {

    // 分页查询方法
    Page<ApprovalProcess> getPage(int pageNum, int pageSize);

    List<ApprovalProcess> list(QueryWrapper<ApprovalProcess> queryWrapper);

    ApprovalProcess getApprovalProcessByProcessId(Integer processId);

    Integer getMaxStepOrderByApplicationTypeId(Integer applicationTypeId);

}
