package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.DTO.AwardDetailDTO;
import com.ruoyi.system.domain.entity.AwardDetail;
import com.ruoyi.system.domain.entity.CompetitionName;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.vo.AwardDetailVO;
import com.ruoyi.system.domain.vo.AwardTypeVO;
import com.ruoyi.system.domain.vo.AwardYearVO;
import com.ruoyi.system.mapper.entrepreneurPark.AwardDetailMapper;
import com.ruoyi.system.mapper.entrepreneurPark.CompetitionNameMapper;
import com.ruoyi.system.mapper.entrepreneurPark.CompetitionTypeMapper;
import com.ruoyi.system.mapper.entrepreneurPark.EnterpriseMapper;
import com.ruoyi.system.service.entrepreneurPark.AwardDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AwardDetailServiceImpl extends ServiceImpl<AwardDetailMapper, AwardDetail> implements AwardDetailService {

    @Autowired
    private AwardDetailMapper awardDetailMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private CompetitionNameMapper competitionNameMapper;

    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;

    @Override
    public boolean saveAwardDetail(AwardDetailDTO awardDetailDTO) {
        AwardDetail awardDetail = new AwardDetail();
        BeanUtils.copyProperties(awardDetailDTO, awardDetail);
        awardDetail.setCreatedAt(new Date());

        // 创建查询条件
        QueryWrapper<CompetitionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", awardDetailDTO.getType());
        // 查询类型
        List<CompetitionType> competitionTypes = competitionTypeMapper.selectList(queryWrapper);

        if (competitionTypes != null && !competitionTypes.isEmpty()) {
            CompetitionType competitionType = competitionTypes.get(0); // 选择第一条记录
            awardDetail.setTypeId(competitionType.getId());
        }

        QueryWrapper<CompetitionName> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("competition_name", awardDetailDTO.getCompetition());
        // 查询比赛名
        CompetitionName competitionName = competitionNameMapper.selectOne(queryWrapper1);
        if (competitionName != null) {
            awardDetail.setCompetitionId(competitionName.getCompetitionId());
        }

        QueryWrapper<Enterprise> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("company_name", awardDetailDTO.getEnterprise());
        // 查询企业名
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper2);
        if (enterprise != null) {
            awardDetail.setEnterpriseId(enterprise.getCompanyId());
        }

        return save(awardDetail);
    }


    @Override
    public boolean updateAwardDetail(AwardDetailDTO awardDetailDTO) {
        AwardDetail awardDetail = new AwardDetail();
        BeanUtils.copyProperties(awardDetailDTO, awardDetail);
        awardDetail.setUpdatedAt(new Date());

         // 创建查询条件
        QueryWrapper<CompetitionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", awardDetailDTO.getType());
        // 查询类型
        CompetitionType competitionType = competitionTypeMapper.selectOne(queryWrapper);
        if (competitionType != null) {
            awardDetail.setTypeId(competitionType.getId());
        }else{
            throw new RuntimeException("类型不存在");
        }

        QueryWrapper<CompetitionName> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("competition_name", awardDetailDTO.getCompetition());
        // 查询比赛名
        CompetitionName competitionName = competitionNameMapper.selectOne(queryWrapper1);
        if (competitionName != null) {
            awardDetail.setEnterpriseId(competitionName.getCompetitionId());
        }else {
            throw new RuntimeException("比赛不存在");
        }


        QueryWrapper<Enterprise> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("company_name", awardDetailDTO.getEnterprise());
        // 查询企业名
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper2);
        if (enterprise != null) {
            awardDetail.setEnterpriseId(enterprise.getCompanyId());
        }else{
            throw new RuntimeException("企业不存在");
        }

        if (awardDetail.getAwardId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }

        // 查询记录是否存在
        AwardDetail existingRecord = getById(awardDetail.getAwardId());
        if (existingRecord == null) {
            throw new NoSuchElementException("No record found with ID: " + awardDetail.getAwardId());
        }

        return updateById(awardDetail);
    }

    @Override
    public boolean deleteAwardDetail(Integer awardId) {
        return removeById(awardId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> awardIds) {
        return removeByIds(awardIds);  // 批量删除
    }


    @Override
    public Page<AwardDetailVO> getAwardDetailsPage(int page, int size, Integer year, Integer typeId) {
        // 计算分页的起始位置
        int offset = (page - 1) * size;

        // 查询数据
        List<AwardDetail> awardDetails = awardDetailMapper.selectPage(offset, size, year, typeId);

        // 查询总记录数
        int total = awardDetailMapper.count(year, typeId);

        // 创建 Page 对象
        Page<AwardDetail> pageResult = new Page<>(page, size);
        pageResult.setRecords(awardDetails);
        pageResult.setTotal(total);

        List<AwardDetailVO> awardDetailVOS = new ArrayList<>();
        for (AwardDetail awardDetail : awardDetails) {
            AwardDetailVO awardDetailVO = new AwardDetailVO();
            awardDetailVO.setAwardId(awardDetail.getAwardId());

            // 查询企业名
            Enterprise enterprise = enterpriseMapper.selectById(awardDetail.getEnterpriseId());
            if (enterprise != null) {
                awardDetailVO.setEnterprise(enterprise.getCompanyName());
            }

            // 查询比赛名
            CompetitionName competitionNames = competitionNameMapper.selectById(awardDetail.getCompetitionId());
            if (competitionNames != null) {
                awardDetailVO.setCompetition(competitionNames.getCompetitionName());
            }

            // 查询级别
            CompetitionType competitionTypes = competitionTypeMapper.selectById(awardDetail.getTypeId());
            if (competitionTypes != null) {
                awardDetailVO.setType(competitionTypes.getLevel());
            }
            BeanUtils.copyProperties(awardDetail, awardDetailVO);
            awardDetailVOS.add(awardDetailVO);
        }

        Long count = lambdaQuery().select().count();
        Page<AwardDetailVO> pageResponse = Page.of(page, size);
        pageResponse.setRecords(awardDetailVOS);
        pageResponse.setTotal(count);
        return pageResponse;

    }

    @Override
    public Page<AwardDetailVO> getAwardDetailsPage1(int page, int size, Integer year, String type) {
        int typeId = 0;
        if (type != null) {
            QueryWrapper<CompetitionType> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("level", type);
            List<CompetitionType> competitionTypes = competitionTypeMapper.selectList(queryWrapper1);

            if (!competitionTypes.isEmpty()) {
                CompetitionType competitionType = competitionTypes.get(0);
                typeId = competitionType.getId();
            } else {
                // 处理没有找到记录的情况
                typeId = 0;
            }
        }

        // 计算分页的起始位置
        int offset = (page - 1) * size;

        // 查询数据
        List<AwardDetail> awardDetails = awardDetailMapper.selectPage(offset, size, year, typeId);

        // 查询总记录数
        int total = awardDetailMapper.count(year, typeId);

        // 创建 Page 对象
        Page<AwardDetail> pageResult = new Page<>(page, size);
        pageResult.setRecords(awardDetails);
        pageResult.setTotal(total);

        List<AwardDetailVO> awardDetailVOS = new ArrayList<>();
        for (AwardDetail awardDetail : awardDetails) {
            AwardDetailVO awardDetailVO = new AwardDetailVO();
            awardDetailVO.setAwardId(awardDetail.getAwardId());

            // 查询企业名
            Enterprise enterprise = enterpriseMapper.selectById(awardDetail.getEnterpriseId());
            if (enterprise != null) {
                awardDetailVO.setEnterprise(enterprise.getCompanyName());
            }

            // 查询比赛名
            CompetitionName competitionNames = competitionNameMapper.selectById(awardDetail.getCompetitionId());
            if (competitionNames != null) {
                awardDetailVO.setCompetition(competitionNames.getCompetitionName());
            }

            // 查询级别
            CompetitionType competitionTypes = competitionTypeMapper.selectById(awardDetail.getTypeId());
            if (competitionTypes != null) {
                awardDetailVO.setType(competitionTypes.getLevel());
            }
            BeanUtils.copyProperties(awardDetail, awardDetailVO);
            awardDetailVOS.add(awardDetailVO);
        }
        Long count = lambdaQuery().select().count();
        Page<AwardDetailVO> pageResponse = Page.of(page, size);
        pageResponse.setRecords(awardDetailVOS);
        pageResponse.setTotal(count);
        return pageResponse;
    }



    @Override
    public List<AwardDetailVO> getAwardDetailsId(Integer enterpriseId) {
        QueryWrapper<AwardDetail> queryWrapper = new QueryWrapper<>();
        if (enterpriseId != null) {
            queryWrapper.eq("enterprise_id", enterpriseId);
        }

        List<AwardDetail> awardDetails = awardDetailMapper.selectList(queryWrapper);

        List<AwardDetailVO> awardDetailVOS = new ArrayList<>();
        for (AwardDetail awardDetail : awardDetails) {
            AwardDetailVO awardDetailVO = new AwardDetailVO();
            awardDetailVO.setAwardId(awardDetail.getAwardId());

            // 查询企业名
            Enterprise enterprise = enterpriseMapper.selectById(awardDetail.getEnterpriseId());
            if (enterprise != null) {
                awardDetailVO.setEnterprise(enterprise.getCompanyName());
            }

            // 查询比赛名
            CompetitionName competitionNames = competitionNameMapper.selectById(awardDetail.getCompetitionId());
            if (competitionNames != null) {
                awardDetailVO.setCompetition(competitionNames.getCompetitionName());
            }

            // 查询级别
            CompetitionType competitionTypes = competitionTypeMapper.selectById(awardDetail.getTypeId());
            if (competitionTypes != null) {
                awardDetailVO.setType(competitionTypes.getLevel());
            }
            BeanUtils.copyProperties(awardDetail, awardDetailVO);
            awardDetailVOS.add(awardDetailVO);
        }

        return awardDetailVOS;
    }

    @Override
    public long getTotalAwardCount() {
        return count();
    }

    @Override
    public BigDecimal getTotalSubsidyAmount() {
        return awardDetailMapper.getTotalSubsidyAmount();
    }

    @Override
    public List<AwardTypeVO> getAwardTypeSummary() {
        List<AwardTypeVO> awardTypeVOS = new ArrayList<>();

        List<CompetitionType> competitionTypes = competitionTypeMapper.selectList(null);
        for (CompetitionType competitionType : competitionTypes) {
            AwardTypeVO awardTypeVO = new AwardTypeVO();
            awardTypeVO.setLevel(competitionType.getLevel());

            BigDecimal totalSubsidyAmount = awardDetailMapper.getTotalSubsidyAmountByTypeId(competitionType.getId());
            awardTypeVO.setTotalSubsidyAmount(totalSubsidyAmount);

            awardTypeVOS.add(awardTypeVO);
        }

        return awardTypeVOS;
    }


    @Override
    public AwardYearVO getAwardDetailstypeId(Integer typeId) {
        AwardYearVO awardYearVO = new AwardYearVO();
        QueryWrapper<CompetitionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId);
        List<CompetitionType> competitionTypes = competitionTypeMapper.selectList(queryWrapper);
        if(!competitionTypes.isEmpty()){
            awardYearVO.setLevel(competitionTypes.get(0).getLevel());
        }
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        List<Integer> awardCounts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int startYear = currentYear - i;
            Long count = awardDetailMapper.countAwardsByTypeIdAndYear(typeId, startYear);
            awardCounts.add(count.intValue());
        }
        awardYearVO.setYear(awardCounts);

        return awardYearVO;
    }
}
