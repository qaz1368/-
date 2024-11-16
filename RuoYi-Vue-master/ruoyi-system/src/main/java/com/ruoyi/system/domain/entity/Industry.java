package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("行业信息")
@TableName("industries")
public class Industry {

    @ApiModelProperty("行业ID")
    @TableId("industry_id")  // 映射到 industry_id 字段，作为主键
    private Integer industryId;

    @ApiModelProperty("行业名称")
    @TableField("industry_name")  // 映射到 industry_name 字段
    private String industryName;
}

