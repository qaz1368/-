package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;


import java.math.BigDecimal;
import java.util.Date;


@TableName("sys_park")
public class SysPark extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer incubatorId; // 唯一标识符

    @TableField("incubator_name")
    private String incubatorName; // 名称

    private String industry; // 行业类别

    private String address; // 地址

    @TableField("company_members")
    private Integer companyMembers; // 入驻企业数量

    @TableField("entry_date")
    private Date entryDate; // 成立日期

    @TableField("financial_support")
    private BigDecimal financialSupport; // 资金支持金额

    @TableField("investment_amount")
    private BigDecimal investmentAmount; // 投入经费

    @TableField("total_area")
    private BigDecimal totalArea; // 整体面积

    @TableField("created_at")
    private Date createdAt; // 记录创建时间

    @TableField("updated_at")
    private Date updatedAt; // 最后更新时间
}
