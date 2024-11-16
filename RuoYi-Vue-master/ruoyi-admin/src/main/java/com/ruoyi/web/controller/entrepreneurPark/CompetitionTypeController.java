package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.service.entrepreneurPark.CompetitionTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "比赛类型管理接口")
@RestController
@RequestMapping("/competition-types")
public class CompetitionTypeController {

    @Autowired
    private CompetitionTypeService competitionTypeService;

    @ApiOperation("新增比赛类型")
    @PostMapping("/add")
    public boolean add(@RequestBody CompetitionType competitionType) {
        return competitionTypeService.saveCompetitionType(competitionType);
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
    public IPage<CompetitionType> page(@RequestParam int page, @RequestParam int size) {
        return competitionTypeService.getCompetitionTypesPage(page, size);
    }
}
