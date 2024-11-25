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
@ApiModel(description = "政策标签实体类")
public class PolicyTagDTO {


    @ApiModelProperty("标签ID")
    private Integer tagId;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("标签更新时间")
    private LocalDateTime updatedAt;

}
