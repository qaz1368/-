package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.JobEnterprisePosition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobEnterprisePositionMapper extends BaseMapper<JobEnterprisePosition> {
    // MyBatis-Plus 提供了很多默认的方法
    // 比如：insert, deleteById, updateById, selectById 等
}
