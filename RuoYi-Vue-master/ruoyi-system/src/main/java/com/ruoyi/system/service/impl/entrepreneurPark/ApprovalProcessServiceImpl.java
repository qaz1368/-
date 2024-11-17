package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.mapper.entrepreneurPark.ApprovalProcessMapper;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalProcessServiceImpl extends ServiceImpl<ApprovalProcessMapper, ApprovalProcess> implements ApprovalProcessService {

    @Autowired
    private ApprovalProcessMapper approvalProcessMapper;

    @Override
    public Page<ApprovalProcess> getPage(int pageNum, int pageSize) {
        // 构建分页对象
        Page<ApprovalProcess> page = new Page<>(pageNum, pageSize);
        return approvalProcessMapper.selectPage(page, null);  // 查询所有记录
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
