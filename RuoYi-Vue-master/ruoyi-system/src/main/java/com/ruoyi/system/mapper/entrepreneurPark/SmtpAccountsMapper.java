package com.ruoyi.system.mapper.entrepreneurPark;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.DTO.SmtpAccountsDTO;
import com.ruoyi.system.domain.entity.Region;
import com.ruoyi.system.domain.entity.SmtpAccounts;
import com.ruoyi.system.domain.vo.SmtpAccountsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-12
 */
@Mapper
public interface SmtpAccountsMapper extends BaseMapper<SmtpAccounts>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    SmtpAccountsVO selectSmtpAccountsById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param smtpAccountsDTO 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
     List<SmtpAccountsVO> selectSmtpAccountsList(SmtpAccountsDTO smtpAccountsDTO);

    /**
     * 新增【请填写功能名称】
     * 
     * @param smtpAccounts 【请填写功能名称】
     * @return 结果
     */
     int insertSmtpAccounts(SmtpAccounts smtpAccounts);

    /**
     * 修改【请填写功能名称】
     * 
     * @param smtpAccounts 【请填写功能名称】
     * @return 结果
     */
     int updateSmtpAccounts(SmtpAccounts smtpAccounts);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
     int deleteSmtpAccountsById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
     int deleteSmtpAccountsByIds(Long[] ids);



}
