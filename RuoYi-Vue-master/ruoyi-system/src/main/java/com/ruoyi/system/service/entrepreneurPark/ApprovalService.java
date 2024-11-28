package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.DTO.ApprovalDTO;
import com.ruoyi.system.domain.entity.Approval;
import com.ruoyi.system.domain.vo.ApprovalVO;

import java.util.List;

public interface ApprovalService extends IService<Approval> {

    // 分页查询审批记录
    Page<ApprovalVO> getApprovalPage(int pageNum, int pageSize);

    List<Approval> list(QueryWrapper<Approval> queryWrapper);

    boolean updateById(Approval approval);

    Page<ApprovalVO> getPage1(int pageNum, int pageSize, LoginUser loginUser);

    boolean addApproval(ApprovalDTO approvalDTO);
}
