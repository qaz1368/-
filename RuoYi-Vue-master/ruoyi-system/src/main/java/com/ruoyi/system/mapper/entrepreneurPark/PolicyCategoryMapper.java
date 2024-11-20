package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.PolicyCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PolicyCategoryMapper extends BaseMapper<PolicyCategory> {
    // MyBatis-Plus 会自动提供增、删、改、查等方法
}
