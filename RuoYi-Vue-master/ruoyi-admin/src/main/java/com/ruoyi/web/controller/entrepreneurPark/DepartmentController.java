package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.Department;
import com.ruoyi.system.service.entrepreneurPark.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "部门管理接口")
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("新增部门")
    @PostMapping("/add")
    public boolean add(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    @ApiOperation("更新部门")
    @PutMapping("/update")
    public boolean update(@RequestBody Department department) {
        return departmentService.updateDepartment(department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/delete/{departmentId}")
    public boolean delete(@PathVariable Long departmentId) {
        return departmentService.deleteDepartment(departmentId);
    }

    @ApiOperation("批量删除部门")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Long> departmentIds) {
        return departmentService.deleteBatch(departmentIds);
    }

    @ApiOperation("分页查询部门列表")
    @GetMapping("/page")
    public IPage<Department> page(@RequestParam int page, @RequestParam int size) {
        return departmentService.getDepartmentsPage(page, size);
    }
}
