package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.service.entrepreneurPark.IndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "行业管理接口")
@RestController
@RequestMapping("/industries")
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    @ApiOperation("新增行业")
    @PostMapping("/add")
    public boolean add(@RequestBody Industry industry) {
        return industryService.saveIndustry(industry);
    }

    @ApiOperation("更新行业信息")
    @PutMapping("/update")
    public boolean update(@RequestBody Industry industry) {
        return industryService.updateIndustry(industry);
    }

    @ApiOperation("删除行业")
    @DeleteMapping("/delete/{industryId}")
    public boolean delete(@PathVariable Integer industryId) {
        return industryService.deleteIndustry(industryId);
    }

    @ApiOperation("批量删除行业")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> industryIds) {
        return industryService.deleteBatch(industryIds);
    }

    @ApiOperation("分页查询行业")
    @GetMapping("/page")
    public IPage<Industry> page(@RequestParam int page, @RequestParam int size) {
        return industryService.getIndustriesPage(page, size);
    }
}
