package com.ruoyi.system.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmtpAccountsDTO {
    @TableField("user_name")
    private String userName;

    @TableField("email")
    private String email;
}
