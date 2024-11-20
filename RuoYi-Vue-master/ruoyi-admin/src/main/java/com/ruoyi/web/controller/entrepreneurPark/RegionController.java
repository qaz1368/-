package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Region;
import com.ruoyi.system.service.entrepreneurPark.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
@Api(tags = "地域管理接口")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增地域")
    public boolean addRegion(@RequestBody Region region) {
        return regionService.saveRegion(region);
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新地域")
    public boolean updateRegion(@RequestBody Region region) {
        return regionService.updateRegion(region);
    }

    @DeleteMapping("/delete/{regionId}")
    @ApiOperation(value = "删除地域")
    public boolean deleteRegion(@PathVariable("regionId") Integer regionId) {
        return regionService.deleteRegionById(regionId);
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation(value = "批量删除地域")
    public boolean batchDeleteRegions(@RequestBody List<Integer> regionIds) {
        return regionService.deleteBatch(regionIds);
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询地域")
    public Page<Region> getRegionsPage(
            @ApiParam(value = "当前页", required = true) @RequestParam("page") int page,
            @ApiParam(value = "每页记录数", required = true) @RequestParam("size") int size) {
        return regionService.getRegionPage(page, size);
    }

    @GetMapping("/{regionId}")
    @ApiOperation(value = "查询单个地域")
    public Region getRegionById(@PathVariable("regionId") Integer regionId) {
        return regionService.getById(regionId);
    }
}
