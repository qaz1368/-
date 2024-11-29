package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Excel;
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
    @Excel(name = "企业唯一标识符", type = Excel.Type.EXPORT)
    private Integer companyId;

    @ApiModelProperty(value = "企业名称", required = true)
    @TableField("company_name")
    @Excel(name = "企业名称", type = Excel.Type.EXPORT)
    private String companyName;

    @ApiModelProperty(value = "行业id")
    @TableField("industry_id")
    @Excel(name = "行业id", type = Excel.Type.EXPORT)
    private Integer industryId;

    @ApiModelProperty(value = "地域id")
    @TableField("region_id")
    @Excel(name = "地域id", type = Excel.Type.EXPORT)
    private Integer regionId;

    @ApiModelProperty(value = "坐标")
    @TableField("coordinate")
    @Excel(name = "坐标", type = Excel.Type.EXPORT)
    private String coordinate;

    @ApiModelProperty(value = "注册地址")
    @TableField("registered_address")
    @Excel(name = "注册地址", type = Excel.Type.EXPORT)
    private String registeredAddress;

    @ApiModelProperty(value = "法定代表人")
    @TableField("legal_person")
    @Excel(name = "法定代表人", type = Excel.Type.EXPORT)
    private String legalPerson;

    @ApiModelProperty(value = "企业成立日期")
    @TableField("establishment_date")
    @Excel(name = "企业成立日期", type = Excel.Type.EXPORT)
    private LocalDateTime establishmentDate;

    @ApiModelProperty(value = "企业标签，例如“重点企业”或“普通企业”")
    @TableField("enterprise_label")
    @Excel(name = "企业标签", type = Excel.Type.EXPORT)
    private String enterpriseLabel;

    @ApiModelProperty(value = "企业状态（初创、成长、成熟、关闭）", required = true)
    @TableField("company_status")
    @Excel(name = "企业状态", type = Excel.Type.EXPORT, readConverterExp = "Initial=初创,Growing=成长,Mature=成熟,Closed=关闭")
    private String companyStatus;

    @ApiModelProperty(value = "获得补助时间")
    @TableField("subsidy_received_date")
    @Excel(name = "获得补助时间", type = Excel.Type.EXPORT)
    private LocalDateTime subsidyReceivedDate;

    @ApiModelProperty(value = "获得补助金额（单位：元）")
    @TableField("subsidy_amount")
    @Excel(name = "获得补助金额", type = Excel.Type.EXPORT)
    private BigDecimal subsidyAmount;

    @ApiModelProperty(value = "带动就业人数")
    @TableField("employment_impact")
    @Excel(name = "带动就业人数", type = Excel.Type.EXPORT)
    private Integer employmentImpact;

    @ApiModelProperty(value = "签订劳动合同人数")
    @TableField("signed_contracts")
    @Excel(name = "签订劳动合同人数", type = Excel.Type.EXPORT)
    private Integer signedContracts;

    @ApiModelProperty(value = "缴纳社会保险人数")
    @TableField("social_security_contributors")
    @Excel(name = "缴纳社会保险人数", type = Excel.Type.EXPORT)
    private Integer socialSecurityContributors;

    @ApiModelProperty(value = "年营业额（单位：万元）")
    @TableField("annual_revenue")
    @Excel(name = "年营业额", type = Excel.Type.EXPORT)
    private BigDecimal annualRevenue;

    @ApiModelProperty(value = "年纳税金额（单位：万元）")
    @TableField("annual_tax")
    @Excel(name = "年纳税金额", type = Excel.Type.EXPORT)
    private BigDecimal annualTax;

    @ApiModelProperty(value = "注册资本（单位：万元）")
    @TableField("registered_capital")
    @Excel(name = "注册资本", type = Excel.Type.EXPORT)
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "带动扶贫人数")
    @TableField("poverty_alleviation")
    @Excel(name = "带动扶贫人数", type = Excel.Type.EXPORT)
    private Integer povertyAlleviation;

    @ApiModelProperty(value = "贷款金额（单位：万元）")
    @TableField("loan_amount")
    @Excel(name = "贷款金额", type = Excel.Type.EXPORT)
    private BigDecimal loanAmount;

    @ApiModelProperty(value = "是否享受政府补贴（TRUE/FALSE）")
    @TableField("government_subsidy")
    @Excel(name = "是否享受政府补贴", type = Excel.Type.EXPORT, readConverterExp = "false=否,true=是")
    private Boolean governmentSubsidy;

    @ApiModelProperty(value = "是否缴纳社会保障（TRUE/FALSE）")
    @TableField("social_security")
    @Excel(name = "是否缴纳社会保障", type = Excel.Type.EXPORT, readConverterExp = "false=否,true=是")
    private Boolean socialSecurity;

    @ApiModelProperty(value = "项目获奖情况")
    @TableField("awards_received")
    @Excel(name = "项目获奖情况", type = Excel.Type.EXPORT)
    private String awardsReceived;

    @ApiModelProperty(value = "是否需要创业辅导（TRUE/FALSE）")
    @TableField("entrepreneurship_guidance")
    @Excel(name = "是否需要创业辅导", type = Excel.Type.EXPORT, readConverterExp = "false=否,true=是")
    private Boolean entrepreneurshipGuidance;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    @Excel(name = "备注", type = Excel.Type.EXPORT)
    private String remarks;

    @ApiModelProperty(value = "记录创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @Excel(name = "记录创建时间", type = Excel.Type.EXPORT)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "最后更新时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @Excel(name = "最后更新时间", type = Excel.Type.EXPORT)
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "孵化器ID（关联student_entrepreneurship_park表）")
    @TableField("incubator_id")
    @Excel(name = "孵化器ID", type = Excel.Type.EXPORT)
    private Integer incubatorId;

    @ApiModelProperty(value = "企业负责人ID（关联enterprise_managers表）")
    @TableField("manager_id")
    @Excel(name = "企业负责人ID", type = Excel.Type.EXPORT)
    private Integer managerId;
}
