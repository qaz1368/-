import request from '@/utils/request'

// 查询申请信息列表
export function listApplicationType(query) {
  return request({
    url: '/application-type/page',
    method: 'get',
    params: query
  })
}


//更新申请类型
export function updateApplicationType(data) {
  return request({
    url: `/application-type/update`,
    method: 'put',
    data: data
  });
}

//删除申请类型
export function deleteApplicationType(applicationTypeId) {
  return request({
    url: `/application-type/delete/${applicationTypeId}`,
    method: 'delete',
  })
}

//删除申请类型
export function deleteApplicationTypeList(applicationTypeIds) {
  return request({
    url: `/application-type/deleteBatch`,
    method: 'delete',
    data: applicationTypeIds
  })
}

// 通过ID获取申请类型信息
export function getApplicationTypeById(applicationTypeId) {
  return request({
    url: `/application-type/get/${applicationTypeId}`,
    method: 'get',
  })
}

// 新增申请类型
export function addApplicationType(data) {
  return request({
    url: '/application-type/add',
    method: 'post',
    data: data
  })
}