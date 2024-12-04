package com.ruoyi.system.domain.DTO;

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
@TableName("policy_article")
@ApiModel(description = "扶持政策文章实体类")
public class PolicyArticleDTO {

    @ApiModelProperty("文章ID")
    private Integer articleId;

    @ApiModelProperty("分类，指向文章分类表")
    private String category;

    @ApiModelProperty("标签，指向标签表")
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

}
