package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DTO.EnterpriseDTO;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.domain.vo.EnterpriseVO;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(tags = "企业信息管理")
@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("获取企业列表（分页）")
    @GetMapping("/list")
    public Page<EnterpriseVO> list(
            @ApiParam(value = "当前页", defaultValue = "1") @RequestParam(value = "pageNum",defaultValue = "1") int page,
            @ApiParam(value = "每页条数", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "地域（模糊查询）") @RequestParam(required = false) String region,
            @ApiParam(value = "所属行业（模糊查询）") @RequestParam(required = false) String industry,
            @ApiParam(value = "企业状态（初创、成长、成熟、关闭）") @RequestParam(required = false) String companyStatus
    ) {

        return enterpriseService.getEnterprisePage(page,size, region, industry, companyStatus);
    }

    @ApiOperation("新增企业信息")
    @PostMapping("/add")
    public boolean addEnterprise(@RequestBody EnterpriseDTO enterpriseDTO) {
        return enterpriseService.addEnterprise(enterpriseDTO);
    }

    @ApiOperation("更新企业信息")
    @PutMapping("/update")
    public boolean updateEnterprise(@RequestBody EnterpriseDTO enterpriseDTO) {
        return enterpriseService.updateEnterprise(enterpriseDTO);
    }
    /**
     * 根据id查询企业信息
     */
    @ApiOperation("根据id查询企业信息")
    @GetMapping("/{companyId}")
    public EnterpriseVO getCompanyId(
            @ApiParam(value = "企业信息ID", required = true) @PathVariable Integer companyId){

        return enterpriseService.getCompanyId(companyId);
    }


    @ApiOperation("删除企业信息")
    @DeleteMapping("/delete/{id}")
    public boolean deleteEnterprise(@PathVariable Integer id) {
        return enterpriseService.removeById(id);
    }

    @ApiOperation("批量删除企业信息")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return enterpriseService.removeByIds(ids);
    }

    /**
     * 查询数据库全部企业
     */
    @ApiOperation("查询数据库全部企业")
    @GetMapping("/getEnterpriseOptions")
    public List<Enterprise> getEnterpriseOptions() {
        return enterpriseService.list();
    }

    @ApiOperation("获取coordinate有值的企业列表")
    @GetMapping("/withCoordinate")
    public List<EnterpriseVO> getEnterprisesWithCoordinate() {
        return enterpriseService.getEnterprisesWithCoordinate();
    }

    @GetMapping("/enterprise-count-by-park")
    public Map<String, Integer> getEnterpriseCountByPark() {
        return enterpriseService.getEnterpriseCountByPark();
    }

    @ApiOperation("获取每个公司状态及其对应的企业数量")
    @GetMapping("/enterprise-count-by-status")
    public Map<String, Integer> getEnterpriseCountByCompanyStatus() {
        return enterpriseService.getEnterpriseCountByCompanyStatus();
    }


    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<Enterprise> list = enterpriseService.lambdaQuery().select().list();
        ExcelUtil<Enterprise> util = new ExcelUtil<Enterprise>(Enterprise.class);
        util.exportExcel(response, list, "企业管理数据");
    }
}
