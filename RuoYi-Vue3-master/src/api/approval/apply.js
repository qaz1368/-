import request from '@/utils/request'

// 查询审批信息列表
export function listApply(query) {
  return request({
    url: '/api/approval/list1',
    method: 'get',
    params: query
  })
}

