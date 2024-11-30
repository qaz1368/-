package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.Industry;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

      @Select("SELECT COUNT(*) FROM enterprise")
      int getTotalEnterpriseCount();

      @Select("SELECT industry_id, COUNT(*) AS count FROM enterprise GROUP BY industry_id")
      List<Map<String, Object>> getIndustryCounts();

}
