import request from '@/utils/request'

// 查询申请信息列表
export function listEnterprise_managers(query) {
  return request({
    url: '/enterprise-managers/page',
    method: 'get',
    params: query
  })
}