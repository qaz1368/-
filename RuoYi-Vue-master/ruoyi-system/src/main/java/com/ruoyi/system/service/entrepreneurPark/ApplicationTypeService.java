package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.ApplicationType;

import java.util.List;

public interface ApplicationTypeService extends IService<ApplicationType> {

    boolean saveApplicationType(ApplicationType applicationType);  // 新增申请类型

    boolean updateApplicationType(ApplicationType applicationType); // 更新申请类型

    boolean deleteApplicationType(Integer applicationTypeId);  // 删除申请类型

    boolean deleteBatch(List<Integer> applicationTypeIds);  // 批量删除

    IPage<ApplicationType> getApplicationTypePage(int page, int size, String applicationName);  // 分页查询申请类型列表
}
