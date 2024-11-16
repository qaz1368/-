package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApprovalProcess;

public interface ApprovalProcessService extends IService<ApprovalProcess> {

    // 分页查询方法
    Page<ApprovalProcess> getPage(int pageNum, int pageSize);

    // 其他自定义的方法可以在这里添加
}
