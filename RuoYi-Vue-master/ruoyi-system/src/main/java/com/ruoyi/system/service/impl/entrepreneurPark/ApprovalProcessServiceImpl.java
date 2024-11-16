package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.mapper.entrepreneurPark.ApprovalProcessMapper;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 其他 CRUD 方法可以添加
}
