package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.entity.CompetitionType;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.mapper.entrepreneurPark.PolicyCategoryMapper;
import com.ruoyi.system.service.entrepreneurPark.PolicyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyCategoryServiceImpl extends ServiceImpl<PolicyCategoryMapper, PolicyCategory> implements PolicyCategoryService {

    @Autowired
    private PolicyCategoryMapper policyCategoryMapper;

    @Override
    public Page<PolicyCategory> getPolicyCategoriesPage(int page, int size, String categoryName) {
        Page<PolicyCategory> pageRequest = new Page<>(page, size);

        QueryWrapper<PolicyCategory> queryWrapper = new QueryWrapper<>();

        // 通过 competitionName 进行模糊查询
        if (categoryName != null && !categoryName.isEmpty()) {
            queryWrapper.like("category_name", categoryName);
        }

        List<PolicyCategory> records = policyCategoryMapper.selectPage(pageRequest, queryWrapper).getRecords();

        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        List<PolicyCategory> policyCategoryList = records.stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
        pageRequest.setRecords(policyCategoryList);
        pageRequest.setTotal(records.size());
        return pageRequest;

    }
}
