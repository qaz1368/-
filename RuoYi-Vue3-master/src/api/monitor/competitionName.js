import request from '@/utils/request'

// 查询定时任务调度列表
export function listCompetitionName(query) {
  return request({
    url: '/competition-names/page',
    method: 'get',
    params: query
  })
}

// 新增比赛名称列表
export function addCompetitionName(data) {
  return request({
    url: '/competition-names/add',
    method: 'post',
    data: data
  })
}

// 删除奖项名称列表
export function delCompetitionName(competitionId) {
  return request({
    url: `/competition-names/delete/${competitionId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//根据award查询奖项信息
export function getAwardByCompetition_id(competitionId) {
  return request({
    url: `/competition-names/getCompetitionName/${competitionId}`,
    method: 'get'
  })
}

//修改奖项名称信息
export function updateCompetitionName(data) {
  return request({
    url: '/competition-names/update',
    method: 'put',
    data: data
  })
}