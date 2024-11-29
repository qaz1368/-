package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.EAN;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "企业与岗位关联表实体类")
@TableName("job_enterprise_positions") // 映射数据库表名
public class JobEnterprisePosition {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "企业岗位关联唯一标识符", example = "1")
    @Excel(name = "企业岗位关联ID", type = Excel.Type.EXPORT)
    private Integer enterpriseJobId;

    @TableField("enterprise_id")
    @ApiModelProperty(value = "企业ID", required = true)
    @Excel(name = "企业ID", type = Excel.Type.EXPORT)
    private Integer enterpriseId;

    @TableField("position_id")
    @ApiModelProperty(value = "岗位ID", required = true)
    @Excel(name = "岗位ID", type = Excel.Type.EXPORT)
    private Integer positionId;

    @TableField("position_type")
    @ApiModelProperty(value = "岗位类型", required = true, allowableValues = "Full-time, Part-time, Intern")
    @Excel(name = "岗位类型", type = Excel.Type.EXPORT,readConverterExp = "Full-time=全职,Part-time=兼职,Intern=实习")
    private String positionType;

    @TableField("salary_range")
    @ApiModelProperty(value = "薪资范围", example = "10k-20k")
    @Excel(name = "薪资范围", type = Excel.Type.EXPORT)
    private String salaryRange;

    @TableField("location")
    @ApiModelProperty(value = "工作地点", example = "北京")
    @Excel(name = "工作地点", type = Excel.Type.EXPORT)
    private String location;

    @TableField("job_status")
    @ApiModelProperty(value = "岗位状态", allowableValues = "Open, Closed, On Hold")
    @Excel(name = "岗位状态", type = Excel.Type.EXPORT,readConverterExp = "open=启用,Closed=关闭,On Hold=暂停中")
    private String jobStatus;

    @TableField("job_description")
    @ApiModelProperty(value = "岗位描述")
    @Excel(name = "岗位描述", type = Excel.Type.EXPORT)
    private String jobDescription;

    @TableField("is_active")
    @ApiModelProperty(value = "是否启用", example = "1")
    @Excel(name = "是否启用", type = Excel.Type.EXPORT,readConverterExp = "1=是,0=否")
    private Integer isActive;

    @TableField("created_at")
    @ApiModelProperty(value = "记录创建时间", example = "2024-11-22T12:00:00")
    @Excel(name = "记录创建时间", type = Excel.Type.EXPORT)
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty(value = "记录最后更新时间", example = "2024-11-22T12:30:00")
    @Excel(name = "记录最后更新时间", type = Excel.Type.EXPORT)
    private LocalDateTime updatedAt;
}
