package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 企业实体类，映射到数据库中的 enterprise 表
 */
@Data
@ApiModel(description = "企业信息")
public class EnterpriseVO {

    @ApiModelProperty(value = "企业唯一标识符")
    private Integer companyId;

    @ApiModelProperty(value = "企业名称", required = true)
    private String companyName;

    @ApiModelProperty(value = "所属行业")
    private String industry;

    @ApiModelProperty(value = "地域")
    private String region;

    @ApiModelProperty(value = "坐标")
    private String coordinate;

    @ApiModelProperty(value = "注册地址")
    private String registeredAddress;

    @ApiModelProperty(value = "法定代表人")
    private String legalPerson;

    @ApiModelProperty(value = "企业成立日期")
    private LocalDateTime establishmentDate;

    @ApiModelProperty(value = "企业标签，例如“重点企业”或“普通企业”")
    private String enterpriseLabel;

    @ApiModelProperty(value = "企业状态（初创、成长、成熟、关闭）", required = true)
    private String companyStatus;

    @ApiModelProperty(value = "获得补助时间")
    private LocalDateTime subsidyReceivedDate;

    @ApiModelProperty(value = "获得补助金额（单位：元）")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "带动就业人数")
    private Integer employmentImpact;

    @ApiModelProperty(value = "签订劳动合同人数")
    private Integer signedContracts;

    @ApiModelProperty(value = "缴纳社会保险人数")
    private Integer socialSecurityContributors;

    @ApiModelProperty(value = "年营业额（单位：万元）")
    private BigDecimal annualRevenue;

    @ApiModelProperty(value = "年纳税金额（单位：万元）")
    private BigDecimal annualTax;

    @ApiModelProperty(value = "注册资本（单位：万元）")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "带动扶贫人数")
    private Integer povertyAlleviation;

    @ApiModelProperty(value = "贷款金额（单位：万元）")
    private BigDecimal loanAmount;

    @ApiModelProperty(value = "是否享受政府补贴（TRUE/FALSE）")
    private Boolean governmentSubsidy;

    @ApiModelProperty(value = "是否缴纳社会保障（TRUE/FALSE）")
    private Boolean socialSecurity;


    @ApiModelProperty(value = "项目获奖金额")
    private BigDecimal awards;

    @ApiModelProperty(value = "项目获奖情况")
    private String awardsReceived;

    @ApiModelProperty(value = "是否需要创业辅导（TRUE/FALSE）")
    private Boolean entrepreneurshipGuidance;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "记录创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "创业园")
    private String incubator;

    @ApiModelProperty(value = "企业负责人")
    private String managerName;
}
