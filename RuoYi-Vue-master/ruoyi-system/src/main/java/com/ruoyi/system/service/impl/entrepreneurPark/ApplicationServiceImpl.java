package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Application;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationMapper;
import com.ruoyi.system.service.entrepreneurPark.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public Page<Application> getPage(int pageNum, int pageSize) {
        // 创建分页对象
        Page<Application> page = new Page<>(pageNum, pageSize);
        return applicationMapper.selectPage(page, null);  // 查询所有记录
    }
}
