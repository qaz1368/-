package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("policy_category")
@ApiModel(description = "政策分类实体类")
public class PolicyCategory {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("分类ID")
    @Excel(name = "分类ID", type = Excel.Type.EXPORT)
    private Integer categoryId;

    @TableField("category_name")
    @ApiModelProperty("分类名称")
    @Excel(name = "分类名称", type = Excel.Type.EXPORT)
    private String categoryName;

    @TableField("category_description")
    @ApiModelProperty("分类描述")
    @Excel(name = "分类描述", type = Excel.Type.EXPORT)
    private String categoryDescription;

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间", type = Excel.Type.EXPORT)
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间", type = Excel.Type.EXPORT)
    private LocalDateTime updatedAt;
}
