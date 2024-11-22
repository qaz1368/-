package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.service.entrepreneurPark.PolicyTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/policy-tags")
@Api(tags = "政策标签管理")
public class PolicyTagController {

    @Resource
    private PolicyTagService policyTagService;

    @ApiOperation("获取政策标签列表（分页）")
    @GetMapping("/list")
    public Page<PolicyTag> getPolicyTags(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return policyTagService.page(new Page<>(page, size));
    }

    @ApiOperation("根据ID查询政策标签详情")
    @GetMapping("/{id}")
    public PolicyTag getPolicyTagById(
            @ApiParam(value = "标签ID", required = true) @PathVariable Integer id) {
        return policyTagService.getById(id);
    }

    @ApiOperation("新增政策标签")
    @PostMapping
    public boolean addPolicyTag(@RequestBody PolicyTag policyTag) {
        return policyTagService.save(policyTag);
    }

    @ApiOperation("更新政策标签")
    @PutMapping("/{id}")
    public boolean updatePolicyTag(
            @ApiParam(value = "标签ID", required = true) @PathVariable Integer id,
            @RequestBody PolicyTag policyTag) {
        policyTag.setTagId(id);
        return policyTagService.updateById(policyTag);
    }

    @ApiOperation("删除政策标签")
    @DeleteMapping("/{id}")
    public boolean deletePolicyTag(
            @ApiParam(value = "标签ID", required = true) @PathVariable Integer id) {
        return policyTagService.removeById(id);
    }

    @ApiOperation("批量删除政策标签")
    @DeleteMapping("/batch")
    public boolean deletePolicyTags(
            @ApiParam(value = "标签ID列表", required = true) @RequestBody List<Integer> ids) {
        return policyTagService.removeByIds(ids);
    }
}
