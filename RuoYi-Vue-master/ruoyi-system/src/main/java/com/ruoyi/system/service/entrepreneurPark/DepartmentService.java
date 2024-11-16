package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.Department;

import java.util.List;

public interface DepartmentService extends IService<Department> {

    boolean saveDepartment(Department department);  // 新增部门

    boolean updateDepartment(Department department); // 更新部门

    boolean deleteDepartment(Long departmentId);  // 删除部门

    boolean deleteBatch(List<Long> departmentIds);  // 批量删除部门

    IPage<Department> getDepartmentsPage(int page, int size);  // 分页查询部门列表
}
