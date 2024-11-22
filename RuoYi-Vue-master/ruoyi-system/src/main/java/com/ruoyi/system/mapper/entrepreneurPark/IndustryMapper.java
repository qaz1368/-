package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.Industry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndustryMapper extends BaseMapper<Industry> {

      @Select("SELECT * FROM industries WHERE industry_name = #{industryName}")
      Industry selectByIndustry(String industryName);


      @Select("SELECT industry_name FROM industries WHERE industry_id = #{id}")
      String getIndustryNameById(Integer id);
}
