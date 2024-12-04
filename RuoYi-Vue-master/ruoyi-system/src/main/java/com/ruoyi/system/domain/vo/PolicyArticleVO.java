package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(description = "扶持政策文章VO")
public class PolicyArticleVO {

    @ApiModelProperty("文章ID")
    private Integer articleId;

    @ApiModelProperty("分类，指向文章分类表")
    private String category;

    @ApiModelProperty("主要标签，指向标签表")
    private String primaryTag;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("发布日期")
    private Date publishDate;

    @ApiModelProperty("截止时间")
    private Date deadlineDate;

    @ApiModelProperty("文章状态")
    private String status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty("视频播放地址")
    private String videoUrl;
}
