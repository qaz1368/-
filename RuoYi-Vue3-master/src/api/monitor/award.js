import request from '@/utils/request'

// 查询奖项列表
export function listAward(query) {
  return request({
    url: '/award-details/getAwardDetailsPage',
    method: 'get',
    params: query
  })
}

// 新增奖项列表
export function addAward(data) {
  return request({
    url: '/award-details/add',
    method: 'post',
    data: data
  })
}


// 删除奖项列表
export function delAward(awardId) {
  return request({
    url: `/award-details/delete/${awardId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

// 修改奖项
export function updateAward(data) {
  return request({
    url: '/award-details/update',
    method: 'put',
    data: data
  })
}

//根据award查询奖项信息
export function getAwardByAwardId(awardId) {
  return request({
    url: `/award-details/getAwardDetailsId/${awardId}`,
    method: 'get'
  })
}