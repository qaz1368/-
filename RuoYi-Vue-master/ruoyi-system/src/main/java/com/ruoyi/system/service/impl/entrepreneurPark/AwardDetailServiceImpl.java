package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.AwardDetail;
import com.ruoyi.system.mapper.entrepreneurPark.AwardDetailMapper;
import com.ruoyi.system.service.entrepreneurPark.AwardDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardDetailServiceImpl extends ServiceImpl<AwardDetailMapper, AwardDetail> implements AwardDetailService {

    @Autowired
    private AwardDetailMapper awardDetailMapper;

    @Override
    public boolean saveAwardDetail(AwardDetail awardDetail) {
        return save(awardDetail);  // 使用 MyBatis-Plus 提供的保存方法
    }

    @Override
    public boolean updateAwardDetail(AwardDetail awardDetail) {
        return updateById(awardDetail);  // 使用 MyBatis-Plus 提供的更新方法
    }

    @Override
    public boolean deleteAwardDetail(Integer awardId) {
        return removeById(awardId);  // 使用 MyBatis-Plus 提供的删除方法
    }

    @Override
    public boolean deleteBatch(List<Integer> awardIds) {
        return removeByIds(awardIds);  // 批量删除
    }

    @Override
    public IPage<AwardDetail> getAwardDetailsPage(int page, int size) {
        Page<AwardDetail> pageRequest = new Page<>(page, size);
        return page(pageRequest);  // 使用 MyBatis-Plus 提供的分页查询方法
    }
}
