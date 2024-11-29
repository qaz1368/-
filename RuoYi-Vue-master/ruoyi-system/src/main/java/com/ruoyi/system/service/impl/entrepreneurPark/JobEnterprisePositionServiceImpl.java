package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.DTO.JobEnterprisePositionDTO;
import com.ruoyi.system.domain.entity.Enterprise;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.domain.entity.JobEnterprisePosition;
import com.ruoyi.system.domain.entity.JobPosition;
import com.ruoyi.system.domain.vo.JobEnterprisePositionVO;
import com.ruoyi.system.mapper.entrepreneurPark.EnterpriseMapper;
import com.ruoyi.system.mapper.entrepreneurPark.JobEnterprisePositionMapper;
import com.ruoyi.system.mapper.entrepreneurPark.JobPositionMapper;
import com.ruoyi.system.service.entrepreneurPark.JobEnterprisePositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class JobEnterprisePositionServiceImpl extends ServiceImpl<JobEnterprisePositionMapper, JobEnterprisePosition> implements JobEnterprisePositionService {

    @Autowired
    private JobEnterprisePositionMapper jobEnterprisePositionMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private JobPositionMapper jobPositionMapper;


    @Override
    public Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage(int pageNum, int pageSize,String positionType) {
        Page<JobEnterprisePosition> page = new Page<>(pageNum, pageSize);
        QueryWrapper<JobEnterprisePosition> queryWrapper = new QueryWrapper<>();


        Page<JobEnterprisePosition> resultPage = jobEnterprisePositionMapper.selectPage(page, queryWrapper);
        List<JobEnterprisePosition> jobEnterprisePositions = resultPage.getRecords();

        List<JobEnterprisePositionVO> JobEnterprisePositionVOS = new ArrayList<>();
        for (JobEnterprisePosition jobEnterprisePosition : jobEnterprisePositions) {
            JobEnterprisePositionVO jobEnterprisePositionVO = new JobEnterprisePositionVO();
            BeanUtils.copyProperties(jobEnterprisePosition, jobEnterprisePositionVO);
            // 查询企业名
            Enterprise enterprise = enterpriseMapper.selectById(jobEnterprisePosition.getEnterpriseId());
            if (enterprise != null) {
                jobEnterprisePositionVO.setEnterprise(enterprise.getCompanyName());
            }else{
                jobEnterprisePositionVO.setEnterprise("");
            }

            // 查询
            JobPosition jobPosition = jobPositionMapper.selectById(jobEnterprisePosition.getPositionId());
            if (jobPosition != null) {
                if(positionType != null){
                    if(!jobPosition.getPositionName().equals(positionType)){
                        continue;
                    }
                }
                jobEnterprisePositionVO.setPosition(jobPosition.getPositionName());
            }else {
                jobEnterprisePositionVO.setPosition("");
            }

            JobEnterprisePositionVOS.add(jobEnterprisePositionVO);
        }

        Page<JobEnterprisePositionVO> pageResponse = Page.of(pageNum, pageSize);
        pageResponse.setRecords(JobEnterprisePositionVOS);
        return pageResponse;

    }
    @Override
    public Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage1(int pageNum, int pageSize,String positionType) {
        Page<JobEnterprisePosition> page = new Page<>(pageNum, pageSize);
        QueryWrapper<JobEnterprisePosition> queryWrapper = new QueryWrapper<>();

        Page<JobEnterprisePosition> resultPage = jobEnterprisePositionMapper.selectPage(page, queryWrapper);
        List<JobEnterprisePosition> jobEnterprisePositions = resultPage.getRecords();

        List<JobEnterprisePositionVO> JobEnterprisePositionVOS = new ArrayList<>();
        for (JobEnterprisePosition jobEnterprisePosition : jobEnterprisePositions) {
            if(jobEnterprisePosition.getIsActive() == 0){
                continue;
            }
            JobEnterprisePositionVO jobEnterprisePositionVO = new JobEnterprisePositionVO();
            BeanUtils.copyProperties(jobEnterprisePosition, jobEnterprisePositionVO);

            // 查询企业名
            Enterprise enterprise = enterpriseMapper.selectById(jobEnterprisePosition.getEnterpriseId());
            if (enterprise != null) {
                jobEnterprisePositionVO.setEnterprise(enterprise.getCompanyName());
            }else{
                jobEnterprisePositionVO.setEnterprise("");
            }

            // 查询比赛名
            JobPosition jobPosition = jobPositionMapper.selectById(jobEnterprisePosition.getPositionId());
            if (jobPosition != null) {
                if(positionType != null){
                    if(!jobPosition.getPositionName().equals(positionType)){
                        continue;
                    }
                }
                jobEnterprisePositionVO.setPosition(jobPosition.getPositionName());
            }else {
                jobEnterprisePositionVO.setPosition("");
            }

            JobEnterprisePositionVOS.add(jobEnterprisePositionVO);
        }

        Page<JobEnterprisePositionVO> pageResponse = Page.of(pageNum, pageSize);
        pageResponse.setRecords(JobEnterprisePositionVOS);
        return pageResponse;

    }
    @Override
    public boolean removeBatchByIds(List<Integer> enterpriseJobIds) {
        return this.removeByIds(enterpriseJobIds);
    }

    @Override
    public boolean addJobEnterprisePosition(JobEnterprisePositionDTO jobEnterprisePositionDTO) {
        JobEnterprisePosition jobEnterprisePosition = new JobEnterprisePosition();
        BeanUtils.copyProperties(jobEnterprisePositionDTO, jobEnterprisePosition);
        jobEnterprisePosition.setCreatedAt(LocalDateTime.now());
        jobEnterprisePosition.setUpdatedAt(LocalDateTime.now());
        QueryWrapper<Enterprise> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("company_name", jobEnterprisePositionDTO.getEnterprise());
        // 查询企业名
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper2);
        if (enterprise != null) {
            jobEnterprisePosition.setEnterpriseId(enterprise.getCompanyId());
        }else{
            throw new RuntimeException("企业不存在");
        }
        QueryWrapper<JobPosition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("position_name", jobEnterprisePositionDTO.getPosition());
        // 查询企业名
        JobPosition jobPosition = jobPositionMapper.selectOne(queryWrapper);
        if (jobPosition != null) {
            jobEnterprisePosition.setPositionId(jobPosition.getPositionId());
        }else{
            throw new RuntimeException("岗位不存在");
        }
        return save(jobEnterprisePosition);
    }

    @Override
    public boolean updateJobEnterprisePosition(JobEnterprisePositionDTO jobEnterprisePositionDTO) {
        JobEnterprisePosition jobEnterprisePosition = new JobEnterprisePosition();
        BeanUtils.copyProperties(jobEnterprisePositionDTO, jobEnterprisePosition);
        jobEnterprisePosition.setCreatedAt(LocalDateTime.now());
        jobEnterprisePosition.setUpdatedAt(LocalDateTime.now());
        QueryWrapper<Enterprise> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("company_name", jobEnterprisePositionDTO.getEnterprise());
        // 查询企业名
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper2);
        if (enterprise != null) {
            jobEnterprisePosition.setEnterpriseId(enterprise.getCompanyId());
        }else{
            throw new RuntimeException("企业不存在");
        }
        QueryWrapper<JobPosition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("position_name", jobEnterprisePositionDTO.getPosition());
        // 查询企业名
        JobPosition jobPosition = jobPositionMapper.selectOne(queryWrapper);
        if (jobPosition != null) {
            jobEnterprisePosition.setPositionId(jobPosition.getPositionId());
        }else{
            throw new RuntimeException("岗位不存在");
        }
        if (jobEnterprisePosition.getEnterpriseJobId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }

        // 查询记录是否存在
        JobEnterprisePosition existingRecord = getById(jobEnterprisePosition.getEnterpriseJobId());
        if (existingRecord == null) {
            throw new NoSuchElementException("No record found with ID: " + jobEnterprisePosition.getEnterpriseJobId());
        }

        return updateById(jobEnterprisePosition);
    }

    @Override
    public JobEnterprisePositionVO getJobEnterprisePositionById(Integer enterpriseJobId) {
        JobEnterprisePosition jobEnterprisePosition = getById(enterpriseJobId);
        if (jobEnterprisePosition != null) {
            JobEnterprisePositionVO jobEnterprisePositionVO = new JobEnterprisePositionVO();
            BeanUtils.copyProperties(jobEnterprisePosition, jobEnterprisePositionVO);
            // 查询企业名
            Enterprise enterprise = enterpriseMapper.selectById(jobEnterprisePosition.getEnterpriseId());
            if (enterprise != null) {
                jobEnterprisePositionVO.setEnterprise(enterprise.getCompanyName());
            }else{
                jobEnterprisePositionVO.setEnterprise("");
            }

            // 查询岗位
            JobPosition jobPosition = jobPositionMapper.selectById(jobEnterprisePosition.getPositionId());
            if (jobPosition != null) {
                jobEnterprisePositionVO.setPosition(jobPosition.getPositionName());
            }else {
                jobEnterprisePositionVO.setPosition("");
            }

            return jobEnterprisePositionVO;
        }
        return null;
    }
}
