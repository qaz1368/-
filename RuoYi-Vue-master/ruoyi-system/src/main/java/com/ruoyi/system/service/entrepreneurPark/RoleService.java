package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    boolean saveRole(Role role);  // 新增角色

    boolean updateRole(Role role); // 更新角色

    boolean deleteRole(Long roleId);  // 删除角色

    boolean deleteBatch(List<Long> roleIds);  // 批量删除角色

    IPage<Role> getRolesPage(int page, int size);  // 分页查询角色列表
}
