package com.ruoyi.web.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysPark;
import com.ruoyi.system.service.ISysParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/park")
@Api("创业园管理表")
public class SysParkController extends BaseController {

    @Autowired
    private ISysParkService iSysParkService;

    /**
     * 查询创业园信息
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询创业园信息")
    public TableDataInfo list() {

        // 创建分页对象
        Page<SysPark> page = new Page<>(1, 10);

        // 执行分页查询
        IPage<SysPark> result = iSysParkService.lambdaQuery().select()
                .page(page);

        // 封装分页数据
        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setCode(0);
        tableDataInfo.setMsg("查询成功");
        tableDataInfo.setTotal(result.getTotal());
        tableDataInfo.setRows(result.getRecords());

        return tableDataInfo;
    }


    /**
     * 新增创业园信息
     */
    @PostMapping ("/post")
    @ApiOperation("新增创业园信息")
    public AjaxResult add(@RequestBody SysPark sysPark) {
        return iSysParkService.save(sysPark) ? success() : error();
    }

    /**
     * 删除创业园
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除创业园")
    public AjaxResult remove(@PathVariable Integer id) {
        return iSysParkService.removeById(id) ? success() : error();
    }



}
