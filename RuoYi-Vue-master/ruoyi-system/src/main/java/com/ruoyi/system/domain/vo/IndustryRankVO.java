package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询所属行业占比排行")
public class IndustryRankVO {


    @ApiModelProperty("行业名称")
    private String industryName;

    @ApiModelProperty("行业行业")
    private double percentage;
}

