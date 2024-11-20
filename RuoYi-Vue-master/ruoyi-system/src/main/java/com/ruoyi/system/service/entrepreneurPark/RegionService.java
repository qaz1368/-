package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Region;

import java.util.List;

public interface RegionService extends IService<Region> {

    // 分页查询
    Page<Region> getRegionPage(int page, int size);

    // 批量删除
    boolean deleteBatch(List<Integer> regionIds);

    // 删除单条记录
    boolean deleteRegionById(Integer regionId);

    // 更新记录
    boolean updateRegion(Region region);

    // 新增记录
    boolean saveRegion(Region region);
}
