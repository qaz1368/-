import request from '@/utils/request'

// 查询地域列表
export function listRegion(query) {
  return request({
    url: '/regions/page',
    method: 'get',
    params: query
  })
}


//根据regionId删除地域信息
export function deleteRegion(regionId) {
  return request({
    url: `/regions/delete/${regionId}`,
    method: 'delete'
  })
}

//根据regionId删除地域信息
export function deleteRegionList(regionIds) {
  return request({
    url: `/regions/batchDelete`,
    method: 'delete',
    data: regionIds
  })
}



// 新增地域信息
export function addRegion(data) {
  return request({
    url: '/regions/add',
    method: 'post',
    data: data
  })
}




//根据tagId查询地域信息
export function getRegionById(regionId) {
  return request({
    url: `/regions/${regionId}`,
    method: 'get',
  })
}


//修改地域信息
export function updateRegion(data) {
  return request({
    url: '/regions/update',
    method: 'put',
    data: data
  })
}

//查询数据库的全部地域名称
export function getRegionOptions() {
  return request({
    url: '/regions/getRegionOptions',
    method: 'get'
  })
}


