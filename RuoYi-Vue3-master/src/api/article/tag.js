import request from '@/utils/request'

// 查询标签列表
export function listTag(query) {
  return request({
    url: '/policy-tags/list',
    method: 'get',
    params: query
  })
}

//



