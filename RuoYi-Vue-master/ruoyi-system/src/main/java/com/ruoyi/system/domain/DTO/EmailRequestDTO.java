package com.ruoyi.system.domain.DTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("发送邮箱请求")
public class EmailRequestDTO {
    private String applicationId;
}
