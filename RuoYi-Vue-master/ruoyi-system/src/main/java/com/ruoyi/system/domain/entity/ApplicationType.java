package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("申请类型实体类")
@Data
@TableName("application_type")  // 映射数据库表 'application_type'
public class ApplicationType {

    @ApiModelProperty("申请类型ID")
    @TableId("application_type_id")  // 映射到 'application_type_id' 字段
    @Excel(name = "申请类型ID", type = Excel.Type.EXPORT)
    private Integer applicationTypeId;

    @ApiModelProperty("申请类型名称")
    @TableField("application_name")  // 映射到 'application_name' 字段
    @Excel(name = "申请类型名称", type = Excel.Type.EXPORT)
    private String applicationName;

    @ApiModelProperty("申请类型描述")
    @TableField("description")  // 映射到 'description' 字段
    @Excel(name = "申请类型描述", type = Excel.Type.EXPORT)
    private String description;

    @ApiModelProperty("创建时间")
    @TableField("created_at")  // 映射到 'created_at' 字段
    @Excel(name = "创建时间", type = Excel.Type.EXPORT)
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    @TableField("updated_at")  // 映射到 'updated_at' 字段
    @Excel(name = "更新时间", type = Excel.Type.EXPORT)
    private LocalDateTime updatedAt;
}
