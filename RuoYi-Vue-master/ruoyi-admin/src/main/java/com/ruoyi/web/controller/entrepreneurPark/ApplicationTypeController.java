package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.domain.entity.Approval;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.service.entrepreneurPark.ApplicationTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "申请类型管理接口")
@RestController
@RequestMapping("/application-type")
public class ApplicationTypeController {

    @Autowired
    private ApplicationTypeService applicationTypeService;

    @ApiOperation("新增申请类型")
    @PostMapping("/add")
    public boolean addApplicationType(@RequestBody ApplicationType applicationType) {
        return applicationTypeService.saveApplicationType(applicationType);
    }

    @ApiOperation("更新申请类型")
    @PutMapping("/update")
    public boolean updateApplicationType(@RequestBody ApplicationType applicationType) {
        return applicationTypeService.updateApplicationType(applicationType);
    }

    @ApiOperation("删除申请类型")
    @DeleteMapping("/delete/{applicationTypeId}")
    public boolean deleteApplicationType(@PathVariable Integer applicationTypeId) {
        return applicationTypeService.deleteApplicationType(applicationTypeId);
    }

    @ApiOperation("批量删除申请类型")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> applicationTypeIds) {
        return applicationTypeService.deleteBatch(applicationTypeIds);
    }

    @ApiOperation("分页查询申请类型列表")
    @GetMapping("/page")
    public IPage<ApplicationType> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return applicationTypeService.getApplicationTypePage(page, size);
    }

    @ApiOperation("通过ID获取申请类型信息")
    @GetMapping("/get/{applicationTypeId}")
    public ApplicationType getApplicationTypeById(@PathVariable("applicationTypeId") Integer applicationTypeId) {
        return applicationTypeService.getById(applicationTypeId);
    }

    /**
     * 查询数据库全部申请类型
     */
    @ApiOperation("查询数据库全部申请类型")
    @GetMapping("/getApplicationTypeOptions")
    public List<ApplicationType> getApplicationTypeOptions() {
        return applicationTypeService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<ApplicationType> list = applicationTypeService.lambdaQuery().select().list();
        ExcelUtil<ApplicationType> util = new ExcelUtil<ApplicationType>(ApplicationType.class);
        util.exportExcel(response, list, "申请类型数据");
    }
}
