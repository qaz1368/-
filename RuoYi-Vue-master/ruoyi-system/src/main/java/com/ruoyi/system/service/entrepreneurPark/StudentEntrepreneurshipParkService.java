package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.StudentEntrepreneurshipPark;

import java.util.List;

public interface StudentEntrepreneurshipParkService extends IService<StudentEntrepreneurshipPark> {

    // 自定义方法（如有需求）
    boolean saveStudentPark(StudentEntrepreneurshipPark park);

    boolean updateStudentPark(StudentEntrepreneurshipPark park);

    boolean deleteStudentPark(Integer parkId);

    boolean deleteBatch(List<Integer> parkIds);
}
