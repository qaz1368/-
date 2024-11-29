package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DTO.ApplicationDTO;
import com.ruoyi.system.domain.DTO.PassApplicationDTO;
import com.ruoyi.system.domain.entity.Application;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.service.entrepreneurPark.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@Slf4j
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

    @ApiOperation(value = "获取部门管理员申请分页数据", notes = "根据页码和每页数据量获取部门管理员申请分页数据")
    @GetMapping("/list1")
    public Page<Application> getApplicationPage1(
            @ApiParam(value = "页码", required = true) @RequestParam int pageNum,
            @ApiParam(value = "每页数量", required = true) @RequestParam int pageSize) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return applicationService.getPage1(pageNum, pageSize, loginUser);
    }

    @ApiOperation(value = "新增申请", notes = "添加新的申请记录")
    @PostMapping("/add")
    public boolean addApplication(@RequestBody ApplicationDTO applicationDTO) {
        log.info("添加新的申请记录: {}", applicationDTO);
        return applicationService.save(applicationDTO);
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


    // 通过申请
    @ApiOperation(value = "通过申请", notes = "根据ID列表通过申请")
    @PostMapping("/approve/pass")
    public String approveApplication(@RequestBody PassApplicationDTO passApplicationDTO) {
        applicationService.approveApplication(passApplicationDTO);
        return "申请已通过";
    }

    // 拒绝申请
    @ApiOperation(value = "拒绝申请", notes = "根据ID列表拒绝申请")
    @PostMapping("/approve/reject")
    public String rejectApplication(@RequestBody PassApplicationDTO passApplicationDTO) {
        applicationService.rejectApplication(passApplicationDTO);
        return "申请已拒绝";
    }



    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<Application> list = applicationService.lambdaQuery().select().list();
        ExcelUtil<Application> util = new ExcelUtil<Application>(Application.class);
        util.exportExcel(response, list, "申请表数据");
    }


    @ApiOperation("excel导入")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Application> util = new ExcelUtil<Application>(Application.class);
        List<Application> applications = util.importExcel(file.getInputStream());
        String message = String.valueOf(applicationService.saveBatch(applications));
        return success(message);
    }

}
