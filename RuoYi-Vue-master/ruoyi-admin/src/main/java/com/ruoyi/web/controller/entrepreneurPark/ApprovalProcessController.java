package com.ruoyi.web.controller.entrepreneurPark;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.entity.ApprovalProcess;
import com.ruoyi.system.domain.vo.ApprovalProcessVO;
import com.ruoyi.system.service.entrepreneurPark.ApprovalProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "审批流程管理")
@RestController
@RequestMapping("/api/approval-process")
public class ApprovalProcessController {

    @Autowired
    private ApprovalProcessService approvalProcessService;

    @ApiOperation(value = "获取审批流程分页数据", notes = "根据页码和每页数据量获取审批流程分页数据")
    @GetMapping("/list")
    public Page<ApprovalProcessVO> getApprovalProcessPage(
            @ApiParam(value = "页码", required = true) @RequestParam int pageNum,
            @ApiParam(value = "每页数量", required = true) @RequestParam int pageSize) {
        return approvalProcessService.getApprovalProcessPage(pageNum, pageSize);
    }

    @ApiOperation(value = "新增审批流程", notes = "添加新的审批流程")
    @PostMapping("/add")
    public boolean addApprovalProcess(@RequestBody ApprovalProcess approvalProcess) {
        return approvalProcessService.save(approvalProcess);
    }

    @ApiOperation(value = "更新审批流程", notes = "更新已有的审批流程")
    @PutMapping("/update")
    public boolean updateApprovalProcess(@RequestBody ApprovalProcess approvalProcess) {
        return approvalProcessService.updateById(approvalProcess);
    }

    @ApiOperation(value = "删除审批流程", notes = "根据ID删除审批流程")
    @DeleteMapping("/delete/{processId}")
    public boolean deleteApprovalProcess(@PathVariable Integer processId) {
        return approvalProcessService.removeById(processId);
    }

    @ApiOperation(value = "批量删除审批流程", notes = "根据ID列表批量删除审批流程")
    @DeleteMapping("/delete-batch")
    public boolean deleteApprovalProcessBatch(@RequestBody List<Integer> ids) {
        return approvalProcessService.removeByIds(ids);
    }
    @ApiOperation(value = "通过ID获取审批流程", notes = "根据指定的ID获取审批流程信息")
    @GetMapping("/getApprovalProcessById/{processId}")
    public ApprovalProcess getApprovalProcessById(@PathVariable("processId") Long processId) {
        return approvalProcessService.getById(processId);
    }

}
