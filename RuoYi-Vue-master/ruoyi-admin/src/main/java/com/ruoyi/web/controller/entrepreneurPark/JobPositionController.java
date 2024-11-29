package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.service.entrepreneurPark.JobPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "岗位管理接口")
@RestController
@RequestMapping("/api/job-positions")
public class JobPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @ApiOperation(value = "获取岗位列表（分页）")
    @GetMapping("/list")
    public Page<JobPosition> getJobPositionsPage(
            @ApiParam(value = "页码", required = false) @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam(value = "每页大小", required = false) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ApiParam(value = "岗位名称") @RequestParam(required = false) String positionName
    ) {
        return jobPositionService.getJobPositionsPage(pageNum, pageSize, positionName);
    }


    @ApiOperation(value = "根据ID删除岗位")
    @DeleteMapping("/{id}")
    public boolean deleteJobPositionById(
            @ApiParam(value = "岗位ID", required = true) @PathVariable Integer id) {
        return jobPositionService.removeById(id);
    }

    @ApiOperation(value = "批量删除岗位")
    @DeleteMapping("/batch")
    public boolean deleteJobPositions(
            @ApiParam(value = "岗位ID列表", required = true) @RequestBody List<Integer> positionIds) {
        return jobPositionService.removeBatchByIds(positionIds);
    }

    @ApiOperation(value = "添加岗位")
    @PostMapping("/add")
    public boolean addJobPosition(@Valid @RequestBody JobPosition jobPosition) {
        return jobPositionService.save(jobPosition);
    }

    @ApiOperation(value = "更新岗位")
    @PutMapping("/update")
    public boolean updateJobPosition(@Valid @RequestBody JobPosition jobPosition) {
        return jobPositionService.updateById(jobPosition);
    }

    /**
     * 根据positionId获得岗位信息
     */
    @ApiOperation(value = "根据ID查询岗位详情")
    @GetMapping("/{positionId}")
    public JobPosition getJobPositionById(
            @ApiParam(value = "岗位ID", required = true) @PathVariable Integer positionId) {
        return jobPositionService.getById(positionId);
    }

    /**
     * 查询数据库全部岗位
     */
    @ApiOperation("查询数据库全部的岗位")
    @GetMapping("/getJobPositionOptions")
    public List<JobPosition> getJobPositionOptions() {
        return jobPositionService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<JobPosition> list = jobPositionService.lambdaQuery().select().list();
        ExcelUtil<JobPosition> util = new ExcelUtil<JobPosition>(JobPosition.class);
        util.exportExcel(response, list, "岗位管理数据");
    }
}
