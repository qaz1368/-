package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.mapper.entrepreneurPark.IndustryMapper;
import com.ruoyi.system.service.entrepreneurPark.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryServiceImpl extends ServiceImpl<IndustryMapper, Industry> implements IndustryService {

    @Autowired
    private IndustryMapper industryMapper;

    @Override
    public boolean saveIndustry(Industry industry) {
        return save(industry);  // 使用 MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateIndustry(Industry industry) {
        return updateById(industry);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteIndustry(Integer industryId) {
        return removeById(industryId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> industryIds) {
        return removeByIds(industryIds);  // 批量删除
    }

    @Override
    public IPage<Industry> getIndustriesPage(int page, int size) {
        Page<Industry> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // 使用 MyBatis-Plus 提供的分页查询方法
    }
}
