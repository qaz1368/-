package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "地域信息")
@TableName("region")
public class Region {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "地域ID，自增")
    private Integer regionId;

    @TableField("region_name")
    @ApiModelProperty(value = "地域名称")
    private String regionName;

    @TableField("region_type")
    @ApiModelProperty(value = "地域类型（例如：国家、省、市、区）")
    private String regionType;

    @TableField("level")
    @ApiModelProperty(value = "地域的级别（例如：1代表国家，2代表省，3代表市，4代表区）")
    private Integer level;

    @TableField("created_at")
    @ApiModelProperty(value = "记录创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty(value = "记录更新时间")
    private LocalDateTime updatedAt;
}
