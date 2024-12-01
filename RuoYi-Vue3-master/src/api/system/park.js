import request from '@/utils/request'

// 查询创业园列表
export function listPark(query) {
    return request({
        url: '/student-entrepreneurship-park/page',
        method: 'get',
        params: query
    })
}

// 删除创业园列表
export function delPark(parkId) {
  return request({
    url: `/student-entrepreneurship-park/delete/${parkId}`, // 使用模板字符串来插入 awardId
    method: 'delete'
  });
}

//deleteBatch
export function deleteBatch(parkIds) {
    return request({
        url: `/industries/deleteBatch`,
        method: 'delete',
        data: parkIds
    })
}



//新增创业园列表
export function addPark(data) {
  return request({
       url: '/student-entrepreneurship-park/add',
    method: 'post',
    data: data
  });
}

//根据parkId查询企业管理者信息
export function getParkById(parkId) {
  return request({
    url: `/student-entrepreneurship-park/get/${parkId}`,
    method: 'get'
  })
}


// 修改奖项
export function updatePark(data) {
  return request({
    url: '/student-entrepreneurship-park/update',
    method: 'put',
    data: data
  })
}
//查询数据库的全部奖项名称
export function getStudentEntrepreneurshipParkOptions() {
    return request({
        url: '/student-entrepreneurship-park/getStudentEntrepreneurshipParkOptions',
        method: 'get'
    })
}