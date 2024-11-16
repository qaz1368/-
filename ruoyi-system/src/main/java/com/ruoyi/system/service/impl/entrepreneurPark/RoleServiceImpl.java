package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Role;
import com.ruoyi.system.mapper.entrepreneurPark.RoleMapper;
import com.ruoyi.system.service.entrepreneurPark.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean saveRole(Role role) {
        return save(role);  // MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateRole(Role role) {
        return updateById(role);  // MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteRole(Long roleId) {
        return removeById(roleId);  // MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Long> roleIds) {
        return removeByIds(roleIds);  // 批量删除
    }

    @Override
    public IPage<Role> getRolesPage(int page, int size) {
        Page<Role> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // MyBatis-Plus 提供的分页查询方法
    }
}
