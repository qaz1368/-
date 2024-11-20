import request from '@/utils/request'

// 查询部门列表
export function listType(query) {
  return request({
    url: '/policy-categories/list',
    method: 'get',
    params: query
  })
}




