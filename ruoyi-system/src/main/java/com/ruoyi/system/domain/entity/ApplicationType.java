package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 申请类型表（application_type）对应的实体类
 * 用于记录各种申请类型的信息，如请假申请、报销申请等。
 */
@Data
@TableName("application_type")  // 映射到数据库中的"application_type"表
public class ApplicationType {

    @TableId(value = "application_type_id", type = IdType.AUTO)  // application_type_id为主键，自增
    private Integer applicationTypeId;  // 申请类型ID

    @TableField("application_name")
    private String applicationName;  // 申请类型名称

    @TableField("description")
    private String description;  // 申请类型描述

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;  // 创建时间

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;  // 更新时间

}
