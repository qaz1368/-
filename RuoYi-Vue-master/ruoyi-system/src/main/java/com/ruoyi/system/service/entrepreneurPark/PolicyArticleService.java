package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DTO.PolicyArticleDTO;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.vo.PolicyArticleVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PolicyArticleService extends IService<PolicyArticle> {


    boolean addPolicyArticle(PolicyArticleDTO policyArticleDTO, MultipartFile file) throws IOException;

    boolean updatePolicyArticle(PolicyArticleDTO policyArticleDTO, MultipartFile file);

    PolicyArticleVO getPolicyArticleById(Integer id);

    Page<PolicyArticleVO> getPolicyArticles(int page, int size,String categoryName,String tagName);

    boolean removeArticleById(Integer id);

    boolean updateVideo(PolicyArticleDTO policyArticleDTO, MultipartFile file) throws IOException;
}
