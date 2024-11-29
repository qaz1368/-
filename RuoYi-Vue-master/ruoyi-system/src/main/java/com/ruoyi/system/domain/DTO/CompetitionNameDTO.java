package com.ruoyi.system.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("比赛名称实体")
public class CompetitionNameDTO {

    @ApiModelProperty("比赛ID")
    private Integer competitionId;

    @ApiModelProperty("比赛名称")
    private String competitionName;
}
