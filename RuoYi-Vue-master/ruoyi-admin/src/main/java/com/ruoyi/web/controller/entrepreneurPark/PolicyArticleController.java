package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.service.entrepreneurPark.PolicyArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy-articles")
@Api(tags = "扶持政策文章管理")
public class PolicyArticleController {

    @Autowired
    private PolicyArticleService policyArticleService;

    @ApiOperation("获取扶持政策文章列表（分页）")
    @GetMapping("/list")
    public Page<PolicyArticle> getPolicyArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return policyArticleService.page(new Page<>(page, size));
    }

    @ApiOperation("根据ID查询扶持政策文章详情")
    @GetMapping("/{id}")
    public PolicyArticle getPolicyArticleById(
            @ApiParam(value = "文章ID", required = true) @PathVariable Integer id) {
        return policyArticleService.getById(id);
    }

    @ApiOperation("新增扶持政策文章")
    @PostMapping
    public boolean addPolicyArticle(@RequestBody PolicyArticle policyArticle) {
        return policyArticleService.save(policyArticle);
    }

    @ApiOperation("更新扶持政策文章")
    @PutMapping("/{id}")
    public boolean updatePolicyArticle(
            @ApiParam(value = "文章ID", required = true) @PathVariable Integer id,
            @RequestBody PolicyArticle policyArticle) {
        policyArticle.setArticleId(id);
        return policyArticleService.updateById(policyArticle);
    }

    @ApiOperation("删除扶持政策文章")
    @DeleteMapping("/{id}")
    public boolean deletePolicyArticle(
            @ApiParam(value = "文章ID", required = true) @PathVariable Integer id) {
        return policyArticleService.removeById(id);
    }

    @ApiOperation("批量删除扶持政策文章")
    @DeleteMapping("/batch")
    public boolean deletePolicyArticles(
            @ApiParam(value = "文章ID列表", required = true) @RequestBody List<Integer> ids) {
        return policyArticleService.removeByIds(ids);
    }
}
