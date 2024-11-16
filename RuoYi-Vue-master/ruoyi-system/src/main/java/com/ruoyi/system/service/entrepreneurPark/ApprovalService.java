package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.Approval;

public interface ApprovalService extends IService<Approval> {

    // 分页查询审批记录
    Page<Approval> getApprovalPage(int pageNum, int pageSize);
}
