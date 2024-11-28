package com.ruoyi.system.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "申请表")
public class ApplicationDTO {

    @ApiModelProperty("申请人姓名")
    private String applicantName;

    @ApiModelProperty("申请人电话号码")
    private String applicantPhone;

    @ApiModelProperty("申请人邮箱地址")
    private String applicantEmail;

    @ApiModelProperty("申请类型")
    private String applicationType;

    @ApiModelProperty("行业")
    private String industryName;

    @ApiModelProperty("企业介绍")
    private String enterpriseDescription;
}
