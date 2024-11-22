package com.ruoyi.system.service.impl.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.Enterprise;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class JobEnterprisePositionServiceImpl extends ServiceImpl<JobEnterprisePositionMapper, JobEnterprisePosition> implements JobEnterprisePositionService {

    @Autowired
    private JobEnterprisePositionMapper jobEnterprisePositionMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private JobPositionMapper jobPositionMapper;


    @Override
    public Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage(int pageNum, int pageSize) {
        Page<JobEnterprisePosition> page = new Page<>(pageNum, pageSize);
        Page<JobEnterprisePosition> resultPage = jobEnterprisePositionMapper.selectPage(page, null);
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

            // 查询比赛名
            JobPosition jobPosition = jobPositionMapper.selectById(jobEnterprisePosition.getPositionId());
            if (jobPosition != null) {
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
}
