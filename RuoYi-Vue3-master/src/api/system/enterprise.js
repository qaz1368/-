import request from '@/utils/request'

// 分页查询企业列表
export function listEnterprise(query) {
  return request({
    url: '/api/enterprise/list',
    method: 'get',
    params: query
  })
}

// 新增企业列表
export function addEnterprise(data) {
  return request({
    url: '/api/enterprise/add',
    method: 'post',
    data: data
  })
}