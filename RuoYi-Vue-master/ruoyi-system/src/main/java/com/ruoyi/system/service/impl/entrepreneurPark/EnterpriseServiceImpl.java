package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.entity.*;
import com.ruoyi.system.domain.vo.AwardDetailVO;
import com.ruoyi.system.domain.vo.EnterpriseVO;
import com.ruoyi.system.mapper.entrepreneurPark.*;
import com.ruoyi.system.service.entrepreneurPark.AwardDetailService;
import com.ruoyi.system.service.entrepreneurPark.EnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

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

    @Autowired
    private AwardDetailService awardDetailService;

    @Override
    public Page<EnterpriseVO> getEnterprisePage(Page<Enterprise> page, String regionName, String industryName, String companyStatus) {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        if (industryName != null && !industryName.isEmpty()) {
            // 通过 industry 模糊查询获取对应的 id
            List<Integer> industryIds = industryMapper.selectList(Wrappers.<Industry>lambdaQuery()
                            .like(Industry::getIndustryName, industryName))
                    .stream()
                    .map(Industry::getIndustryId)
                    .collect(Collectors.toList());

            if (!industryIds.isEmpty()) {
                queryWrapper.in("industry_id", industryIds);
            }
        }
        // 通过 region 模糊查询获取对应的 id
        if (regionName != null && !regionName.isEmpty()) {
            List<Integer> regionIds = regionMapper.selectList(Wrappers.<Region>lambdaQuery()
                            .like(Region::getRegionName, regionName))
                    .stream()
                    .map(Region::getRegionId)
                    .collect(Collectors.toList());

            if (!regionIds.isEmpty()) {
                queryWrapper.in("region_id", regionIds);
            }
        }
        if (companyStatus != null && !companyStatus.isEmpty()) {
            queryWrapper.eq("company_status", companyStatus);
        }
        queryWrapper.orderByAsc("company_status");
        queryWrapper.orderByAsc("annual_revenue");


        IPage<Enterprise> enterprisePage = enterpriseMapper.selectPage(page, queryWrapper);

        List<Enterprise> enterprisePages = enterprisePage.getRecords();


        List<EnterpriseVO> enterpriseVOS = new ArrayList<>();
        for (Enterprise enterprise : enterprisePages) {
            EnterpriseVO enterpriseVO = new EnterpriseVO();

            // 查询企业名
            Industry industry = industryMapper.selectById(enterprise.getIndustryId());
            if (industry != null) {
                enterpriseVO.setIndustry(industry.getIndustryName());
            } else
                enterpriseVO.setIndustry("暂无");

            // 查询地域
            Region region = regionMapper.selectById(enterprise.getRegionId());
            if (region != null) {
                enterpriseVO.setRegion(region.getRegionName());
            } else
                enterpriseVO.setRegion("暂无");

            // 查询创业园信息
            StudentEntrepreneurshipPark studentEntrepreneurshipPark = studentEntrepreneurshipParkMapper.selectById(enterprise.getIncubatorId());
            if (studentEntrepreneurshipPark != null) {
                enterpriseVO.setIncubator(studentEntrepreneurshipPark.getParkName());
            } else
                enterpriseVO.setIncubator("暂无");

            // 查询企业管理者
            EnterpriseManagers enterpriseManagers = enterpriseManagersMapper.selectById(enterprise.getManagerId());
            if (enterpriseManagers != null) {
                enterpriseVO.setManagerName(enterpriseManagers.getName());
            } else{
                enterpriseVO.setManagerName("暂无");
            }

            BeanUtils.copyProperties(enterprise, enterpriseVO);


            StringBuilder awardsString = new StringBuilder();
            String awardsReceived = enterprise.getAwardsReceived();
            if (awardsReceived != null && !awardsReceived.isEmpty()) {
                 if (awardsReceived.startsWith("[") && awardsReceived.endsWith("]")) {
                        awardsReceived = awardsReceived.substring(1, awardsReceived.length() - 1);
                 }
                String[] awards = awardsReceived.split(",");
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (String award : awards) {
                    List<AwardDetailVO> awardDetails = awardDetailService.getAwardDetailsId(Integer.valueOf(award));
                    for (AwardDetailVO awardDetail : awardDetails) {
                        totalAmount = totalAmount.add(awardDetail.getSubsidyAmount());
                        awardsString.append(awardDetail.getYear()).append(awardDetail.getCompetition())
                                        .append(awardDetail.getLevel()).append("获得补助金额")
                                .append(awardDetail.getSubsidyAmount()).append("元,   ");
                    }
                    awardsString.append(System.lineSeparator());

                }
            enterpriseVO.setAwards(totalAmount);
            enterpriseVO.setAwardsReceived(String.valueOf(awardsString));
            }else{
                enterpriseVO.setAwards(BigDecimal.ZERO);
                enterpriseVO.setAwardsReceived("暂无");
            }


            enterpriseVOS.add(enterpriseVO);
        }

        Page<EnterpriseVO> pageResponse = Page.of(page.getPages(),page.getSize());
        pageResponse.setRecords(enterpriseVOS);
        return pageResponse;

    }

}
