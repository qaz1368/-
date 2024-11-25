package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.JobPosition;

import java.util.List;

public interface JobPositionService extends IService<JobPosition> {

    // 分页查询
    Page<JobPosition> getJobPositionsPage(int pageNum, int pageSize, String positionName);

    // 批量删除
    boolean removeBatchByIds(List<Integer> positionIds);
}
