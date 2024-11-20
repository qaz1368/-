package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.ApplicationType;
import com.ruoyi.system.service.entrepreneurPark.ApplicationTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "申请类型管理接口")
@RestController
@RequestMapping("/application-type")
public class ApplicationTypeController {

    @Autowired
    private ApplicationTypeService applicationTypeService;

    @ApiOperation("新增申请类型")
    @PostMapping("/add")
    public boolean add(@RequestBody ApplicationType applicationType) {
        return applicationTypeService.saveApplicationType(applicationType);
    }

    @ApiOperation("更新申请类型")
    @PutMapping("/update")
    public boolean update(@RequestBody ApplicationType applicationType) {
        return applicationTypeService.updateApplicationType(applicationType);
    }

    @ApiOperation("删除申请类型")
    @DeleteMapping("/delete/{applicationTypeId}")
    public boolean delete(@PathVariable Integer applicationTypeId) {
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
}
