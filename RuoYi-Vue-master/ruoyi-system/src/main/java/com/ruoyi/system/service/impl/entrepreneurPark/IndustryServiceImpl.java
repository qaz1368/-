package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.domain.vo.IndustryRankVO;
import com.ruoyi.system.mapper.entrepreneurPark.EnterpriseMapper;
import com.ruoyi.system.mapper.entrepreneurPark.IndustryMapper;
import com.ruoyi.system.service.entrepreneurPark.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndustryServiceImpl extends ServiceImpl<IndustryMapper, Industry> implements IndustryService {

    @Autowired
    private IndustryMapper industryMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public boolean saveIndustry(Industry industry) {
        return save(industry);  // 使用 MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateIndustry(Industry industry) {
        return updateById(industry);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteIndustry(Integer industryId) {
        return removeById(industryId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> industryIds) {
        return removeByIds(industryIds);  // 批量删除
    }

    @Override
    public IPage<Industry> getIndustriesPage(int page, int size) {
        int offset = (page - 1) * size;
        List<Industry> industries = industryMapper.selectPage(offset, size);
        int total = industryMapper.count();
        Page<Industry> pageResult = new Page<>(page, size);
        pageResult.setRecords(industries);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public List<IndustryRankVO> getIndustryRanking(int size) {
       // 获取 enterprise 表中所有数据的总和
        int totalEnterpriseCount = enterpriseMapper.getTotalEnterpriseCount();

        // 获取每个 industry_id 的数量
        List<Map<String, Object>> industryCounts = enterpriseMapper.getIndustryCounts();

        // 计算每个行业的占比
        List<IndustryRankVO> industryRanks = new ArrayList<>();
        for (Map<String, Object> count : industryCounts) {
            Integer industryId = (Integer) count.get("industry_id");
            Long countValue = (Long) count.get("count");
            double percentage = (double) countValue / totalEnterpriseCount * 100;

            IndustryRankVO rank = new IndustryRankVO();
            rank.setIndustryName(industryMapper.getIndustryNameById(industryId));
            rank.setPercentage(percentage);
            industryRanks.add(rank);
        }

        // 按百分比降序排序
        industryRanks.sort((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()));

        // 取前 size 个行业
        List<IndustryRankVO> topIndustries = industryRanks.stream().limit(size).collect(Collectors.toList());

        // 计算其它行业的百分比
        double otherPercentage = 100 - topIndustries.stream().mapToDouble(IndustryRankVO::getPercentage).sum();

        // 如果其它行业的百分比大于 0，添加到结果中
        if (otherPercentage > 0) {
            IndustryRankVO otherRank = new IndustryRankVO();
            otherRank.setIndustryName("其它");
            otherRank.setPercentage(otherPercentage);
            topIndustries.add(otherRank);
        }

        return topIndustries;
    }

}
