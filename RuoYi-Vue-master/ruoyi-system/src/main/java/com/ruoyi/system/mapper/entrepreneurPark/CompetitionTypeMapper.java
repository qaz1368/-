package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.CompetitionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompetitionTypeMapper extends BaseMapper<CompetitionType> {
    List<CompetitionType> selectCompetitionTypesByPage(@Param("start") int start, @Param("size") int size, @Param("level") String level);
}

