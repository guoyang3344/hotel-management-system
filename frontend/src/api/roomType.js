import request from '@/utils/request'

export function getRoomTypes(params) {
  return request({
    url: '/roomTypes',
    method: 'get',
    params
  })
}

export function getRoomType(id) {
  return request({
    url: `/roomTypes/${id}`,
    method: 'get'
  })
}

export function createRoomType(data) {
  return request({
    url: '/roomTypes',
    method: 'post',
    data
  })
}

export function updateRoomType(data) {
  return request({
    url: '/roomTypes',
    method: 'put',
    data
  })
}

export function deleteRoomType(id) {
  return request({
    url: `/roomTypes/${id}`,
    method: 'delete'
  })
}

export function deleteRoomTypes(ids) {
  return request({
    url: '/roomTypes/batch',
    method: 'delete',
    data: ids
  })
}

export function getAllRoomTypes() {
  return request({
    url: '/roomTypes/listAll',
    method: 'get'
  })
}
