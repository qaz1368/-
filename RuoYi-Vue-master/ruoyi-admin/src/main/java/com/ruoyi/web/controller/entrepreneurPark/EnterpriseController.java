package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "企业信息管理")
@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("获取企业列表（分页）")
    @GetMapping("/list")
    public Page<Enterprise> list(
            @ApiParam(value = "当前页", defaultValue = "1") @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页条数", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "企业名称（模糊查询）") @RequestParam(required = false) String companyName) {

        Page<Enterprise> pageRequest = new Page<>(page, size);
        return enterpriseService.getEnterprisePage(pageRequest, companyName);
    }

    @ApiOperation("新增企业信息")
    @PostMapping("/add")
    public boolean add(@RequestBody Enterprise enterprise) {
        return enterpriseService.save(enterprise);
    }

    @ApiOperation("更新企业信息")
    @PutMapping("/update")
    public boolean update(@RequestBody Enterprise enterprise) {
        return enterpriseService.updateById(enterprise);
    }

    @ApiOperation("删除企业信息")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id) {
        return enterpriseService.removeById(id);
    }

    @ApiOperation("批量删除企业信息")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return enterpriseService.removeByIds(ids);
    }
}
