package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.CompetitionName;
import com.ruoyi.system.service.entrepreneurPark.CompetitionNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "比赛名称管理接口")
@RestController
@RequestMapping("/competition-names")
public class CompetitionNameController {

    @Autowired
    private CompetitionNameService competitionNameService;

    @ApiOperation("新增比赛名称")
    @PostMapping("/add")
    public boolean add(@RequestBody CompetitionName competitionName) {
        return competitionNameService.saveCompetitionName(competitionName);
    }

    @ApiOperation("更新比赛名称")
    @PutMapping("/update")
    public boolean update(@RequestBody CompetitionName competitionName) {
        return competitionNameService.updateCompetitionName(competitionName);
    }

    @ApiOperation("删除比赛名称")
    @DeleteMapping("/delete/{competitionId}")
    public boolean delete(@PathVariable Integer competitionId) {
        return competitionNameService.deleteCompetitionName(competitionId);
    }

    @ApiOperation("批量删除比赛名称")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> competitionIds) {
        return competitionNameService.deleteBatch(competitionIds);
    }

    @ApiOperation("分页查询比赛名称")
    @GetMapping("/page")
    public IPage<CompetitionName> page(@RequestParam int page, @RequestParam int size) {
        return competitionNameService.getCompetitionNamesPage(page, size);
    }
}
