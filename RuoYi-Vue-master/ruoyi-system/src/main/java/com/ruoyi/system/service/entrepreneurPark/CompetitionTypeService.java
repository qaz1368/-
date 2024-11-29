package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.DTO.CompetitionTypeDTO;
import com.ruoyi.system.domain.entity.CompetitionType;

import java.util.List;

public interface CompetitionTypeService extends IService<CompetitionType> {

    boolean saveCompetitionType(CompetitionTypeDTO competitionTypeDTO);  // 新增比赛类型

    boolean updateCompetitionType(CompetitionType competitionType); // 更新比赛类型信息

    boolean deleteCompetitionType(Integer id);  // 删除比赛类型

    boolean deleteBatch(List<Integer> ids);  // 批量删除比赛类型

    IPage<CompetitionType> getCompetitionTypesPage(int page, int size,String level);  // 分页查询比赛类型列表
}
