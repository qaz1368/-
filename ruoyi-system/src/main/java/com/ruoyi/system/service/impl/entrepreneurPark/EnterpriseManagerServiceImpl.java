package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.mapper.entrepreneurPark.EnterpriseManagerMapper;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseManagerServiceImpl extends ServiceImpl<EnterpriseManagerMapper, EnterpriseManagers>
        implements EnterpriseManagerService {

    @Autowired
    private EnterpriseManagerMapper managerMapper;

    @Override
    public boolean saveManager(EnterpriseManagers manager) {
        return save(manager);  // 使用 MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateManager(EnterpriseManagers manager) {
        return updateById(manager);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteManager(Integer managerId) {
        return removeById(managerId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> managerIds) {
        return removeByIds(managerIds);  // 批量删除
    }

    @Override
    public IPage<EnterpriseManagers> getManagersPage(int page, int size) {
        Page<EnterpriseManagers> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // 使用 MyBatis-Plus 提供的分页查询方法
    }
}
