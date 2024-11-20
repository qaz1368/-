import request from '@/utils/request'

// 查询创业园列表
export function listPark(query) {
    return request({
        url: '/student-entrepreneurship-park/page',
        method: 'get',
        params: query
    })
}

