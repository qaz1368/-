package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.entity.AwardDetail;
import com.ruoyi.system.domain.entity.CompetitionName;
import com.ruoyi.system.service.entrepreneurPark.CompetitionNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public IPage<CompetitionName> page(
               @RequestParam(value = "pageNum", defaultValue = "1") int page,
               @RequestParam(value = "size", defaultValue = "10") int size,
               @ApiParam(value = "比赛名称") @RequestParam(required = false) String competitionName
    ) {
        return competitionNameService.getCompetitionNamesPage(page, size,competitionName);
    }


    /**
     * 根据competition_id来查询比赛名称
     */
    @ApiOperation("根据competition_id来查询比赛名称")
    @GetMapping("/getCompetitionName/{competitionId}")
    public CompetitionName getCompetitionName(@PathVariable Integer competitionId) {
        return competitionNameService.getById(competitionId);
    }


    /**
     * 查询数据库全部的比赛名称
     */
    @ApiOperation("查询数据库全部的比赛名称")
    @GetMapping("/getAllCompetitionName")
    public List<CompetitionName> getAllCompetitionName() {
        return competitionNameService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<CompetitionName> list = competitionNameService.lambdaQuery().select().list();
        ExcelUtil<CompetitionName> util = new ExcelUtil<CompetitionName>(CompetitionName.class);
        util.exportExcel(response, list, "比赛名称管理数据");
    }
}
