package com.ruoyi.system.domain.DTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "拒绝申请的请求参数")
public class RefuseApplicationDTO {
    private Integer applicationId;
    private String reason;
}
