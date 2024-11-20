import request from '@/utils/request'

// 查询创业园列表
export function listPark(query) {
    return request({
        url: '/student-entrepreneurship-park/page',
        method: 'get',
        params: query
    })
}

// 删除奖项列表
export function delPark(parkId) {
  return request({
    url: `/student-entrepreneurship-park/delete/${parkId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}