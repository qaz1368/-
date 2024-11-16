package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.entity.AwardDetail;

import java.util.List;

public interface AwardDetailService extends IService<AwardDetail> {

    boolean saveAwardDetail(AwardDetail awardDetail);  // 新增获奖情况

    boolean updateAwardDetail(AwardDetail awardDetail); // 更新获奖情况信息

    boolean deleteAwardDetail(Integer awardId);  // 删除获奖情况

    boolean deleteBatch(List<Integer> awardIds);  // 批量删除获奖情况

    IPage<AwardDetail> getAwardDetailsPage(int page, int size);  // 分页查询获奖情况列表
}
