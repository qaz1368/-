import request from '@/utils/request'

// 分页查询企业列表
export function listMenu(query) {
  return request({
    url: '/api/enterprise/list',
    method: 'get',
    params: query
  })
}

