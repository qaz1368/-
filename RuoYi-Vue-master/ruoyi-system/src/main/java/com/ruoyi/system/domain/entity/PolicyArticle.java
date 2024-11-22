package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("policy_article")
@ApiModel(description = "扶持政策文章实体类")
public class PolicyArticle {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("文章ID")
    private Integer articleId;

    @TableField("category_id")
    @ApiModelProperty("分类ID，指向文章分类表")
    private Integer categoryId;

    @TableField("primary_tag_id")
    @ApiModelProperty("主要标签ID，指向标签表")
    private Integer primaryTagId;

    @TableField("title")
    @ApiModelProperty("文章标题")
    private String title;

    @TableField("content")
    @ApiModelProperty("文章内容")
    private String content;

    @TableField("publish_date")
    @ApiModelProperty("发布日期")
    private LocalDate publishDate;

    @TableField("deadline_date")
    @ApiModelProperty("截止时间")
    private LocalDate deadlineDate;

    @TableField("status")
    @ApiModelProperty("文章状态")
    private String status;

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;
}
