package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.util.List;

@Api(tags = "企业信息管理")
@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("获取企业列表（分页）")
    @GetMapping("/list")
    public Page<EnterpriseVO> list(
            @ApiParam(value = "当前页", defaultValue = "1") @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页条数", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "地域（模糊查询）") @RequestParam(required = false) String region,
            @ApiParam(value = "所属行业（模糊查询）") @RequestParam(required = false) String industry,
            @ApiParam(value = "企业状态（初创、成长、成熟、关闭）") @RequestParam(required = false) String companyStatus
    ) {

        Page<Enterprise> pageRequest = new Page<>(page, size);
        return enterpriseService.getEnterprisePage(pageRequest, region, industry, companyStatus);
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

}
