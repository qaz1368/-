package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.ApplicationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplicationTypeMapper extends BaseMapper<ApplicationType> {

    @Select("SELECT * FROM application_type WHERE application_name = #{applicationType}")
    ApplicationType selectByApplicationType(String applicationType);

    List<ApplicationType> selectPage(@Param("page") int page, @Param("size") int size, @Param("applicationName") String applicationName);
}
