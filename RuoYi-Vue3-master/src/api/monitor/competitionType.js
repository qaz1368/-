import request from '@/utils/request'

// 查询比赛名称列表
export function listCompetitionType(query) {
  return request({
    url: '/competition-types/page',
    method: 'get',
    params: query
  })
}


// 新增比赛类型列表
export function addCompetitionType(data) {
  return request({
    url: '/competition-types/add',
    method: 'post',
    data: data
  })
}

