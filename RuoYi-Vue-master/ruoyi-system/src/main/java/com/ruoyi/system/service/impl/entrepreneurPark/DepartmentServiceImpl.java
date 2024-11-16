package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Department;
import com.ruoyi.system.mapper.entrepreneurPark.DepartmentMapper;
import com.ruoyi.system.service.entrepreneurPark.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public boolean saveDepartment(Department department) {
        return save(department);  // MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateDepartment(Department department) {
        return updateById(department);  // MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteDepartment(Long departmentId) {
        return removeById(departmentId);  // MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Long> departmentIds) {
        return removeByIds(departmentIds);  // 批量删除
    }

    @Override
    public IPage<Department> getDepartmentsPage(int page, int size) {
        Page<Department> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // MyBatis-Plus 提供的分页查询方法
    }
}
