package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.DTO.StudentEntrepreneurshipParkDTO;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.domain.entity.Region;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;
import com.ruoyi.system.mapper.entrepreneurPark.IndustryMapper;
import com.ruoyi.system.mapper.entrepreneurPark.StudentEntrepreneurshipParkMapper;
import com.ruoyi.system.service.entrepreneurPark.StudentEntrepreneurshipParkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentEntrepreneurshipParkServiceImpl extends ServiceImpl<StudentEntrepreneurshipParkMapper, StudentEntrepreneurshipPark>
        implements StudentEntrepreneurshipParkService {

    @Autowired
    private StudentEntrepreneurshipParkMapper parkMapper;
    @Autowired
    private IndustryMapper industryMapper;

    @Override
    public boolean saveStudentPark(StudentEntrepreneurshipParkDTO studentEntrepreneurshipParkDTO) {
        StudentEntrepreneurshipPark park = new StudentEntrepreneurshipPark();
        BeanUtils.copyProperties(studentEntrepreneurshipParkDTO, park);
        park.setCreatedAt(new Date());
        park.setUpdatedAt(new Date());

        QueryWrapper<Industry> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("industry_name", studentEntrepreneurshipParkDTO.getIndustry());
        // 查询行业
        Industry industry = industryMapper.selectOne(queryWrapper);
        if (industry != null) {
            park.setIndustry(industry.getIndustryName());
        }else{
            throw new RuntimeException("行业不存在");
        }
        return save(park);
    }

    @Override
    public boolean updateStudentPark(StudentEntrepreneurshipParkDTO studentEntrepreneurshipParkDTO) {
        StudentEntrepreneurshipPark park = new StudentEntrepreneurshipPark();
        BeanUtils.copyProperties(studentEntrepreneurshipParkDTO, park);
        park.setUpdatedAt(new Date());
        QueryWrapper<Industry> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("industry_name", studentEntrepreneurshipParkDTO.getIndustry());
        // 查询行业
        Industry industry = industryMapper.selectOne(queryWrapper);
        if (industry != null) {
            park.setIndustry(industry.getIndustryName());
        }else{
            throw new RuntimeException("行业不存在");
        }
        return updateById(park);
    }

    @Override
    public boolean deleteStudentPark(Integer parkId) {
        return removeById(parkId);
    }

    @Override
    public boolean deleteBatch(List<Integer> parkIds) {
        return removeByIds(parkIds);
    }

    @Override
    public IPage<StudentEntrepreneurshipPark> studentEntrepreneurshipParkPage(int page, int size) {
        // Page构造方法：当前页, 每页显示记录数
        Page<StudentEntrepreneurshipPark> studentEntrepreneurshipParkPage = new Page<>(page, size);

        List<StudentEntrepreneurshipPark> records = parkMapper.selectPage(studentEntrepreneurshipParkPage, null).getRecords();

        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        List<StudentEntrepreneurshipPark> studentEntrepreneurshipParkList = records.stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
        studentEntrepreneurshipParkPage.setRecords(studentEntrepreneurshipParkList);
        studentEntrepreneurshipParkPage.setTotal(records.size());
        return studentEntrepreneurshipParkPage;
    }
}
