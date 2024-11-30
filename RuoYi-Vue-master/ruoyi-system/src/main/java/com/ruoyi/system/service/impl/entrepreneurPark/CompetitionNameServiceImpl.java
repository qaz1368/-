package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.CompetitionName;
import com.ruoyi.system.mapper.entrepreneurPark.CompetitionNameMapper;
import com.ruoyi.system.service.entrepreneurPark.CompetitionNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionNameServiceImpl extends ServiceImpl<CompetitionNameMapper, CompetitionName> implements CompetitionNameService {

    @Autowired
    private CompetitionNameMapper competitionNameMapper;

    @Override
    public boolean saveCompetitionName(CompetitionName competitionName) {
        return save(competitionName);  // 使用 MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateCompetitionName(CompetitionName competitionName) {
        return updateById(competitionName);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteCompetitionName(Integer competitionId) {
        return removeById(competitionId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> competitionIds) {
        return removeByIds(competitionIds);  // 批量删除
    }

    @Override
    public IPage<CompetitionName> getCompetitionNamesPage(int page, int size,String competitionName) {
        // 计算分页起始位置
        int start = (page - 1) * size;

        // 调用 MyBatis 的分页查询方法
        List<CompetitionName> competitionNames = competitionNameMapper.selectCompetitionNamesByPage(start, size, competitionName);

        Long count = lambdaQuery().select().count();
        // 手动封装成 IPage 对象
        IPage<CompetitionName> pageResult = new Page<>(page, size);
        pageResult.setRecords(competitionNames);
        pageResult.setTotal(count);

        return pageResult;
    }
}
