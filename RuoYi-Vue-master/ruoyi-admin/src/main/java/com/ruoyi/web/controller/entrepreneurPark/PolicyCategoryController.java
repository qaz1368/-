package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.service.entrepreneurPark.PolicyCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy-categories")
@Api(tags = "政策分类管理")
public class PolicyCategoryController {

    @Autowired
    private PolicyCategoryService policyCategoryService;

    @ApiOperation("获取政策分类列表（分页）")
    @GetMapping("/list")
    public Page<PolicyCategory> getPolicyCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return policyCategoryService.page(new Page<>(page, size));
    }

    @ApiOperation("根据ID查询政策分类详情")
    @GetMapping("/{id}")
    public PolicyCategory getPolicyCategoryById(
            @ApiParam(value = "分类ID", required = true) @PathVariable Integer id) {
        return policyCategoryService.getById(id);
    }

    @ApiOperation("新增政策分类")
    @PostMapping("/add")
    public boolean addPolicyCategory(@RequestBody PolicyCategory policyCategory) {
        return policyCategoryService.save(policyCategory);
    }

    @ApiOperation("更新政策分类")
    @PutMapping("/update")
    public boolean updatePolicyCategory(
            @RequestBody PolicyCategory policyCategory) {
        return policyCategoryService.updateById(policyCategory);
    }

    @ApiOperation("删除政策分类")
    @DeleteMapping("/{id}")
    public boolean deletePolicyCategory(
            @ApiParam(value = "分类ID", required = true) @PathVariable Integer id) {
        return policyCategoryService.removeById(id);
    }

    @ApiOperation("批量删除政策分类")
    @DeleteMapping("/batch")
    public boolean deletePolicyCategories(
            @ApiParam(value = "分类ID列表", required = true) @RequestBody List<Integer> ids) {
        return policyCategoryService.removeByIds(ids);
    }
}
