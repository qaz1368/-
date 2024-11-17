package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.Approval;

import java.util.List;

public interface ApprovalService extends IService<Approval> {

    // 分页查询审批记录
    Page<Approval> getApprovalPage(int pageNum, int pageSize);

    List<Approval> list(QueryWrapper<Approval> queryWrapper);

    boolean updateById(Approval approval);

}
