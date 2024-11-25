package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.PolicyCategory;
import org.springframework.stereotype.Service;


public interface PolicyCategoryService extends IService<PolicyCategory> {

    Page<PolicyCategory> getPolicyCategoriesPage(int page, int size, String categoryName);
}
