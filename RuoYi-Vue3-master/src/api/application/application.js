import request from '@/utils/request'

// 查询申请信息列表
export function listApplication(query) {
  return request({
    url: '/api/application/list1',
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
export function rejectApplication(data) {
  return request({
    url: `/api/application//approve/reject`,
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

//获得申请类型
export function getIndustryOptions() {
  return request({
    url: '/industries/getIndustryOptions',
    method: 'get'
  })
}

export function getApplicationTypeOptions() {
  return request({
    url: '/application-type/getApplicationTypeOptions',
    method: 'get'
  })
}

//excel导出数据
export function exportApplication(query) {
  return request({
    url: '/api/application/export',
    method: 'get',
    params: query
  })
}