package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.mapper.entrepreneurPark.JobPositionMapper;
import com.ruoyi.system.service.entrepreneurPark.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPositionServiceImpl extends ServiceImpl<JobPositionMapper, JobPosition> implements JobPositionService {

    @Autowired
    private JobPositionMapper jobPositionMapper;

    @Override
    public Page<JobPosition> getJobPositionsPage(int pageNum, int pageSize, String positionName) {
        Page<JobPosition> page = new Page<>(pageNum, pageSize);

        QueryWrapper<JobPosition> queryWrapper = new QueryWrapper<>();

        // 通过 positionName 进行模糊查询
        if (positionName != null && !positionName.isEmpty()) {
            queryWrapper.like("position_name", positionName);
        }

        Page<JobPosition> page1 = this.page(page, queryWrapper);
        // 定义起始位置和每页大小
        int start = (pageNum - 1) * pageSize; // 起始位置

        List<JobPosition> jobPositionPages = page1.getRecords().stream()
                .skip(start)
                .limit(pageSize)
                .collect(Collectors.toList());
        page1.setRecords(jobPositionPages);
        page1.setTotal(page1.getTotal());
        return page1;

    }

    @Override
    public boolean removeBatchByIds(List<Integer> positionIds) {
        return this.removeByIds(positionIds);
    }
}
