package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.RoleDept;

import java.util.List;

public interface RoleDeptService extends IService<RoleDept> {

    boolean saveRoleDept(RoleDept roleDept);  // 新增角色和部门关联

    boolean updateRoleDept(RoleDept roleDept); // 更新角色和部门关联

    boolean deleteRoleDept(Long roleId, Long departmentId);  // 删除角色和部门关联

    boolean deleteBatch(List<RoleDept> roleDepts);  // 批量删除角色和部门关联

    IPage<RoleDept> getRoleDeptPage(int page, int size);  // 分页查询角色和部门关联列表
}
