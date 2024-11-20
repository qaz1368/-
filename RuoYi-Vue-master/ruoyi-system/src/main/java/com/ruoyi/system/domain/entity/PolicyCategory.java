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
@TableName("policy_category")
@ApiModel(description = "政策分类实体类")
public class PolicyCategory {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("分类ID")
    private Integer categoryId;

    @TableField("category_name")
    @ApiModelProperty("分类名称")
    private String categoryName;

    @TableField("category_description")
    @ApiModelProperty("分类描述")
    private String categoryDescription;

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;
}
