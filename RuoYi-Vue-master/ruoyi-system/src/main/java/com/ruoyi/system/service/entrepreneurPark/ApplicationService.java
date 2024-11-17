package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.entity.Application;

public interface ApplicationService extends IService<Application> {



    // 分页查询
    Page<Application> getPage(int pageNum, int pageSize);

    boolean save(Application application);

    Page<Application> getPage1(int pageNum, int pageSize, LoginUser loginUser);
}
