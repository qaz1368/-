import request from '@/utils/request'

// 审批流程管理
export function getApprovalProcessPage(query) {
  return request({
    url: '/api/approval-process/list',
    method: 'get',
    params: query
  })
}
//通过ID获取审批流程
export function getApprovalProcessById(processId) {
  return request({
    url: `/api/approval-process/getApprovalProcessById/${processId}`,
    method: 'get'
  })
}

//删除审批流程
export function deleteApprovalProcess(processId) {
  return request({
    url: `/api/approval-process/delete/${processId}`,
    method: 'delete'
  })
}

//processIds
export function deleteApprovalProcessList(processIds) {
  return request({
    url: `/api/approval-process/delete-batch`,
    method: 'delete',
    data: processIds
  })
}

// 新增审批流程
export function addApprovalProcess(data) {
  return request({
    url: '/api/approval-process/add',
    method: 'post',
    data: data
  })
}

// 更新审批流程
export function updateApprovalProcess(data) {
  return request({
    url: '/api/approval-process/update',
    method: 'put',
    data: data
  })
}

//查询数据库的全部部门信息
export function selectDeptList() {
  return request({
    url: '/system/dept/list1',
    method: 'get'
  })
}

//查询数据库的角色信息
export function optionselect1() {
  return request({
    url: '/system/role/optionselect1',
    method: 'get'
  })
}

//查询数据库的申请类型
export function getApplicationTypeOptions() {
  return request({
    url: '/application-type/getApplicationTypeOptions',
    method: 'get'
  })
}




