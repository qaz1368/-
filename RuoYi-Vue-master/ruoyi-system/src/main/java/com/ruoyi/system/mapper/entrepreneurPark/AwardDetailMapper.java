package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.AwardDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AwardDetailMapper extends BaseMapper<AwardDetail> {

    @Select("SELECT SUM(subsidy_amount) FROM award_details")
    BigDecimal getTotalSubsidyAmount();

    @Select("SELECT SUM(subsidy_amount) FROM award_details WHERE type_id = #{typeId}")
    BigDecimal getTotalSubsidyAmountByTypeId(Integer typeId);
    @Select({
            "SELECT a.*, ct.type_name, cn.competition_name",
            "FROM award_details a",
            "LEFT JOIN competition_types ct ON a.type_id = ct.type_id",
            "LEFT JOIN competition_names cn ON a.competition_id = cn.competition_id",
            "WHERE a.award_id = #{awardId}"
    })
    AwardDetail getAwardDetailById(Integer awardId);

    @Select("SELECT COUNT(*) FROM award_details WHERE type_id = #{typeId} AND year >= #{startYear}")
    Long countAwardsByTypeIdAndYear(@Param("typeId") Integer typeId, @Param("startYear") Integer startYear);
    List<AwardDetail> selectPage(@Param("offset") int offset, @Param("limit") int limit, @Param("year") Integer year, @Param("typeId") Integer typeId);
    int count(@Param("year") Integer year, @Param("typeId") Integer typeId);
}
