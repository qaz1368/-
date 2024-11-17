import request from '@/utils/request'

// 查询申请信息列表
export function listApplication(query) {
  return request({
    url: '/api/application/list',
    method: 'get',
    params: query
  })
}