package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;
import com.ruoyi.system.mapper.entrepreneurPark.StudentEntrepreneurshipParkMapper;
import com.ruoyi.system.service.entrepreneurPark.StudentEntrepreneurshipParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentEntrepreneurshipParkServiceImpl extends ServiceImpl<StudentEntrepreneurshipParkMapper, StudentEntrepreneurshipPark>
        implements StudentEntrepreneurshipParkService {

    @Autowired
    private StudentEntrepreneurshipParkMapper parkMapper;

    @Override
    public boolean saveStudentPark(StudentEntrepreneurshipPark park) {
        return save(park);
    }

    @Override
    public boolean updateStudentPark(StudentEntrepreneurshipPark park) {
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
}
