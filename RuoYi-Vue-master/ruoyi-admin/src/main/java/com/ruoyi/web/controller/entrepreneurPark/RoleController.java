package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.Role;
import com.ruoyi.system.service.entrepreneurPark.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("新增角色")
    @PostMapping("/add")
    public boolean add(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @ApiOperation("更新角色")
    @PutMapping("/update")
    public boolean update(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/delete/{roleId}")
    public boolean delete(@PathVariable Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Long> roleIds) {
        return roleService.deleteBatch(roleIds);
    }

    @ApiOperation("分页查询角色列表")
    @GetMapping("/page")
    public IPage<Role> page(@RequestParam int page, @RequestParam int size) {
        return roleService.getRolesPage(page, size);
    }
}
