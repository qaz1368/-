package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.mapper.entrepreneurPark.PolicyCategoryMapper;
import com.ruoyi.system.mapper.entrepreneurPark.PolicyTagMapper;
import com.ruoyi.system.service.entrepreneurPark.PolicyTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyTagServiceImpl extends ServiceImpl<PolicyTagMapper, PolicyTag> implements PolicyTagService {

    @Autowired
    private PolicyTagMapper policyTagMapper;

    @Override
    public Page<PolicyTag> getPolicyTagsPage(int page, int size, String tagName) {
        Page<PolicyTag> pageRequest = new Page<>(page, size);

        QueryWrapper<PolicyTag> queryWrapper = new QueryWrapper<>();

        // 通过 competitionName 进行模糊查询
        if (tagName != null && !tagName.isEmpty()) {
            queryWrapper.like("tag_name", tagName);
        }

        List<PolicyTag> records = policyTagMapper.selectPage(pageRequest, queryWrapper).getRecords();

        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        List<PolicyTag> policyCategoryList = records.stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
        pageRequest.setRecords(policyCategoryList);
        pageRequest.setTotal(records.size());
        return pageRequest;
    }
}
