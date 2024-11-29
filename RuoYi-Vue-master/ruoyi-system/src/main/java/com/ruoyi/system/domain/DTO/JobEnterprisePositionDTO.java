package com.ruoyi.system.domain.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "企业与岗位关联表实体类")
public class JobEnterprisePositionDTO {


    @ApiModelProperty(value = "企业岗位关联唯一标识符", example = "1")
    private Integer enterpriseJobId;

    @ApiModelProperty(value = "企业", required = true)
    private String enterprise;

    @ApiModelProperty(value = "岗位", required = true)
    private String position;

    @ApiModelProperty(value = "岗位类型", required = true, allowableValues = "Full-time, Part-time, Intern")
    private String positionType;

    @ApiModelProperty(value = "薪资范围", example = "10k-20k")
    private String salaryRange;

    @ApiModelProperty(value = "工作地点", example = "北京")
    private String location;

    @ApiModelProperty(value = "岗位状态", allowableValues = "Open, Closed, On Hold")
    private String jobStatus;

    @ApiModelProperty(value = "岗位描述")
    private String jobDescription;

    @ApiModelProperty(value = "是否启用", example = "1")
    private Integer isActive;

    @ApiModelProperty(value = "记录创建时间", example = "2024-11-22T12:00:00")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "记录最后更新时间", example = "2024-11-22T12:30:00")
    private LocalDateTime updatedAt;
}
