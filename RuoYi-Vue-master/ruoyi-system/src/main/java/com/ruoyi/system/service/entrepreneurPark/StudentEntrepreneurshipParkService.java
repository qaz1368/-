package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DTO.StudentEntrepreneurshipParkDTO;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;

import java.util.List;

public interface StudentEntrepreneurshipParkService extends IService<StudentEntrepreneurshipPark> {

    // 自定义方法（如有需求）
    boolean saveStudentPark(StudentEntrepreneurshipParkDTO studentEntrepreneurshipParkDTO);

    boolean updateStudentPark(StudentEntrepreneurshipParkDTO studentEntrepreneurshipParkDTO);

    boolean deleteStudentPark(Integer parkId);

    boolean deleteBatch(List<Integer> parkIds);

    IPage<StudentEntrepreneurshipPark> studentEntrepreneurshipParkPage(int page, int size);
}
