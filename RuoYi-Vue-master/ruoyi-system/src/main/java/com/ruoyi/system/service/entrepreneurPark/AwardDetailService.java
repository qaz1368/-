package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DTO.AwardDetailDTO;
import com.ruoyi.system.domain.entity.AwardDetail;
import com.ruoyi.system.domain.vo.AwardDetailVO;
import com.ruoyi.system.domain.vo.AwardTypeVO;
import com.ruoyi.system.domain.vo.AwardYearVO;

import java.math.BigDecimal;
import java.util.List;

public interface AwardDetailService extends IService<AwardDetail> {

    boolean saveAwardDetail(AwardDetailDTO awardDetailDTO);  // 新增获奖情况

    boolean updateAwardDetail(AwardDetailDTO awardDetailDTO); // 更新获奖情况信息

    boolean deleteAwardDetail(Integer awardId);  // 删除获奖情况

    boolean deleteBatch(List<Integer> awardIds);  // 批量删除获奖情况

    Page<AwardDetailVO> getAwardDetailsPage(int page, int size, Integer year, Integer typeId);  // 分页查询获奖情况列表

    Page<AwardDetailVO> getAwardDetailsPage1(int page, int size, Integer year, String type);  // 分页查询获奖情况列表

    List<AwardDetailVO> getAwardDetailsId(Integer enterpriseId);

    long getTotalAwardCount();

    BigDecimal getTotalSubsidyAmount();

    List<AwardTypeVO> getAwardTypeSummary();

    AwardYearVO getAwardDetailstypeId(Integer typeId);
}
