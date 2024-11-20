package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.entity.*;
import com.ruoyi.system.domain.vo.EnterpriseVO;
import com.ruoyi.system.mapper.entrepreneurPark.*;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private IndustryMapper industryMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private StudentEntrepreneurshipParkMapper studentEntrepreneurshipParkMapper;

    @Autowired
    private EnterpriseManagerMapper enterpriseManagersMapper;

    @Override
    public Page<EnterpriseVO> getEnterprisePage(Page<Enterprise> page, String companyName) {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
         if (companyName != null && !companyName.isEmpty()) {
            queryWrapper.eq("company_name", companyName);
        }

        IPage<Enterprise> enterprisePage = enterpriseMapper.selectPage(page, queryWrapper);

        List<Enterprise> enterprisePages = enterprisePage.getRecords();


        List<EnterpriseVO> enterpriseVOS = new ArrayList<>();
        for (Enterprise enterprise : enterprisePages) {
            EnterpriseVO enterpriseVO = new EnterpriseVO();

            // 查询企业名
            Industry industry = industryMapper.selectById(enterprise.getIndustryId());
            if (industry != null) {
                enterpriseVO.setIndustry(industry.getIndustryName());
            }else
                enterpriseVO.setIndustry("暂无");

            // 查询地域
            Region region = regionMapper.selectById(enterprise.getRegionId());
            if (region != null) {
                enterpriseVO.setRegion(region.getRegionName());
            }else
                enterpriseVO.setRegion("暂无");

            // 查询创业园信息
            StudentEntrepreneurshipPark studentEntrepreneurshipPark = studentEntrepreneurshipParkMapper.selectById(enterprise.getIncubatorId());
            if (studentEntrepreneurshipPark != null) {
                enterpriseVO.setIncubator(studentEntrepreneurshipPark.getParkName());
            }else
                enterpriseVO.setIncubator("暂无");

            // 查询企业管理者
            EnterpriseManagers enterpriseManagers = enterpriseManagersMapper.selectById(enterprise.getManagerId());
            if (enterpriseManagers != null) {
                enterpriseVO.setManagerName(enterpriseManagers.getName());
            }else
                enterpriseVO.setManagerName("暂无");

            BeanUtils.copyProperties(enterprise, enterpriseVO);
            enterpriseVOS.add(enterpriseVO);
        }

        Page<EnterpriseVO> pageResponse = Page.of(page.getPages(),page.getSize());
        pageResponse.setRecords(enterpriseVOS);
        return pageResponse;

    }

}
