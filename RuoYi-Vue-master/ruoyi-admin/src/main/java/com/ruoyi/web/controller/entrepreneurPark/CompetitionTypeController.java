package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DTO.CompetitionTypeDTO;
import com.ruoyi.system.domain.entity.CompetitionName;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.service.entrepreneurPark.CompetitionTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "比赛类型管理接口")
@RestController
@RequestMapping("/competition-types")
public class CompetitionTypeController {

    @Autowired
    private CompetitionTypeService competitionTypeService;

    @ApiOperation("新增比赛类型")
    @PostMapping("/add")
    public boolean add(@RequestBody CompetitionTypeDTO competitionTypeDTO) {
        return competitionTypeService.saveCompetitionType(competitionTypeDTO);
    }

    @ApiOperation("更新比赛类型")
    @PutMapping("/update")
    public boolean update(@RequestBody CompetitionType competitionType) {
        return competitionTypeService.updateCompetitionType(competitionType);
    }

    @ApiOperation("删除比赛类型")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id) {
        return competitionTypeService.deleteCompetitionType(id);
    }

    @ApiOperation("批量删除比赛类型")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return competitionTypeService.deleteBatch(ids);
    }

    @ApiOperation("分页查询比赛类型")
    @GetMapping("/page")
    public IPage<CompetitionType> page(
               @RequestParam(value = "pageNum", defaultValue = "1") int page,
               @RequestParam(value = "size", defaultValue = "10") int size,
    @ApiParam(value = "比赛类型名称") @RequestParam(required = false) String level
    ) {
        return competitionTypeService.getCompetitionTypesPage(page, size,level);
    }

    /**
     * 查询数据库全部的比赛类型
     */
    @ApiOperation("查询数据库全部的比赛类型")
    @GetMapping("/getAllCompetitionType")
    public List<CompetitionType> getAllCompetitionType() {
        return competitionTypeService.list();
    }

    /**
     * 根据id查询全部比赛类型
     */
    @ApiOperation("根据id查询全比赛类型")
    @GetMapping("/getCompetitionTypeById/{id}")
    public CompetitionType getCompetitionTypeById(@PathVariable Integer id) {
        return competitionTypeService.getById(id);
    }


    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<CompetitionType> list = competitionTypeService.lambdaQuery().select().list();
        ExcelUtil<CompetitionType> util = new ExcelUtil<CompetitionType>(CompetitionType.class);
        util.exportExcel(response, list, "比赛名称管理数据");
    }
}
