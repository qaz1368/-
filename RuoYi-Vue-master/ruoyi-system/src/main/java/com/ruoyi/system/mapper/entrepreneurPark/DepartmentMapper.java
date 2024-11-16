package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    // 可以根据需求添加自定义的查询方法
}
