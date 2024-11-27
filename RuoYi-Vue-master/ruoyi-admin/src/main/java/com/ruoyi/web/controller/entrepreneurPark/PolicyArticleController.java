package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.DTO.PolicyArticleDTO;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.vo.PolicyArticleVO;
import com.ruoyi.system.service.entrepreneurPark.PolicyArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy-articles")
@Api(tags = "扶持政策文章管理")
public class PolicyArticleController {

    @Autowired
    private PolicyArticleService policyArticleService;

    @ApiOperation("获取扶持政策文章列表（分页）")
    @GetMapping("/list")
    public Page<PolicyArticleVO> getPolicyArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "分类名称") @RequestParam(required = false) String category,
            @ApiParam(value = "标签名称") @RequestParam(required = false) String primaryTag
    ) {
        return policyArticleService.getPolicyArticles(page, size, category,primaryTag);
    }

    @ApiOperation("根据ID查询扶持政策文章详情")
    @GetMapping("/{id}")
    public PolicyArticleVO getPolicyArticleById(
            @ApiParam(value = "文章ID", required = true) @PathVariable Integer id) {
        return policyArticleService.getPolicyArticleById(id);
    }

    @ApiOperation("新增扶持政策文章")
    @PostMapping("/add")
    public boolean addPolicyArticle(@RequestBody PolicyArticleDTO policyArticleDTO) {
        log.info("添加新的扶持政策文章: {}", policyArticleDTO);
        return policyArticleService.addPolicyArticle(policyArticleDTO);
    }

    @ApiOperation("更新扶持政策文章")
    @PutMapping("/updatePolicyArticle")
    public boolean updatePolicyArticle(@RequestBody PolicyArticleDTO policyArticleDTO) {
        return policyArticleService.updatePolicyArticle(policyArticleDTO);
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
