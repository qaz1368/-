package com.ruoyi.system.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 smtp_accounts
 * 
 * @author ruoyi
 * @date 2024-12-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmtpAccounts
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;


    /** 关联用户id **/
    private Long user_id;

    /** SMTP服务器域名 */
    @Excel(name = "SMTP服务器域名")
    private String smtpServerDomain;

    /** SMTP服务器端口 */
    @Excel(name = "SMTP服务器端口")
    private Long smtpServerPort;

    /** 是否启用SSL */
    @Excel(name = "是否启用SSL")
    private Integer sslEnabled;

    /** 是否启用STARTTLS */
    @Excel(name = "是否启用STARTTLS")
    private Integer starttlsEnabled;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;


    @TableField("user_name")
    @Excel(name = "用户名")
    private String userName;

    @TableField("email")
    @Excel(name = "邮箱地址")
    private String email;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSmtpServerDomain(String smtpServerDomain) 
    {
        this.smtpServerDomain = smtpServerDomain;
    }

    public String getSmtpServerDomain() 
    {
        return smtpServerDomain;
    }
    public void setSmtpServerPort(Long smtpServerPort) 
    {
        this.smtpServerPort = smtpServerPort;
    }

    public Long getSmtpServerPort() 
    {
        return smtpServerPort;
    }
    public void setSslEnabled(Integer sslEnabled) 
    {
        this.sslEnabled = sslEnabled;
    }

    public Integer getSslEnabled() 
    {
        return sslEnabled;
    }
    public void setStarttlsEnabled(Integer starttlsEnabled) 
    {
        this.starttlsEnabled = starttlsEnabled;
    }

    public Integer getStarttlsEnabled() 
    {
        return starttlsEnabled;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("smtpServerDomain", getSmtpServerDomain())
            .append("smtpServerPort", getSmtpServerPort())
            .append("sslEnabled", getSslEnabled())
            .append("starttlsEnabled", getStarttlsEnabled())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
