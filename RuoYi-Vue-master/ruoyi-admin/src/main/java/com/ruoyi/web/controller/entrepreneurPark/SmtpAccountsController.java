package com.ruoyi.web.controller.entrepreneurPark;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.DTO.SmtpAccountsDTO;
import com.ruoyi.system.domain.entity.SmtpAccounts;
import com.ruoyi.system.domain.vo.SmtpAccountsVO;
import com.ruoyi.system.service.entrepreneurPark.ISmtpAccountsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2024-12-12
 */
@RestController
@RequestMapping("/system/accounts")
@Api(tags ="邮箱管理")
public class SmtpAccountsController extends BaseController
{
    @Autowired
    private ISmtpAccountsService smtpAccountsService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    @ApiOperation("分页查询列表")
    public TableDataInfo list(SmtpAccountsDTO smtpAccountsDTO)
    {
        startPage();
        List<SmtpAccountsVO> list = smtpAccountsService.selectSmtpAccountsList(smtpAccountsDTO);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:accounts:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出【请填写功能名称】列表")
    public void export(HttpServletResponse response, SmtpAccountsDTO smtpAccountsDTO)
    {
        List<SmtpAccountsVO> list = smtpAccountsService.selectSmtpAccountsList(smtpAccountsDTO);
        ExcelUtil<SmtpAccountsVO> util = new ExcelUtil<SmtpAccountsVO>(SmtpAccountsVO.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:accounts:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取【请填写功能名称】详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(smtpAccountsService.selectSmtpAccountsById(id));
    }



    /**
     * 修改邮箱信息
     */
    @PreAuthorize("@ss.hasPermi('system:accounts:edit')")
    @Log(title = "修改邮箱信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改邮箱信息")
    public AjaxResult edit(@RequestBody SmtpAccounts smtpAccounts)
    {
        return toAjax(smtpAccountsService.updateSmtpAccounts(smtpAccounts));
    }




}
