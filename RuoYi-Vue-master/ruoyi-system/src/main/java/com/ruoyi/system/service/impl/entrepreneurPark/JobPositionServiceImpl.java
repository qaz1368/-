package com.ruoyi.system.service.impl.entrepreneurPark;

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
    public Page<JobPosition> getJobPositionsPage(int pageNum, int pageSize) {
        Page<JobPosition> page = new Page<>(pageNum, pageSize);
        return this.page(page);
    }

    @Override
    public boolean removeBatchByIds(List<Integer> positionIds) {
        return this.removeByIds(positionIds);
    }
}
