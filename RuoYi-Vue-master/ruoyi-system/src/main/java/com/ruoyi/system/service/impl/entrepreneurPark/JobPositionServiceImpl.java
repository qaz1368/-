package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.mapper.entrepreneurPark.JobPositionMapper;
import com.ruoyi.system.service.entrepreneurPark.JobPositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPositionServiceImpl extends ServiceImpl<JobPositionMapper, JobPosition> implements JobPositionService {

    @Override
    public Page<JobPosition> getJobPositionsPage(int pageNum, int pageSize, String positionName) {
        Page<JobPosition> page = new Page<>(pageNum, pageSize);

        QueryWrapper<JobPosition> queryWrapper = new QueryWrapper<>();

        // 通过 positionName 进行模糊查询
        if (positionName != null && !positionName.isEmpty()) {
            queryWrapper.like("position_name", positionName);
        }

        return this.page(page, queryWrapper);
    }

    @Override
    public boolean removeBatchByIds(List<Integer> positionIds) {
        return this.removeByIds(positionIds);
    }
}
