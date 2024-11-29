package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.DTO.JobEnterprisePositionDTO;
import com.ruoyi.system.domain.entity.JobEnterprisePosition;
import com.ruoyi.system.domain.vo.JobEnterprisePositionVO;

import java.util.List;

public interface JobEnterprisePositionService extends IService<JobEnterprisePosition> {

    // 分页查询
    Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage(int pageNum, int pageSize,String positionType);
    // 分页查询
    Page<JobEnterprisePositionVO> getJobEnterprisePositionsPage1(int pageNum, int pageSize,String positionType);

    // 批量删除
    boolean removeBatchByIds(List<Integer> enterpriseJobIds);

    boolean addJobEnterprisePosition(JobEnterprisePositionDTO jobEnterprisePositionDTO);

    boolean updateJobEnterprisePosition(JobEnterprisePositionDTO jobEnterprisePositionDTO);

    JobEnterprisePositionVO getJobEnterprisePositionById(Integer enterpriseJobId);
}
