package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.entity.Approval;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationMapper;
import com.ruoyi.system.mapper.entrepreneurPark.ApprovalMapper;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import com.ruoyi.system.service.entrepreneurPark.ApprovalService;
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

    @Override
    public Page<Approval> getApprovalPage(int pageNum, int pageSize) {
        Page<Approval> page = new Page<>(pageNum, pageSize);
        return approvalMapper.selectPage(page, null);  // MyBatis-Plus 提供的分页查询
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
    public Page<Approval> getPage1(int pageNum, int pageSize, LoginUser loginUser) {
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

            Page<Approval> resultPage =approvalMapper.selectPage(page, queryWrapper1);

            // 返回分页结果
            return resultPage;
    }



}
