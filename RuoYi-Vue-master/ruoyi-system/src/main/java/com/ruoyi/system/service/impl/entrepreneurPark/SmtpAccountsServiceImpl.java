package com.ruoyi.system.service.impl.entrepreneurPark;

import java.util.List;
import java.util.logging.Logger;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.DTO.SmtpAccountsDTO;
import com.ruoyi.system.domain.entity.SmtpAccounts;
import com.ruoyi.system.domain.vo.SmtpAccountsVO;
import com.ruoyi.system.mapper.entrepreneurPark.SmtpAccountsMapper;
import com.ruoyi.system.service.entrepreneurPark.ISmtpAccountsService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-12
 */
@Service
public class SmtpAccountsServiceImpl extends ServiceImpl<SmtpAccountsMapper, SmtpAccounts> implements ISmtpAccountsService
{
    @Autowired
    private SmtpAccountsMapper smtpAccountsMapper;


    @Autowired
    private SysUserServiceImpl sysUserService;


    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public SmtpAccountsVO selectSmtpAccountsById(Long id)
    {
        return smtpAccountsMapper.selectSmtpAccountsById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param smtpAccountsDTO 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SmtpAccountsVO> selectSmtpAccountsList(SmtpAccountsDTO smtpAccountsDTO) {

        List<SmtpAccountsVO> result = smtpAccountsMapper.selectSmtpAccountsList(smtpAccountsDTO);

        return result;
    }



    /**
     * 修改【请填写功能名称】
     * 
     * @param smtpAccounts 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSmtpAccounts(SmtpAccounts smtpAccounts)
    {
        return smtpAccountsMapper.updateSmtpAccounts(smtpAccounts);
    }


}
