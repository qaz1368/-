package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "企业管理者管理接口")
@RestController
@RequestMapping("/enterprise-managers")
public class EnterpriseManagerController {

    @Autowired
    private EnterpriseManagerService managerService;

    @ApiOperation("新增企业管理者")
    @PostMapping("/add")
    public boolean add(@RequestBody EnterpriseManagers manager) {
        return managerService.saveManager(manager);
    }

    @ApiOperation("更新企业管理者信息")
    @PutMapping("/update")
    public boolean update(@RequestBody EnterpriseManagers manager) {
        return managerService.updateManager(manager);
    }

    @ApiOperation("删除企业管理者")
    @DeleteMapping("/delete/{managerId}")
    public boolean delete(@PathVariable Integer managerId) {
        return managerService.deleteManager(managerId);
    }

    @ApiOperation("批量删除企业管理者")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> managerIds) {
        return managerService.deleteBatch(managerIds);
    }

    @ApiOperation("分页查询企业管理者")
    @GetMapping("/page")
    public IPage<EnterpriseManagers> page(@RequestParam int page, @RequestParam int size) {
        return managerService.getManagersPage(page, size);
    }
}
