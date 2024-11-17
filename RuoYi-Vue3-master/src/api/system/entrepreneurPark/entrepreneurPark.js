import request from '@/utils/request'

//查询企业管理者列表
export function List_enterprise_managers(query) {
  return request({
    url: '/enterprise-managers/page',
    method: 'get',
    params: query
  })
}