package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;
import com.ruoyi.system.service.entrepreneurPark.StudentEntrepreneurshipParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "创业园管理")
@RestController
@RequestMapping("/student-entrepreneurship-park")
public class StudentEntrepreneurshipParkController {

    @Autowired
    private StudentEntrepreneurshipParkService parkService;

    @ApiOperation("新增创业园")
    @PostMapping("/add")
    public boolean add(@RequestBody StudentEntrepreneurshipPark park) {
        return parkService.saveStudentPark(park);
    }

    @ApiOperation("更新创业园信息")
    @PutMapping("/update")
    public boolean update(@RequestBody StudentEntrepreneurshipPark park) {
        return parkService.updateStudentPark(park);
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
    public IPage<StudentEntrepreneurshipPark> page(@RequestParam int page, @RequestParam int size) {
        Page<StudentEntrepreneurshipPark> pageRequest = new Page<>(page, size);
        return parkService.page(pageRequest);
    }
}
