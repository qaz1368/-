package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.mapper.entrepreneurPark.ApplicationTypeMapper;
import com.ruoyi.system.service.entrepreneurPark.ApplicationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationTypeServiceImpl extends ServiceImpl<ApplicationTypeMapper, ApplicationType> implements ApplicationTypeService {

    @Autowired
    private ApplicationTypeMapper applicationTypeMapper;

    @Override
    public boolean saveApplicationType(ApplicationType applicationType) {
        applicationType.setApplicationTypeId(null);
        applicationType.setCreatedAt(LocalDateTime.now());
        applicationType.setUpdatedAt(LocalDateTime.now());
        return save(applicationType);  // MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateApplicationType(ApplicationType applicationType) {
        applicationType.setUpdatedAt(LocalDateTime.now());
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
    public IPage<ApplicationType> getApplicationTypePage(int page, int size, String applicationName) {
        // 计算分页的起始位置
        int offset = (page - 1) * size;

        // 查询数据
        List<ApplicationType> list = applicationTypeMapper.selectPage(offset, size, applicationName);

        // 创建 Page 对象
        Page<ApplicationType> pageResult = new Page<>(page, size);
        pageResult.setRecords(list);

        // 返回结果
        return pageResult;
    }
}
