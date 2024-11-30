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
@ApiModel("比赛名称实体")
@TableName("competition_names")  // 映射到数据库表 'competition_names'
public class CompetitionName {

    @ApiModelProperty("比赛ID")
    @TableId(value = "competition_id", type = IdType.AUTO)  // 映射到 'competition_id' 字段，作为主键，并设置为主键自增
    @Excel(name = "比赛ID", type = Excel.Type.EXPORT)
    private Integer competitionId;

    @ApiModelProperty("比赛名称")
    @TableField("competition_name")  // 映射到 'competition_name' 字段
    @Excel(name = "比赛名称", type = Excel.Type.EXPORT)
    private String competitionName;
}
