package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("比赛类型")
@TableName("competition_types")  // 映射到数据库表 'competition_types'
public class CompetitionType {

    @ApiModelProperty("比赛类型ID")
    @TableId("id")  // 映射到 'id' 字段，作为主键
    private Integer id;

    @ApiModelProperty("比赛类型名称")
    @TableField("level")  // 映射到 'level' 字段
    private String level;
}
