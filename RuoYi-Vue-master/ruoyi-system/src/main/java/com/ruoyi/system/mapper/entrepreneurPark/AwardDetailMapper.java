package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.AwardDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AwardDetailMapper extends BaseMapper<AwardDetail> {

    @Select("SELECT SUM(subsidy_amount) FROM award_details")
    BigDecimal getTotalSubsidyAmount();

    @Select("SELECT SUM(subsidy_amount) FROM award_details WHERE type_id = #{typeId}")
    BigDecimal getTotalSubsidyAmountByTypeId(Integer typeId);

}
