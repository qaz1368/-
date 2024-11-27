package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.DTO.EnterpriseDTO;
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
import java.time.LocalDateTime;
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

    @Override
    public boolean addEnterprise(EnterpriseDTO enterpriseDTO) {
        Enterprise enterprise = new Enterprise();
        BeanUtils.copyProperties(enterpriseDTO,enterprise);
        enterprise.setCreatedAt(LocalDateTime.now());
        enterprise.setUpdatedAt(LocalDateTime.now());
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_name",enterprise.getCompanyName());
        if (count(queryWrapper)>0){
            throw new RuntimeException("企业已存在");
        }
        QueryWrapper<Industry> industryQueryWrapper = new QueryWrapper<>();
        industryQueryWrapper.eq("industry_name",enterpriseDTO.getIndustry());
        Industry industry = industryMapper.selectOne(industryQueryWrapper);
        if (industry==null){
            throw new RuntimeException("行业不存在");
        }
        enterprise.setIndustryId(industry.getIndustryId());

        QueryWrapper<Region> regionQueryWrapper = new QueryWrapper<>();
        regionQueryWrapper.eq("region_name",enterpriseDTO.getRegion());
        Region region = regionMapper.selectOne(regionQueryWrapper);
        if (region==null){
            throw new RuntimeException("地域不存在");
        }
        enterprise.setRegionId(region.getRegionId());

        QueryWrapper<StudentEntrepreneurshipPark> studentEntrepreneurshipParkQueryWrapper = new QueryWrapper<>();
        studentEntrepreneurshipParkQueryWrapper.eq("park_name",enterpriseDTO.getIncubator());
        StudentEntrepreneurshipPark studentEntrepreneurshipPark = studentEntrepreneurshipParkMapper.selectOne(studentEntrepreneurshipParkQueryWrapper);
        if (studentEntrepreneurshipPark==null){
            throw new RuntimeException("创业园不存在");
        }
        enterprise.setIncubatorId(studentEntrepreneurshipPark.getParkId());

        QueryWrapper<EnterpriseManagers> enterpriseManagersQueryWrapper = new QueryWrapper<>();
        enterpriseManagersQueryWrapper.eq("name",enterpriseDTO.getManagerName());
        EnterpriseManagers enterpriseManagers = enterpriseManagersMapper.selectOne(enterpriseManagersQueryWrapper);
        if (enterpriseManagers==null){
            throw new RuntimeException("企业管理者不存在");
        }
        enterprise.setManagerId(enterpriseManagers.getManagerId());

        QueryWrapper<EnterpriseManagers> enterpriseManagersQueryWrapper1 = new QueryWrapper<>();
        enterpriseManagersQueryWrapper1.eq("name",enterpriseDTO.getLegalPerson());
        EnterpriseManagers enterpriseManagers1 = enterpriseManagersMapper.selectOne(enterpriseManagersQueryWrapper1);
        if (enterpriseManagers1==null){
            throw new RuntimeException("企业法人不存在");
        }
        enterprise.setLegalPerson(enterpriseDTO.getLegalPerson());
        enterprise.setCompanyId(null);
        return save(enterprise);
    }

    @Override
    public boolean updateEnterprise(EnterpriseDTO enterpriseDTO) {
        Enterprise enterprise = new Enterprise();
        BeanUtils.copyProperties(enterpriseDTO,enterprise);
        enterprise.setUpdatedAt(LocalDateTime.now());
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_name",enterprise.getCompanyName());
        if (count(queryWrapper)>0){
            throw new RuntimeException("企业已存在");
        }
        QueryWrapper<Industry> industryQueryWrapper = new QueryWrapper<>();
        industryQueryWrapper.eq("industry_name",enterpriseDTO.getIndustry());
        Industry industry = industryMapper.selectOne(industryQueryWrapper);
        if (industry==null){
            throw new RuntimeException("行业不存在");
        }
        enterprise.setIndustryId(industry.getIndustryId());

        QueryWrapper<Region> regionQueryWrapper = new QueryWrapper<>();
        regionQueryWrapper.eq("region_name",enterpriseDTO.getRegion());
        Region region = regionMapper.selectOne(regionQueryWrapper);
        if (region==null){
            throw new RuntimeException("地域不存在");
        }
        enterprise.setRegionId(region.getRegionId());

        QueryWrapper<StudentEntrepreneurshipPark> studentEntrepreneurshipParkQueryWrapper = new QueryWrapper<>();
        studentEntrepreneurshipParkQueryWrapper.eq("park_name",enterpriseDTO.getIncubator());
        StudentEntrepreneurshipPark studentEntrepreneurshipPark = studentEntrepreneurshipParkMapper.selectOne(studentEntrepreneurshipParkQueryWrapper);
        if (studentEntrepreneurshipPark==null){
            throw new RuntimeException("创业园不存在");
        }
        enterprise.setIncubatorId(studentEntrepreneurshipPark.getParkId());

        QueryWrapper<EnterpriseManagers> enterpriseManagersQueryWrapper = new QueryWrapper<>();
        enterpriseManagersQueryWrapper.eq("name",enterpriseDTO.getManagerName());
        EnterpriseManagers enterpriseManagers = enterpriseManagersMapper.selectOne(enterpriseManagersQueryWrapper);
        if (enterpriseManagers==null){
            throw new RuntimeException("企业管理者不存在");
        }
        enterprise.setManagerId(enterpriseManagers.getManagerId());

        QueryWrapper<EnterpriseManagers> enterpriseManagersQueryWrapper1 = new QueryWrapper<>();
        enterpriseManagersQueryWrapper1.eq("legal_person",enterpriseDTO.getLegalPerson());
        EnterpriseManagers enterpriseManagers1 = enterpriseManagersMapper.selectOne(enterpriseManagersQueryWrapper1);
        if (enterpriseManagers1==null){
            throw new RuntimeException("企业管理者不存在");
        }
        enterprise.setLegalPerson(enterpriseDTO.getLegalPerson());

        return updateById(enterprise);
    }

    @Override
    public EnterpriseVO getCompanyId(Integer companyId) {
        Enterprise enterprise = enterpriseMapper.selectById(companyId);
        if (enterprise!=null){
            EnterpriseVO enterpriseVO = new EnterpriseVO();
            BeanUtils.copyProperties(enterprise,enterpriseVO);
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
            return enterpriseVO;
        }
        return null;
    }

}
