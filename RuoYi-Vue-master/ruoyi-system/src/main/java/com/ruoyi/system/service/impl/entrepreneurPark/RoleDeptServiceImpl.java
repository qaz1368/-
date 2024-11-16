package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.RoleDept;
import com.ruoyi.system.mapper.entrepreneurPark.RoleDeptMapper;
import com.ruoyi.system.service.entrepreneurPark.RoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDept> implements RoleDeptService {

    @Autowired
    private RoleDeptMapper roleDeptMapper;

    @Override
    public boolean saveRoleDept(RoleDept roleDept) {
        return save(roleDept);  // MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateRoleDept(RoleDept roleDept) {
        return updateById(roleDept);  // MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteRoleDept(Long roleId, Long departmentId) {
        return removeById(new RoleDept() {{
            setRoleId(roleId);
            setDepartmentId(departmentId);
        }});  // 根据主键删除，使用组合主键
    }

    @Override
    public boolean deleteBatch(List<RoleDept> roleDepts) {
        return removeByIds(roleDepts);  // 批量删除
    }

    @Override
    public IPage<RoleDept> getRoleDeptPage(int page, int size) {
        Page<RoleDept> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // MyBatis-Plus 提供的分页查询方法
    }
}
