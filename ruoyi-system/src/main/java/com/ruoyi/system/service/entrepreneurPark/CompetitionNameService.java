package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.CompetitionName;

import java.util.List;

public interface CompetitionNameService extends IService<CompetitionName> {

    boolean saveCompetitionName(CompetitionName competitionName);  // 新增比赛名称

    boolean updateCompetitionName(CompetitionName competitionName); // 更新比赛名称

    boolean deleteCompetitionName(Integer competitionId);  // 删除比赛名称

    boolean deleteBatch(List<Integer> competitionIds);  // 批量删除比赛名称

    IPage<CompetitionName> getCompetitionNamesPage(int page, int size);  // 分页查询比赛名称列表
}
