package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.domain.entity.PolicyTag;
import org.springframework.stereotype.Service;

public interface PolicyTagService extends IService<PolicyTag> {

    Page<PolicyTag> getPolicyTagsPage(int page, int size, String tagName);
}
