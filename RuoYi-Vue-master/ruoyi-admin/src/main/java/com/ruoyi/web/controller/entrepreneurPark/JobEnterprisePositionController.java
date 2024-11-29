package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DTO.JobEnterprisePositionDTO;
import com.ruoyi.system.domain.entity.JobEnterprisePosition;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.domain.vo.JobEnterprisePositionVO;
import com.ruoyi.system.service.entrepreneurPark.JobEnterprisePositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "企业与岗位关联接口")
@RestController
@RequestMapping("/api/job-enterprise-positions")
public class JobEnterprisePositionController {

    @Autowired
    private JobEnterprisePositionService jobEnterprisePositionService;

    @ApiOperation(value = "获取企业与岗位关联列表（分页）")
    @GetMapping("/list")
    public Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage(
            @ApiParam(value = "页码", required = false) @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam(value = "每页大小", required = false) @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ApiParam(value = "岗位类型") @RequestParam(required = false) String positionType
    ) {
        return jobEnterprisePositionService.getJobEnterprisePositionsPage(pageNum, pageSize,positionType);
    }


    @ApiOperation(value = "根据ID删除企业与岗位关联")
    @DeleteMapping("/del/{id}")
    public boolean deleteJobEnterprisePositionById(
            @ApiParam(value = "企业岗位关联ID", required = true) @PathVariable Integer id) {
        return jobEnterprisePositionService.removeById(id);
    }

    @ApiOperation(value = "批量删除企业与岗位关联")
    @DeleteMapping("/batch")
    public boolean deleteJobEnterprisePositions(
            @ApiParam(value = "企业岗位关联ID列表", required = true) @RequestBody List<Integer> enterpriseJobIds) {
        return jobEnterprisePositionService.removeBatchByIds(enterpriseJobIds);
    }

    @ApiOperation(value = "添加企业与岗位关联")
    @PostMapping("/add")
    public boolean addJobEnterprisePosition(@Valid @RequestBody JobEnterprisePositionDTO jobEnterprisePositionDTO) {
        return jobEnterprisePositionService.addJobEnterprisePosition(jobEnterprisePositionDTO);
    }

    @ApiOperation(value = "更新企业与岗位关联")
    @PutMapping("/update")
    public boolean updateJobEnterprisePosition(@Valid @RequestBody JobEnterprisePositionDTO jobEnterprisePositionDTO) {
        return jobEnterprisePositionService.updateJobEnterprisePosition(jobEnterprisePositionDTO);
    }

    /**
     * 根据enterpriseJobId查询招聘信息
     */
    @ApiOperation(value = "根据ID查询招聘信息")
    @GetMapping("/{enterpriseJobId}")
    public JobEnterprisePositionVO getJobEnterprisePositionById(
            @ApiParam(value = "招聘信息ID", required = true) @PathVariable Integer enterpriseJobId) {
        return jobEnterprisePositionService.getJobEnterprisePositionById(enterpriseJobId);
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<JobEnterprisePosition> list = jobEnterprisePositionService.lambdaQuery().select().list();
        ExcelUtil<JobEnterprisePosition> util = new ExcelUtil<JobEnterprisePosition>(JobEnterprisePosition.class);
        util.exportExcel(response, list, "岗位管理数据");
    }
}
