package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("policy_article")
@ApiModel(description = "扶持政策文章实体类")
public class PolicyArticle {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("文章ID")
    @Excel(name = "文章ID", type = Excel.Type.EXPORT)
    private Integer articleId;

    @TableField("category_id")
    @ApiModelProperty("分类ID，指向文章分类表")
    @Excel(name = "分类ID", type = Excel.Type.EXPORT)
    private Integer categoryId;

    @TableField("primary_tag_id")
    @ApiModelProperty("主要标签ID，指向标签表")
    @Excel(name = "主要标签ID", type = Excel.Type.EXPORT)
    private Integer primaryTagId;

    @TableField("title")
    @ApiModelProperty("文章标题")
    @Excel(name = "文章标题", type = Excel.Type.EXPORT)
    private String title;

    @TableField("content")
    @ApiModelProperty("文章内容")
    @Excel(name = "文章内容", type = Excel.Type.EXPORT)
    private String content;

    @TableField("publish_date")
    @ApiModelProperty("发布日期")
    @Excel(name = "发布日期", type = Excel.Type.EXPORT)
    private Date publishDate;

    @TableField("deadline_date")
    @ApiModelProperty("截止时间")
    @Excel(name = "截止时间", type = Excel.Type.EXPORT)
    private Date deadlineDate;

    @TableField("status")
    @ApiModelProperty("文章状态")
    @Excel(name = "文章状态", type = Excel.Type.EXPORT)
    private String status;

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间", type = Excel.Type.EXPORT)
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间", type = Excel.Type.EXPORT)
    private LocalDateTime updatedAt;

    @TableField("video_url")
    @ApiModelProperty("视频路径")
    @Excel(name = "视频路径", type = Excel.Type.EXPORT)
    private String videoUrl;
}
