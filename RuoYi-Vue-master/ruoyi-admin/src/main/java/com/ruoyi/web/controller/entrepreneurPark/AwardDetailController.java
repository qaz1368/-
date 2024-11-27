package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.DTO.AwardDetailDTO;
import com.ruoyi.system.domain.entity.AwardDetail;
import com.ruoyi.system.domain.vo.AwardDetailVO;
import com.ruoyi.system.domain.vo.AwardTypeVO;
import com.ruoyi.system.domain.vo.AwardYearVO;
import com.ruoyi.system.service.entrepreneurPark.AwardDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "获奖情况管理接口")
@RestController
@RequestMapping("/award-details")
public class AwardDetailController {

    @Autowired
    private AwardDetailService awardDetailService;

    @ApiOperation("新增获奖情况")
    @PostMapping("/add")
    public boolean add(@RequestBody AwardDetailDTO awardDetailDTO) {
        return awardDetailService.saveAwardDetail(awardDetailDTO);
    }

    @ApiOperation("更新获奖情况")
    @PutMapping("/update")
    public boolean update(@RequestBody AwardDetailDTO awardDetailDTO) {
        return awardDetailService.updateAwardDetail(awardDetailDTO);
    }

    @ApiOperation("删除获奖情况")
    @DeleteMapping("/delete/{awardId}")
    public boolean delete(@PathVariable Integer awardId) {
        return awardDetailService.deleteAwardDetail(awardId);
    }

    @ApiOperation("批量删除获奖情况")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> awardIds) {
        return awardDetailService.deleteBatch(awardIds);
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
    public AwardYearVO getAwardDetailstypeId(
            @ApiParam(value = "比赛类型") @RequestParam(required = false) Integer typeId
    ) {
        return awardDetailService.getAwardDetailstypeId(typeId);
    }

    @ApiOperation("获取id对应获奖情况")
    @GetMapping("/getAwardDetailsId/{awardId}")
    public AwardDetailVO getAwardDetailsId(
            @ApiParam(value = "获奖情况ID", required = true) @PathVariable Integer awardId) {

        return awardDetailService.getAwardDetailsId(awardId).get(0);
    }

    /**
     * 根据AwardId获取获奖情况
     */
    @ApiOperation("根据AwardId获取获奖情况")
    @GetMapping("/getAwardDetail/{awardId}")
    public AjaxResult getAwardDetail(@PathVariable Integer awardId) {

        try {
            return AjaxResult.success(awardDetailService.getById(awardId));
        } catch (Exception e) {
            return AjaxResult.error("获取获奖详情失败，请稍后再试");
        }
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
}
