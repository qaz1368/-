package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Application;
import com.ruoyi.system.service.entrepreneurPark.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "申请管理")
@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @ApiOperation(value = "获取申请分页数据", notes = "根据页码和每页数据量获取申请分页数据")
    @GetMapping("/list")
    public Page<Application> getApplicationPage(
            @ApiParam(value = "页码", required = true) @RequestParam int pageNum,
            @ApiParam(value = "每页数量", required = true) @RequestParam int pageSize) {
        return applicationService.getPage(pageNum, pageSize);
    }

    @ApiOperation(value = "新增申请", notes = "添加新的申请记录")
    @PostMapping("/add")
    public boolean addApplication(@RequestBody Application application) {
        return applicationService.save(application);
    }

    @ApiOperation(value = "更新申请", notes = "更新已有的申请记录")
    @PutMapping("/update")
    public boolean updateApplication(@RequestBody Application application) {
        return applicationService.updateById(application);
    }

    @ApiOperation(value = "删除申请", notes = "根据ID删除申请")
    @DeleteMapping("/delete/{id}")
    public boolean deleteApplication(@PathVariable Integer id) {
        return applicationService.removeById(id);
    }

    @ApiOperation(value = "批量删除申请", notes = "根据ID列表批量删除申请")
    @DeleteMapping("/delete-batch")
    public boolean deleteApplicationBatch(@RequestBody List<Integer> ids) {
        return applicationService.removeByIds(ids);
    }
}
