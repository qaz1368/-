package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationTypeMapper;
import com.ruoyi.system.service.entrepreneurPark.ApplicationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationTypeServiceImpl extends ServiceImpl<ApplicationTypeMapper, ApplicationType> implements ApplicationTypeService {

    @Autowired
    private ApplicationTypeMapper applicationTypeMapper;

    @Override
    public boolean saveApplicationType(ApplicationType applicationType) {
        return save(applicationType);  // MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateApplicationType(ApplicationType applicationType) {
        return updateById(applicationType);  // MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteApplicationType(Integer applicationTypeId) {
        return removeById(applicationTypeId);  // 根据ID删除
    }

    @Override
    public boolean deleteBatch(List<Integer> applicationTypeIds) {
        return removeByIds(applicationTypeIds);  // 批量删除
    }

    @Override
    public IPage<ApplicationType> getApplicationTypePage(int page, int size) {
        Page<ApplicationType> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // MyBatis-Plus 提供的分页查询方法
    }
}
