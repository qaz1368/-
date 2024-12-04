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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public boolean updatePolicyArticle(PolicyArticleDTO policyArticleDTO, MultipartFile file) {
        // 获取 articleId 并查询文章
        Integer articleId = policyArticleDTO.getArticleId();
        PolicyArticle policyArticle = getById(articleId);
        if (policyArticle == null) {
            throw new RuntimeException("Article not found with id: " + articleId);
        }

        // 创建 PolicyArticle 实体对象
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
            int insertResult = policyCategoryMapper.insert(policyCategory1);
            if (insertResult <= 0) {
                throw new RuntimeException("分类插入失败");
            } else {
                policyArticle.setCategoryId(policyCategory1.getCategoryId());
            }
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
            } else {
                policyArticle.setPrimaryTagId(policyTag1.getTagId());
            }
        }
        System.out.println("触发了吗1");
        // 处理视频文件
        if (file != null) {
            System.out.println("触发了吗2");
            // 定义文件保存路径 - 使用File.separator确保跨平台兼容性
            String uploadDir = "videos";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 删除原视频文件
            String oldVideoPath = policyArticle.getVideoUrl();
            if (oldVideoPath != null) {
                File oldVideoFile = new File(oldVideoPath);
                if (oldVideoFile.exists()) {
                    boolean isDeleted = oldVideoFile.delete();
                    if (!isDeleted) {
                        throw new RuntimeException("Failed to delete old video file: " + oldVideoFile.getAbsolutePath());
                    }
                }
            }

            // 保存新视频文件
            String newFilePath = uploadDir + File.separator + file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(newFilePath));
            } catch (IOException e) {
                throw new RuntimeException("Failed to save new video file: " + newFilePath, e);
            }

            // 更新 PolicyArticle 对象的 videoUrl
            policyArticle.setVideoUrl(newFilePath);
        }

        // 设置更新时间
        policyArticle.setUpdatedAt(LocalDateTime.now());

        // 保存到数据库
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

    //添加新视频
    @Override
    public boolean addPolicyArticle(PolicyArticleDTO policyArticleDTO, MultipartFile file) throws IOException {
        // 定义文件保存路径 - 使用File.separator确保跨平台兼容性
        String uploadDir = "videos"; // 移除开头的/，使用相对路径
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = null;
        if (file != null) {
            // 构建文件保存路径 - 使用File.separator确保正确的路径分隔
            filePath = uploadDir + File.separator + file.getOriginalFilename();
            // 保存文件
            FileCopyUtils.copy(file.getBytes(), new File(filePath));
        }

        // 其余代码保持不变...
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
            int insertResult = policyCategoryMapper.insert(policyCategory1);
            if (insertResult <= 0) {
                throw new RuntimeException("分类插入失败");
            } else {
                policyArticle.setCategoryId(policyCategory1.getCategoryId());
            }
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
            } else {
                policyArticle.setPrimaryTagId(policyTag1.getTagId());
            }
        }

        // 设置创建时间和更新时间
        policyArticle.setCreatedAt(LocalDateTime.now());
        policyArticle.setUpdatedAt(LocalDateTime.now());
        policyArticle.setVideoUrl(filePath); // 设置文件路径

        // 保存到数据库
        return save(policyArticle);
    }

    //删除视频
    @Override
    public boolean removeArticleById(Integer id) {
        // 根据id查询文章
        PolicyArticle policyArticle = getById(id);

        // 定义上传目录的绝对路径

        // 如果存在视频文件，删除文件
        if (policyArticle != null && policyArticle.getVideoUrl() != null) {
            String videoUrl = policyArticle.getVideoUrl();
            File videoFile = new File( videoUrl); // 构建绝对路径
            if (videoFile.exists()) {
                boolean isDeleted = videoFile.delete();
                if (!isDeleted) {
                    // 处理删除文件失败的情况
                    throw new RuntimeException("Failed to delete video file: " + videoFile.getAbsolutePath());
                }
            }
        }

        // 最后删除该数据
        return removeById(id);
    }

    /**
     * 修改视频
     * @param
     * @param file
     * @return
     */
    @Override
    public boolean updateVideo(PolicyArticleDTO policyArticleDTO, MultipartFile file) throws IOException {
        Integer articleId = policyArticleDTO.getArticleId();
        // 根据id查询文章
        PolicyArticle policyArticle = getById(articleId);
        if (policyArticle == null) {
            throw new RuntimeException("Article not found with id: " + articleId);
        }

        // 定义文件保存路径 - 使用File.separator确保跨平台兼容性
        String uploadDir = "videos";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 删除原视频文件
        String oldVideoPath = policyArticle.getVideoUrl();
        if (oldVideoPath != null) {
            File oldVideoFile = new File(oldVideoPath);
            if (oldVideoFile.exists()) {
                boolean isDeleted = oldVideoFile.delete();
                if (!isDeleted) {
                    throw new RuntimeException("Failed to delete old video file: " + oldVideoFile.getAbsolutePath());
                }
            }
        }

        // 保存新视频文件
        String newFilePath = null;
        if (file != null) {
            newFilePath = uploadDir + File.separator + file.getOriginalFilename();
            FileCopyUtils.copy(file.getBytes(), new File(newFilePath));
        }

        // 更新 PolicyArticle 对象的 videoUrl
        policyArticle.setVideoUrl(newFilePath);
        policyArticle.setUpdatedAt(LocalDateTime.now()); // 更新更新时间

        // 保存到数据库
        return updateById(policyArticle);
    }



}
