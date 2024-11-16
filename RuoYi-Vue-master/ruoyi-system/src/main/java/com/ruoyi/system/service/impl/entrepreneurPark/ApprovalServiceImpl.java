package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.entity.Approval;
import com.ruoyi.system.mapper.entrepreneurPark.ApprovalMapper;
import com.ruoyi.system.service.entrepreneurPark.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {

    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public Page<Approval> getApprovalPage(int pageNum, int pageSize) {
        Page<Approval> page = new Page<>(pageNum, pageSize);
        return approvalMapper.selectPage(page, null);  // MyBatis-Plus 提供的分页查询
    }
}
