package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("行业信息")
@TableName("industries")
public class Industry {

    @ApiModelProperty("行业ID")
    @TableId(value = "industry_id", type = IdType.AUTO)
    @Excel(name = "行业ID", type = Excel.Type.EXPORT)
    private Integer industryId;

    @ApiModelProperty("行业名称")
    @TableField("industry_name")  // 映射到 industry_name 字段
    @Excel(name = "行业名称", type = Excel.Type.EXPORT)
    private String industryName;


}

