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

        return policyTagMapper.selectPage(pageRequest, queryWrapper);
    }
}
