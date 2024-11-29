package com.ruoyi.system.domain.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("比赛类型")
public class CompetitionTypeDTO {

    @ApiModelProperty("比赛类型ID")
    private Integer id;

    @ApiModelProperty("比赛类型名称")
    private String level;
}
