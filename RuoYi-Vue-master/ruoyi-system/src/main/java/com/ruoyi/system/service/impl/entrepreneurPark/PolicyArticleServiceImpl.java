package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DTO.PolicyArticleDTO;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.domain.entity.PolicyArticle;
import com.ruoyi.system.domain.entity.PolicyCategory;
import com.ruoyi.system.domain.entity.PolicyTag;
import com.ruoyi.system.domain.vo.PolicyArticleVO;
import com.ruoyi.system.mapper.entrepreneurPark.PolicyArticleMapper;
import com.ruoyi.system.mapper.entrepreneurPark.PolicyCategoryMapper;
import com.ruoyi.system.mapper.entrepreneurPark.PolicyTagMapper;
import com.ruoyi.system.service.entrepreneurPark.PolicyArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyArticleServiceImpl extends ServiceImpl<PolicyArticleMapper, PolicyArticle> implements PolicyArticleService {

    @Autowired
    private PolicyTagMapper policyTagMapper;

    @Autowired
    private PolicyCategoryMapper policyCategoryMapper;

    @Autowired
    private PolicyArticleMapper policyArticleMapper;

    @Override
    public boolean addPolicyArticle(PolicyArticleDTO policyArticleDTO) {
        // 创建 PolicyArticle 实体对象
        PolicyArticle policyArticle = new PolicyArticle();
        BeanUtils.copyProperties(policyArticleDTO, policyArticle);

        // 查询 category_id
        PolicyCategory policyCategory = policyCategoryMapper.selectOne(
                new QueryWrapper<PolicyCategory>()
                        .eq("category_name", policyArticleDTO.getCategory())
        );
        if (policyCategory != null) {
            policyArticle.setCategoryId(policyCategory.getCategoryId());
        } else {
            // 处理 category 不存在的情况
            PolicyCategory policyCategory1 = new PolicyCategory();
            policyCategory1.setCategoryName(policyArticleDTO.getCategory());
            policyCategory1.setCreatedAt(LocalDateTime.now());
            policyCategory1.setUpdatedAt(LocalDateTime.now());
            int insertResult =policyCategoryMapper.insert(policyCategory1);
            if (insertResult <= 0) {
                throw new RuntimeException("分类插入失败");
            }else
                policyArticle.setCategoryId(policyCategory1.getCategoryId());
        }

        // 查询 primary_tag_id
        PolicyTag policyTag = policyTagMapper.selectOne(
                new QueryWrapper<PolicyTag>()
                        .eq("tag_name", policyArticleDTO.getPrimaryTag())
        );
        if (policyTag != null) {
            policyArticle.setPrimaryTagId(policyTag.getTagId());
        } else {
            PolicyTag policyTag1 = new PolicyTag();
            policyTag1.setTagName(policyArticleDTO.getPrimaryTag());
            policyTag1.setCreatedAt(LocalDateTime.now());
            policyTag1.setUpdatedAt(LocalDateTime.now());
            int insertResult = policyTagMapper.insert(policyTag1);
            if (insertResult <= 0) {
                throw new RuntimeException("标签插入失败");
            }else
                policyArticle.setPrimaryTagId(policyTag1.getTagId());
        }

        // 设置创建时间和更新时间
        policyArticle.setCreatedAt(LocalDateTime.now());
        policyArticle.setUpdatedAt(LocalDateTime.now());

        // 保存到数据库
        return save(policyArticle);
    }

    @Override
    public boolean updatePolicyArticle(PolicyArticleDTO policyArticleDTO) {
         // 创建 PolicyArticle 实体对象
        PolicyArticle policyArticle = new PolicyArticle();
        BeanUtils.copyProperties(policyArticleDTO, policyArticle);

        // 查询 category_id
        PolicyCategory policyCategory = policyCategoryMapper.selectOne(
                new QueryWrapper<PolicyCategory>()
                        .eq("category_name", policyArticleDTO.getCategory())
        );
        if (policyCategory != null) {
            policyArticle.setCategoryId(policyCategory.getCategoryId());
        } else {
            // 处理 category 不存在的情况
            PolicyCategory policyCategory1 = new PolicyCategory();
            policyCategory1.setCategoryName(policyArticleDTO.getCategory());
            policyCategory1.setCreatedAt(LocalDateTime.now());
            policyCategory1.setUpdatedAt(LocalDateTime.now());
            int insertResult =policyCategoryMapper.insert(policyCategory1);
            if (insertResult <= 0) {
                throw new RuntimeException("分类插入失败");
            }else
                policyArticle.setCategoryId(policyCategory1.getCategoryId());
        }

        // 查询 primary_tag_id
        PolicyTag policyTag = policyTagMapper.selectOne(
                new QueryWrapper<PolicyTag>()
                        .eq("tag_name", policyArticleDTO.getPrimaryTag())
        );
        if (policyTag != null) {
            policyArticle.setPrimaryTagId(policyTag.getTagId());
        } else {
            PolicyTag policyTag1 = new PolicyTag();
            policyTag1.setTagName(policyArticleDTO.getPrimaryTag());
            policyTag1.setCreatedAt(LocalDateTime.now());
            policyTag1.setUpdatedAt(LocalDateTime.now());
            int insertResult = policyTagMapper.insert(policyTag1);
            if (insertResult <= 0) {
                throw new RuntimeException("标签插入失败");
            }else
                policyArticle.setPrimaryTagId(policyTag1.getTagId());
        }
        // 设置更新时间
        policyArticle.setUpdatedAt(LocalDateTime.now());

        return updateById(policyArticle);
    }

    @Override
    public PolicyArticleVO getPolicyArticleById(Integer id) {
        PolicyArticle policyArticle = getById(id);
        if (policyArticle != null) {
            // 查询分类名称
            PolicyCategory policyCategory = policyCategoryMapper.selectById(policyArticle.getCategoryId());
            String categoryName = policyCategory.getCategoryName();
            // 查询标签名称
            PolicyTag policyTag = policyTagMapper.selectById(policyArticle.getPrimaryTagId());
            String primaryTagName = policyTag.getTagName();
            // 封装VO对象
            PolicyArticleVO policyArticleVO = new PolicyArticleVO();
            BeanUtils.copyProperties(policyArticle, policyArticleVO);
            policyArticleVO.setCategory(categoryName);
            policyArticleVO.setPrimaryTag(primaryTagName);
            return policyArticleVO;
        }
        return null;
    }

    @Override
    public Page<PolicyArticleVO> getPolicyArticles(int page, int size,String categoryName,String tagName) {

        // 通过 categoryName 模糊查询 PolicyCategory 表
        QueryWrapper<PolicyCategory> categoryQueryWrapper = new QueryWrapper<>();
        if (categoryName != null && !categoryName.isEmpty()) {
            categoryQueryWrapper.like("category_name", categoryName);
        }
        List<PolicyCategory> categories = policyCategoryMapper.selectList(categoryQueryWrapper);
        List<Integer> categoryIds = categories.stream().map(PolicyCategory::getCategoryId).collect(Collectors.toList());

        // 通过 tagName 模糊查询 PolicyTag 表
        QueryWrapper<PolicyTag> tagQueryWrapper = new QueryWrapper<>();
        if (tagName != null && !tagName.isEmpty()) {
            tagQueryWrapper.like("tag_name", tagName);
        }
        List<PolicyTag> tags = policyTagMapper.selectList(tagQueryWrapper);
        List<Integer> tagIds = tags.stream().map(PolicyTag::getTagId).collect(Collectors.toList());

        // 创建 PolicyArticle 的 QueryWrapper
        QueryWrapper<PolicyArticle> articleQueryWrapper = new QueryWrapper<>();

        // 添加 category_id 和 tag_id 的查询条件
        if (!categoryIds.isEmpty()) {
            articleQueryWrapper.in("category_id", categoryIds);
        }
        if (!tagIds.isEmpty()) {
            articleQueryWrapper.in("primary_tag_id", tagIds);
        }

        Page<PolicyArticle> policyArticlePage = new Page<>(page, size);

       Page<PolicyArticle> resultPage = policyArticleMapper.selectPage(policyArticlePage, articleQueryWrapper);

        // 定义起始位置和每页大小
        int start = (page - 1) * size; // 起始位置

        List<PolicyArticle> records = resultPage.getRecords().stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());

        List<PolicyArticleVO> policyArticleVOList = new ArrayList<>();
        for (PolicyArticle policyArticle : records) {
            PolicyArticleVO policyArticleVO = new PolicyArticleVO();
            BeanUtils.copyProperties(policyArticle, policyArticleVO);

            // 查询 category_name
            PolicyCategory policyCategory = policyCategoryMapper.selectById(policyArticle.getCategoryId());
            if (policyCategory != null) {
                policyArticleVO.setCategory(policyCategory.getCategoryName());
            }

            // 查询 tag_name
            PolicyTag policyTag = policyTagMapper.selectById(policyArticle.getPrimaryTagId());
            if (policyTag != null) {
                policyArticleVO.setPrimaryTag(policyTag.getTagName());
            }

            policyArticleVOList.add(policyArticleVO);
        }
        Long count = lambdaQuery().select().count();
        // 创建分页结果对象
        Page<PolicyArticleVO> resultVOPage = new Page<>(page, size);
        resultVOPage.setTotal(resultPage.getTotal());
        resultVOPage.setRecords(policyArticleVOList);
        resultVOPage.setTotal(count);
        return resultVOPage;
    }


}
