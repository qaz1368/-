package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.DTO.PolicyArticleDTO;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.service.entrepreneurPark.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/DP/")
@Api(tags = "大屏api")
public class DPController {

    @Autowired
    private PolicyArticleService policyArticleService;
    @Autowired
    private AwardDetailService awardDetailService;
    @Autowired
    private JobEnterprisePositionService jobEnterprisePositionService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private StudentEntrepreneurshipParkService parkService;

    @ApiOperation("获取文章列表（分页）")
    @GetMapping("/articleList")
    public Page<PolicyArticleVO> getPolicyArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "分类名称") @RequestParam(required = false) String category,
            @ApiParam(value = "标签名称") @RequestParam(required = false) String primaryTag
    ) {
        return policyArticleService.getPolicyArticles(page, size, category,primaryTag);
    }

    @ApiOperation("获取获奖总数")
    @GetMapping("/getTotalAwardCount")
    public AjaxResult getTotalAwardCount() {
        try {
            long totalAwardCount = awardDetailService.getTotalAwardCount();
            return AjaxResult.success(totalAwardCount);
        } catch (Exception e) {
            return AjaxResult.error("获取获奖总数失败，请稍后再试");
        }
    }

    @ApiOperation("获取获奖总补贴")
    @GetMapping("/getTotalSubsidyAmount")
    public AjaxResult getTotalSubsidyAmount() {
        try {
            BigDecimal totalSubsidyAmount = awardDetailService.getTotalSubsidyAmount();
            return AjaxResult.success(totalSubsidyAmount);
        } catch (Exception e) {
            return AjaxResult.error("获取获奖总补贴，请稍后再试");
        }
    }

    @ApiOperation("获取获奖类型对应的补贴金额")
    @GetMapping("/getAwardTypeSummary")
    public AjaxResult getAwardTypeSummary() {
        try {
            List<AwardTypeVO> awardTypeSummary = awardDetailService.getAwardTypeSummary();
            return AjaxResult.success(awardTypeSummary);
        } catch (Exception e) {
            return AjaxResult.error("获取不同类型获奖情况汇总失败，请稍后再试");
        }
    }

    @ApiOperation("分页查询获奖情况")
    @GetMapping("/getAwardDetailsPage")
    public Page<AwardDetailVO> getAwardDetailsPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @ApiParam(value = "年份") @RequestParam(required = false) Integer year,
            @ApiParam(value = "比赛类型") @RequestParam(required = false) Integer typeId) {
        return awardDetailService.getAwardDetailsPage(page, size, year, typeId);
    }

    @ApiOperation("获取对应级别近4年获奖情况")
    @GetMapping("/getAwardDetailstypeId")
    public List<Integer> getAwardDetailstypeId(
            @ApiParam(value = "比赛类型") @RequestParam(required = false) Integer typeId
    ) {
        return awardDetailService.getAwardDetailstypeId(typeId);
    }

    @ApiOperation(value = "获取企业与岗位关联列表（分页）")
    @GetMapping("/positionsList")
    public Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage(
            @ApiParam(value = "页码", required = false) @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam(value = "每页大小", required = false) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ApiParam(value = "岗位类型") @RequestParam(required = false) String positionType
    ) {
        return jobEnterprisePositionService.getJobEnterprisePositionsPage(pageNum, pageSize,positionType);
    }


    @GetMapping("/ranking")
    @ApiOperation("所属行业占比排行")
    public List<IndustryRankVO> getIndustryRanking(@RequestParam int size) {
        return industryService.getIndustryRanking(size);
    }

    /**
     * 根据parkId查询创业园信息
     */
    @ApiOperation("根据parkId查询创业园信息")
    @GetMapping("/get/{parkId}")
    public StudentEntrepreneurshipPark get(@PathVariable Integer parkId) {
        return parkService.getById(parkId);
    }

}
