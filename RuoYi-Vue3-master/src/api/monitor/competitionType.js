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

// 删除比赛类型
export function delCompetitionType(competitionId) {
  return request({
    url: `/competition-types/delete/${competitionId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}
//批量删除比赛类型
export function delCompetitionTypeList(ids) {
  return request({
    url: `/competition-types/deleteBatch`,
    method: 'delete',
    data: ids
  })
}



//查询数据库全部的比赛类型
export function getCompetitionType() {
  return request({
    url: '/competition-types/getAllCompetitionType',
    method: 'get'
  })
}

//根据id查询全部的比赛类型
export function getCompetitionTypeById(id) {
  return request({
    url: `/competition-types/getCompetitionTypeById/${id}`,
    method: 'get',
  })
}

//更新比赛类型
export function updateCompetitionType(data) {
  return request({
    url: '/competition-types/update',
    method: 'put',
    data: data
  })
}