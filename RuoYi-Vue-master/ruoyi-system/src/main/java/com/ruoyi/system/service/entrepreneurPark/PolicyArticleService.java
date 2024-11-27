package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DTO.PolicyArticleDTO;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.vo.PolicyArticleVO;


public interface PolicyArticleService extends IService<PolicyArticle> {


    boolean addPolicyArticle(PolicyArticleDTO policyArticleDTO);

    boolean updatePolicyArticle(PolicyArticleDTO policyArticleDTO);

    PolicyArticleVO getPolicyArticleById(Integer id);

    Page<PolicyArticleVO> getPolicyArticles(int page, int size,String categoryName,String tagName);

}
