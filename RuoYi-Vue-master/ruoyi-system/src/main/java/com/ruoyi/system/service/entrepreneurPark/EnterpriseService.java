package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DTO.EnterpriseDTO;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.vo.EnterpriseVO;

import java.util.List;
import java.util.Map;

public interface EnterpriseService extends IService<Enterprise> {

    /**
     * 分页查询企业信息
     *
     * @param page 分页对象
     * @return 分页后的企业信息
     */
    Page<EnterpriseVO> getEnterprisePage(int page,int  size, String regionName, String industryName, String companyStatus);

    boolean addEnterprise(EnterpriseDTO enterpriseDTO);

    boolean updateEnterprise(EnterpriseDTO enterpriseDTO);

    EnterpriseVO getCompanyId(Integer companyId);

    List<EnterpriseVO> getEnterprisesWithCoordinate();

    Map<String, Integer> getEnterpriseCountByPark();

    /**
     * 获取每个公司状态及其对应的企业数量
     * @return Map<公司状态, 企业数量>
     */
    Map<String, Integer> getEnterpriseCountByCompanyStatus();
}
