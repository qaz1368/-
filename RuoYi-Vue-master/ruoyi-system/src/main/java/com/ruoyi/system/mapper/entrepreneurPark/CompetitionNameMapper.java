package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.CompetitionName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompetitionNameMapper extends BaseMapper<CompetitionName> {
    List<CompetitionName> selectCompetitionNamesByPage(@Param("page") int page, @Param("size") int size, @Param("competitionName") String competitionName);
}