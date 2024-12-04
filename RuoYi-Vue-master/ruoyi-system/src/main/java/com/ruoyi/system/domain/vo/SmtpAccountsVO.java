package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
@Data
public class SmtpAccountsVO {
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
    private String userName;

    @TableField("email")
    private String email;
}
