package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.entity.AwardDetail;
import com.ruoyi.system.service.entrepreneurPark.AwardDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "获奖情况管理接口")
@RestController
@RequestMapping("/award-details")
public class AwardDetailController {

    @Autowired
    private AwardDetailService awardDetailService;

    @ApiOperation("新增获奖情况")
    @PostMapping("/add")
    public boolean add(@RequestBody AwardDetail awardDetail) {
        return awardDetailService.saveAwardDetail(awardDetail);
    }

    @ApiOperation("更新获奖情况")
    @PutMapping("/update")
    public boolean update(@RequestBody AwardDetail awardDetail) {
        return awardDetailService.updateAwardDetail(awardDetail);
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
    @GetMapping("/page")
    public IPage<AwardDetail> page(
               @RequestParam(value = "page", defaultValue = "1") int page,
               @RequestParam(value = "size", defaultValue = "10") int size) {
        return awardDetailService.getAwardDetailsPage(page, size);
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
}
