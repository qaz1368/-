package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DTO.StudentEntrepreneurshipParkDTO;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.entity.EnterpriseManagers;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;
import com.ruoyi.system.service.entrepreneurPark.StudentEntrepreneurshipParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "创业园管理")
@RestController
@RequestMapping("/student-entrepreneurship-park")
public class StudentEntrepreneurshipParkController {

    @Autowired
    private StudentEntrepreneurshipParkService parkService;

    @ApiOperation("新增创业园")
    @PostMapping("/add")
    public boolean add(@RequestBody StudentEntrepreneurshipParkDTO studentEntrepreneurshipParkDTO) {
        return parkService.saveStudentPark(studentEntrepreneurshipParkDTO);
    }

    @ApiOperation("更新创业园信息")
    @PutMapping("/update")
    public boolean update(@RequestBody StudentEntrepreneurshipParkDTO studentEntrepreneurshipParkDTO) {
        return parkService.updateStudentPark(studentEntrepreneurshipParkDTO);
    }

    @ApiOperation("删除创业园")
    @DeleteMapping("/delete/{parkId}")
    public boolean delete(@PathVariable Integer parkId) {
        return parkService.deleteStudentPark(parkId);
    }

    @ApiOperation("批量删除创业园")
    @DeleteMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> parkIds) {
        return parkService.deleteBatch(parkIds);
    }

    @ApiOperation("分页查询创业园")
    @GetMapping("/page")
    public IPage<StudentEntrepreneurshipPark> studentEntrepreneurshipParkPage(@RequestParam(value = "pageNum", defaultValue = "1") int page,
               @RequestParam(value = "size", defaultValue = "10") int size) {
        return parkService.studentEntrepreneurshipParkPage(page, size);
    }

    /**
     * 根据parkId查询创业园信息
     */
    @ApiOperation("根据parkId查询创业园信息")
    @GetMapping("/get/{parkId}")
    public StudentEntrepreneurshipPark get(@PathVariable Integer parkId) {
        return parkService.getById(parkId);
    }

    /**
     * 查询数据库全部创业园
     */
    @ApiOperation("查询数据库全部创业园")
    @GetMapping("/getStudentEntrepreneurshipParkOptions")
    public List<StudentEntrepreneurshipPark> getStudentEntrepreneurshipParkOptions() {
        return parkService.list();
    }

    @ApiOperation("excel导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        // 查询表里全部数据
        List<StudentEntrepreneurshipPark> list = parkService.lambdaQuery().select().list();
        ExcelUtil<StudentEntrepreneurshipPark> util = new ExcelUtil<StudentEntrepreneurshipPark>(StudentEntrepreneurshipPark.class);
        util.exportExcel(response, list, "比赛名称管理数据");
    }

}
