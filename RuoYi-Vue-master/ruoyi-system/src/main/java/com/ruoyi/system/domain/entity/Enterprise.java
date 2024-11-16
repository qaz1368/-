package com.ruoyi.system.domain.entity;

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
@TableName("enterprise")
public class Enterprise {

    @ApiModelProperty(value = "企业唯一标识符")
    @TableId(value = "company_id", type = IdType.AUTO)
    private Integer companyId;

    @ApiModelProperty(value = "企业名称", required = true)
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "所属行业")
    @TableField("industry")
    private String industry;

    @ApiModelProperty(value = "注册地址")
    @TableField("registered_address")
    private String registeredAddress;

    @ApiModelProperty(value = "法定代表人")
    @TableField("legal_person")
    private String legalPerson;

    @ApiModelProperty(value = "企业成立日期")
    @TableField("establishment_date")
    private LocalDateTime establishmentDate;

    @ApiModelProperty(value = "企业状态（初创、成长、成熟、关闭）", required = true)
    @TableField("company_status")
    private String companyStatus;

    @ApiModelProperty(value = "获得补助时间")
    @TableField("subsidy_received_date")
    private LocalDateTime subsidyReceivedDate;

    @ApiModelProperty(value = "获得补助金额（单位：元）")
    @TableField("subsidy_amount")
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "带动就业人数")
    @TableField("employment_impact")
    private Integer employmentImpact;

    @ApiModelProperty(value = "签订劳动合同人数")
    @TableField("signed_contracts")
    private Integer signedContracts;

    @ApiModelProperty(value = "缴纳社会保险人数")
    @TableField("social_security_contributors")
    private Integer socialSecurityContributors;

    @ApiModelProperty(value = "年营业额（单位：万元）")
    @TableField("annual_revenue")
    private BigDecimal annualRevenue;

    @ApiModelProperty(value = "年纳税金额（单位：万元）")
    @TableField("annual_tax")
    private BigDecimal annualTax;

    @ApiModelProperty(value = "注册资本（单位：万元）")
    @TableField("registered_capital")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "带动扶贫人数")
    @TableField("poverty_alleviation")
    private Integer povertyAlleviation;

    @ApiModelProperty(value = "贷款金额（单位：万元）")
    @TableField("loan_amount")
    private BigDecimal loanAmount;

    @ApiModelProperty(value = "是否享受政府补贴（TRUE/FALSE）")
    @TableField("government_subsidy")
    private Boolean governmentSubsidy;

    @ApiModelProperty(value = "是否缴纳社会保障（TRUE/FALSE）")
    @TableField("social_security")
    private Boolean socialSecurity;

    @ApiModelProperty(value = "项目获奖情况")
    @TableField("awards_received")
    private String awardsReceived;

    @ApiModelProperty(value = "是否需要创业辅导（TRUE/FALSE）")
    @TableField("entrepreneurship_guidance")
    private Boolean entrepreneurshipGuidance;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "记录创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "最后更新时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "孵化器ID（关联student_entrepreneurship_park表）")
    @TableField("incubator_id")
    private Integer incubatorId;

    @ApiModelProperty(value = "企业负责人ID（关联enterprise_managers表）")
    @TableField("manager_id")
    private Integer managerId;
}
