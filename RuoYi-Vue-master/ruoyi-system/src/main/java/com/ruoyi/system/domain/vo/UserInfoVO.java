package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询个人中心基本信息返回")
public class UserInfoVO {
    private String nick_name;
    private String email;
    private String phonenumber;
    private String emailAuthorizationCode;

    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;
}
