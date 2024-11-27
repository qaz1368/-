import request from '@/utils/request'

// 查询申请信息列表
export function listApplication(query) {
  return request({
    url: '/api/application/list',
    method: 'get',
    params: query
  })
}


//通过申请
export function passApplication(data) {
  return request({
    url: `/api/application/approve/pass`,
    method: 'post',
    data: data
  });
}

//拒绝申请
export function rejectApplication(applicationId,data) {
  return request({
    url: `/api/application/reject/${applicationId}`,
    method: 'post',
    data: data
  })
}

// 新增申请
export function addApplication(data) {
  return request({
    url: '/api/application/add',
    method: 'post',
    data: data
  })
}