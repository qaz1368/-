import request from '@/utils/request'

// 查询审批信息列表
export function listApply(query) {
  return request({
    url: '/api/approval/list',
    method: 'get',
    params: query
  })
}
//根据tagId查询标签信息
export function getPost(approvalId) {
  return request({
    url: `/api/approval/${approvalId}`,
    method: 'get'
  })
}

//根据tagId删除标签信息
export function delPost(approvalId) {
  return request({
    url: `/api/approval/delete/${approvalId}`,
    method: 'delete'
  })
}


// 新增标签信息
export function addPost(data) {
  return request({
    url: '/api/approval/add',
    method: 'post',
    data: data
  })
}

// 修改标签信息
export function updatePost(data) {
  return request({
    url: '/api/approval/update',
    method: 'put',
    data: data
  })
}

