package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.RoleDept;
import com.ruoyi.system.service.entrepreneurPark.RoleDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色与部门关联管理接口")
@RestController
@RequestMapping("/role-dept")
public class RoleDeptController {

    @Autowired
    private RoleDeptService roleDeptService;

    @ApiOperation("新增角色与部门关联")
    @PostMapping("/add")
    public boolean add(@RequestBody RoleDept roleDept) {
        return roleDeptService.saveRoleDept(roleDept);
    }

    @ApiOperation("更新角色与部门关联")
    @PutMapping("/update")
    public boolean update(@RequestBody RoleDept roleDept) {
        return roleDeptService.updateRoleDept(roleDept);
    }

    @ApiOperation("删除角色与部门关联")
    @DeleteMapping("/delete/{roleId}/{departmentId}")
    public boolean delete(@PathVariable Long roleId, @PathVariable Long departmentId) {
        return roleDeptService.deleteRoleDept(roleId, departmentId);
    }

    @ApiOperation("批量删除角色与部门关联")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<RoleDept> roleDepts) {
        return roleDeptService.deleteBatch(roleDepts);
    }

    @ApiOperation("分页查询角色与部门关联列表")
    @GetMapping("/page")
    public IPage<RoleDept> page(@RequestParam int page, @RequestParam int size) {
        return roleDeptService.getRoleDeptPage(page, size);
    }
}
