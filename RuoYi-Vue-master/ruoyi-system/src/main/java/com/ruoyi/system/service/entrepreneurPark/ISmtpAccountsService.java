package com.ruoyi.system.service.entrepreneurPark;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DTO.SmtpAccountsDTO;
import com.ruoyi.system.domain.entity.Industry;
import com.ruoyi.system.domain.entity.SmtpAccounts;
import com.ruoyi.system.domain.vo.SmtpAccountsVO;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2024-12-12
 */
public interface ISmtpAccountsService extends IService<SmtpAccounts>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public SmtpAccountsVO selectSmtpAccountsById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param  smtpAccountsDTO 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SmtpAccountsVO> selectSmtpAccountsList(SmtpAccountsDTO smtpAccountsDTO);



    /**
     * 修改【请填写功能名称】
     * 
     * @param smtpAccounts 【请填写功能名称】
     * @return 结果
     */
    public int updateSmtpAccounts(SmtpAccounts smtpAccounts);


}
