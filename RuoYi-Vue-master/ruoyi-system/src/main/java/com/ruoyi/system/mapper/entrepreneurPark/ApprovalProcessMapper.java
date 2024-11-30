package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApprovalProcessMapper extends BaseMapper<ApprovalProcess> {
    @Select("SELECT * FROM approval_process WHERE application_type_id = #{applicationTypeId} AND step_order = #{sequence}")
    ApprovalProcess selectByTypeAndOrder(@Param("applicationTypeId") Integer applicationTypeId, @Param("sequence") Integer sequence);

    List<ApprovalProcess> selectPage(@Param("offset") int offset, @Param("limit") int limit);
    int count();
}
