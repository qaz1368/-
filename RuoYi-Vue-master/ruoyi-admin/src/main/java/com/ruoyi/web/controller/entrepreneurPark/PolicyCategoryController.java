package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.service.entrepreneurPark.PolicyCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
            @RequestParam(value = "pageNum",defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "分类名称") @RequestParam(required = false) String categoryName
    ) {
        return policyCategoryService.getPolicyCategoriesPage(page, size, categoryName);
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
    @DeleteMapping("/delete/{id}")
    public boolean deletePolicyCategory(
            @ApiParam(value = "分类ID", required = true) @PathVariable Integer id) {
        return policyCategoryService.removeById(id);
    }

    @ApiOperation("批量删除政策分类")
    @DeleteMapping("/batch")
    public boolean deletePolicyCategories(
            @ApiParam(value = "分类ID列表", required = true) @RequestBody List<Integer> categoryIds) {
        return policyCategoryService.removeByIds(categoryIds);
    }

    /**
     * 查询数据库全部的分类
     */
    @ApiOperation("查询数据库全部的分类")
    @GetMapping("/getCategoryOptions")
    public List<PolicyCategory> getCategoryOptions() {
        return policyCategoryService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<PolicyCategory> list = policyCategoryService.lambdaQuery().select().list();
        ExcelUtil<PolicyCategory> util = new ExcelUtil<PolicyCategory>(PolicyCategory.class);
        util.exportExcel(response, list, "标签数据");
    }
}
