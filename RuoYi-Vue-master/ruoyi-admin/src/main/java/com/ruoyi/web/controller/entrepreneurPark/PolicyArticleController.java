package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DTO.PolicyArticleDTO;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.domain.vo.PolicyArticleVO;
import com.ruoyi.system.service.entrepreneurPark.PolicyArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
            @RequestParam(value = "pageNum",defaultValue = "1") int page,
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
    public boolean addPolicyArticle(
            @RequestPart("policyArticleDTO") PolicyArticleDTO policyArticleDTO,
            @RequestPart(value = "file", required = false)MultipartFile file) throws IOException {
        log.info("添加新的扶持政策文章: {}", policyArticleDTO);

        return policyArticleService.addPolicyArticle(policyArticleDTO,file);
    }

    @ApiOperation("更新扶持政策文章")
    @PutMapping("/updatePolicyArticle")
    public boolean updatePolicyArticle(@RequestPart("policyArticleDTO")  PolicyArticleDTO policyArticleDTO,
                                       @RequestPart(value = "file", required = false)MultipartFile file) {
        // 添加日志
        log.info("Received policyArticleDTO: {}", policyArticleDTO);
        log.info("Received file: {}", file != null ? file.getOriginalFilename() : "null");
        return policyArticleService.updatePolicyArticle(policyArticleDTO,file);
    }

    @ApiOperation("删除扶持政策文章")
    @DeleteMapping("/{id}")
    public boolean deletePolicyArticle(
            @ApiParam(value = "文章ID", required = true) @PathVariable Integer id) {

        return policyArticleService.removeArticleById(id);
    }

    @ApiOperation("批量删除扶持政策文章")
    @DeleteMapping("/batch")
    public boolean deletePolicyArticles(
            @ApiParam(value = "文章ID列表", required = true) @RequestBody List<Integer> articleIds) {
        return policyArticleService.removeByIds(articleIds);
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<PolicyArticle> list = policyArticleService.lambdaQuery().select().list();
        ExcelUtil<PolicyArticle> util = new ExcelUtil<PolicyArticle>(PolicyArticle.class);
        util.exportExcel(response, list, "文章数据");
    }


    /**
     * 更改文章视频
     */
    @ApiOperation("更改文章视频")
    @PostMapping("/updateVideo")
    public boolean updateVideo(
            @RequestPart("policyArticleDTO") PolicyArticleDTO policyArticleDTO,
            @ApiParam(value = "视频文件", required = true) @RequestPart MultipartFile file) throws IOException {

        return  policyArticleService.updateVideo(policyArticleDTO,file);
    }


}
