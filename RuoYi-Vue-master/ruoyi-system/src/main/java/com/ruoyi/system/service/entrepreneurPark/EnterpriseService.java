package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.vo.EnterpriseVO;

public interface EnterpriseService extends IService<Enterprise> {

    /**
     * 分页查询企业信息
     *
     * @param page 分页对象
     * @param companyName 企业名称
     * @return 分页后的企业信息
     */
    Page<EnterpriseVO> getEnterprisePage(Page<Enterprise> page, String companyName,String companyStatus);
}
