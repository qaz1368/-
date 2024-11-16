package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.Application;

public interface ApplicationService extends IService<Application> {

    // 分页查询
    Page<Application> getPage(int pageNum, int pageSize);
}
