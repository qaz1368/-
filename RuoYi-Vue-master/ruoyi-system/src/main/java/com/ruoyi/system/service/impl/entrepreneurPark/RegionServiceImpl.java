package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.domain.entity.Region;
import com.ruoyi.system.mapper.entrepreneurPark.RegionMapper;
import com.ruoyi.system.service.entrepreneurPark.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public Page<Region> getRegionPage(int page, int size) {
        // Page构造方法：当前页, 每页显示记录数
        Page<Region> regionPage = new Page<>(page, size);

        List<Region> records = regionMapper.selectPage(regionPage, null).getRecords();

        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        List<Region> regionList = records.stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
        regionPage.setRecords(regionList);
        regionPage.setTotal(records.size());
        return regionPage;
    }

    @Override
    public boolean deleteBatch(List<Integer> regionIds) {
        return removeByIds(regionIds);
    }

    @Override
    public boolean deleteRegionById(Integer regionId) {
        return removeById(regionId);
    }

    @Override
    public boolean updateRegion(Region region) {
        return updateById(region);
    }

    @Override
    public boolean saveRegion(Region region) {
        return save(region);
    }
}
