package com.ruoyi.system.service.impl.entrepreneurPark;

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
    public IPage<CompetitionName> getCompetitionNamesPage(int page, int size) {
        Page<CompetitionName> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // 使用 MyBatis-Plus 提供的分页查询方法
    }
}
