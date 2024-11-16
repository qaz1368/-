package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.Enterprise;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    // 可以添加自定义查询方法，如果有需求的话
}
