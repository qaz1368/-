

//查询数据库的全部行业名称
import request from "@/util/request";

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

