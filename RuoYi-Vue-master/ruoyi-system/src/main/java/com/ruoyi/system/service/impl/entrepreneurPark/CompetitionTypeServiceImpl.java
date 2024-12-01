package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.DTO.CompetitionTypeDTO;
import com.ruoyi.system.domain.entity.CompetitionName;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.entity.Region;
import com.ruoyi.system.mapper.entrepreneurPark.CompetitionTypeMapper;
import com.ruoyi.system.service.entrepreneurPark.CompetitionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionTypeServiceImpl extends ServiceImpl<CompetitionTypeMapper, CompetitionType> implements CompetitionTypeService {

    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;

    @Override
    public boolean saveCompetitionType(CompetitionTypeDTO competitionTypeDTO) {
        CompetitionType competitionType = new CompetitionType();
        competitionType.setLevel(competitionTypeDTO.getLevel());
        return save(competitionType);
    }

    @Override
    public boolean updateCompetitionType(CompetitionType competitionType) {
        return updateById(competitionType);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteCompetitionType(Integer id) {
        return removeById(id);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> ids) {
        return removeByIds(ids);  // 批量删除
    }

    @Override
    public IPage<CompetitionType> getCompetitionTypesPage(int page, int size,String level) {
        // Page构造方法：当前页, 每页显示记录数
        Page<CompetitionType> competitionTypePage = new Page<>(page, size);
        QueryWrapper<CompetitionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(level != null, "level", level);

        List<CompetitionType> records = competitionTypeMapper.selectPage(competitionTypePage, queryWrapper).getRecords();
        Long count = lambdaQuery().select().count();
        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        List<CompetitionType> competitionTypeList = records.stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
        competitionTypePage.setRecords(competitionTypeList);
        competitionTypePage.setTotal(count);
        return competitionTypePage;

    }
}
