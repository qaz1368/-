package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.entity.Application;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    @Insert("INSERT INTO application (process_id, application_type_id, applicant_name, applicant_phone, applicant_email, industry_id, application_date, status, created_at, updated_at, enterprise_description) " +
            "VALUES (#{processId}, #{applicationTypeId}, #{applicantName}, #{applicantPhone}, #{applicantEmail}, #{industryId}, #{applicationDate}, #{status}, #{createdAt}, #{updatedAt}, #{enterpriseDescription})")
    void insertApplication(Application application);

    @Select("SELECT * FROM application WHERE application_id = #{applicationId}")
    Application findApplicationById(Integer applicationId);

    @Update("UPDATE application SET status = #{status}, updated_at = #{updatedAt} WHERE application_id = #{applicationId}")
    void updateApplicationStatus(@Param("applicationId") Integer applicationId, @Param("status") String status, @Param("updatedAt") Date updatedAt);

    @Select("SELECT * FROM application WHERE status = 'pending'")
    List<Application> findPendingApplications();
}
