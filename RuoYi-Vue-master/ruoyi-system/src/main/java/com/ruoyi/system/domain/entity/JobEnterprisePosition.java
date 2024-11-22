package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "企业与岗位关联表实体类")
@TableName("job_enterprise_positions") // 映射数据库表名
public class JobEnterprisePosition {

    @TableId
    @ApiModelProperty(value = "企业岗位关联唯一标识符", example = "1")
    private Integer enterpriseJobId;

    @TableField("enterprise_id")
    @ApiModelProperty(value = "企业ID", required = true)
    private Integer enterpriseId;

    @TableField("position_id")
    @ApiModelProperty(value = "岗位ID", required = true)
    private Integer positionId;

    @TableField("position_type")
    @ApiModelProperty(value = "岗位类型", required = true, allowableValues = "Full-time, Part-time, Intern")
    private String positionType;

    @TableField("salary_range")
    @ApiModelProperty(value = "薪资范围", example = "10k-20k")
    private String salaryRange;

    @TableField("location")
    @ApiModelProperty(value = "工作地点", example = "北京")
    private String location;

    @TableField("job_status")
    @ApiModelProperty(value = "岗位状态", allowableValues = "Open, Closed, On Hold")
    private String jobStatus;

    @TableField("job_description")
    @ApiModelProperty(value = "岗位描述")
    private String jobDescription;

    @TableField("is_active")
    @ApiModelProperty(value = "是否启用", example = "true")
    private Boolean isActive;

    @TableField("created_at")
    @ApiModelProperty(value = "记录创建时间", example = "2024-11-22T12:00:00")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty(value = "记录最后更新时间", example = "2024-11-22T12:30:00")
    private LocalDateTime updatedAt;
}
