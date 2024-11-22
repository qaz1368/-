package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "岗位表实体类")
@TableName("job_positions") // 映射数据库表名
public class JobPosition {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "岗位唯一标识符", example = "1")
    private Integer positionId;

    @TableField("position_name")
    @ApiModelProperty(value = "岗位名称", required = true)
    private String positionName;

    @TableField("position_description")
    @ApiModelProperty(value = "岗位描述", example = "负责产品研发")
    private String positionDescription;

    @TableField("required_skills")
    @ApiModelProperty(value = "岗位要求的技能", example = "沟通能力, 技术能力")
    private String requiredSkills;

    @TableField("position_responsibilities")
    @ApiModelProperty(value = "岗位职责", example = "负责团队管理")
    private String positionResponsibilities;

    @TableField("created_at")
    @ApiModelProperty(value = "记录创建时间", example = "2024-11-22T12:00:00")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty(value = "记录最后更新时间", example = "2024-11-22T12:30:00")
    private LocalDateTime updatedAt;
}
