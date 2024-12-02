package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.Application;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.domain.vo.IndustryRankVO;
import com.ruoyi.system.service.entrepreneurPark.IndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "行业管理接口")
@RestController
@RequestMapping("/industries")
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    @ApiOperation("新增行业")
    @PostMapping("/add")
    public boolean addIndustry(@RequestBody Industry industry) {
        return industryService.saveIndustry(industry);
    }

    @ApiOperation("更新行业信息")
    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermi('system:industry:edit')")
    public boolean updateIndustry(@RequestBody Industry industry) {
        return industryService.updateIndustry(industry);
    }

    @ApiOperation("删除行业")
    @DeleteMapping("/delete/{industryId}")
    @PreAuthorize("@ss.hasPermi('system:industry:remove')")
    public boolean deleteIndustry(@PathVariable Integer industryId) {
        return industryService.deleteIndustry(industryId);
    }

    @ApiOperation("批量删除行业")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> industryIds) {
        return industryService.deleteBatch(industryIds);
    }

    @ApiOperation("分页查询行业")
    @GetMapping("/page")
    public IPage<Industry> pageIndustry(
            @ApiParam(value = "当前页", required = false) @RequestParam(value = "pageNum", defaultValue = "1") int page,
            @ApiParam(value = "每页记录数", required = false) @RequestParam(value = "size", defaultValue = "10") int size) {
        return industryService.getIndustriesPage(page, size);
    }

    @GetMapping("/getIndustryById/{industryId}")
    @ApiOperation("通过ID获取行业信息")
    public Industry getIndustryById(@PathVariable("industryId") Integer industryId) {
        return industryService.getById(industryId);
    }


    @GetMapping("/ranking")
    @ApiOperation("所属行业占比排行")
    public List<IndustryRankVO> getIndustryRanking(@RequestParam int size) {
        return industryService.getIndustryRanking(size);
    }

    /**
     * 查询数据库全部行业
     */
    @ApiOperation("查询数据库全部的行业")
    @GetMapping("/getIndustryOptions")
    public List<Industry> getIndustryOptions() {
        return industryService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<Industry> list = industryService.lambdaQuery().select().list();
        ExcelUtil<Industry> util = new ExcelUtil<Industry>(Industry.class);
        util.exportExcel(response, list, "行业数据");
    }
}
