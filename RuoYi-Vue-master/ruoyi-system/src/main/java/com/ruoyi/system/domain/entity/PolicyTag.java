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
@TableName("policy_tags")
@ApiModel(description = "政策标签实体类")
public class PolicyTag {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("标签ID")
    private Integer tagId;

    @TableField("tag_name")
    @ApiModelProperty("标签名称")
    private String tagName;

    @TableField("created_at")
    @ApiModelProperty("标签创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @ApiModelProperty("标签更新时间")
    private LocalDateTime updatedAt;
}
