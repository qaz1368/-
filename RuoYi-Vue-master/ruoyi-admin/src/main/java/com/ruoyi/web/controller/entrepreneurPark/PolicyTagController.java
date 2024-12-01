package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.service.entrepreneurPark.PolicyTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
            @RequestParam(value = "pageNum", defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "标签名称") @RequestParam(required = false) String tagName
    ) {
        return policyTagService.getPolicyTagsPage(page, size, tagName);
    }

    @ApiOperation("根据ID查询政策标签详情")
    @GetMapping("/{tagId}")
    public PolicyTag getPolicyTagById(
            @ApiParam(value = "标签ID", required = true) @PathVariable Integer tagId) {
        return policyTagService.getById(tagId);
    }

    @ApiOperation("新增政策标签")
    @PostMapping("/add")
    public boolean addPolicyTag(@RequestBody PolicyTag policyTag) {
        return policyTagService.save(policyTag);
    }

    @ApiOperation("更新政策标签")
    @PutMapping("/update")
    public boolean updatePolicyTag(
            @RequestBody PolicyTag policyTag) {
        return policyTagService.updateById(policyTag);
    }

    @ApiOperation("删除政策标签")
    @DeleteMapping("/del/{id}")
    public boolean deletePolicyTag(
            @ApiParam(value = "标签ID", required = true) @PathVariable Integer id) {
        return policyTagService.removeById(id);
    }

    @ApiOperation("批量删除政策标签")
    @DeleteMapping("/batch")
    public boolean deletePolicyTags(
            @ApiParam(value = "标签ID列表", required = true) @RequestBody List<Integer> tagIds) {
        return policyTagService.removeByIds(tagIds);
    }

    /**
     * 查询数据库全部标签
     */
    @ApiOperation("查询数据库全部的标签")
    @GetMapping("/getPrimaryTagOptions")
    public List<PolicyTag> getPrimaryTagOptions() {
        return policyTagService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<PolicyTag> list = policyTagService.lambdaQuery().select().list();
        ExcelUtil<PolicyTag> util = new ExcelUtil<PolicyTag>(PolicyTag.class);
        util.exportExcel(response, list, "标签数据");
    }
}
