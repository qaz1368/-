package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.domain.vo.EnterpriseManagersVO;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "企业管理者管理接口")
@RestController
@RequestMapping("/enterprise-managers")
public class EnterpriseManagerController {

    @Autowired
    private EnterpriseManagerService managerService;

    @ApiOperation("新增企业管理者")
    @PostMapping("/add")
    public boolean add(@RequestBody EnterpriseManagers manager) {
        return managerService.saveManager(manager);
    }

    @ApiOperation("更新企业管理者信息")
    @PutMapping("/update")
    public boolean update(@RequestBody EnterpriseManagers manager) {
        return managerService.updateManager(manager);
    }

    @ApiOperation("删除企业管理者")
    @DeleteMapping("/delete/{managerId}")
    public boolean delete(@PathVariable Integer managerId) {
        return managerService.deleteManager(managerId);
    }

    @ApiOperation("批量删除企业管理者")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> managerIds) {
        return managerService.deleteBatch(managerIds);
    }

    @ApiOperation("分页查询企业管理者")
    @GetMapping("/page")
    public IPage<EnterpriseManagersVO> page(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @ApiParam(value = "查询生源地") @RequestParam(required = false) String birthplace,
            @ApiParam(value = "查询姓名") @RequestParam(required = false) String name
    ) {
        return managerService.getManagersPage(page, size, birthplace, name);
    }


    /**
     * 根据manager_id查询企业管理者信息
     */
    @ApiOperation("根据manager_id查询企业管理者信息")
    @GetMapping("/getManager/{managerId}")
    public EnterpriseManagers getManager(@PathVariable Integer managerId) {
        return managerService.getById(managerId);
    }

    /**
     * 查询数据库全部企业管理者
     */
    @ApiOperation("查询数据库全部企业管理者")
    @GetMapping("/getEnterpriseManagersOptions")
    public List<EnterpriseManagers> getEnterpriseManagersOptions() {
        return managerService.list();
    }


    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<EnterpriseManagers> list = managerService.lambdaQuery().select().list();
        ExcelUtil<EnterpriseManagers> util = new ExcelUtil<EnterpriseManagers>(EnterpriseManagers.class);
        util.exportExcel(response, list, "企业管理者数据");
    }

}
