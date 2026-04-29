import request from '@/utils/request'

export function getRooms(params) {
  return request({
    url: '/rooms',
    method: 'get',
    params
  })
}

export function getRoom(id) {
  return request({
    url: `/rooms/${id}`,
    method: 'get'
  })
}

export function createRoom(data) {
  return request({
    url: '/rooms',
    method: 'post',
    data
  })
}

export function updateRoom(data) {
  return request({
    url: '/rooms',
    method: 'put',
    data
  })
}

export function deleteRoom(id) {
  return request({
    url: `/rooms/${id}`,
    method: 'delete'
  })
}

export function deleteRooms(ids) {
  return request({
    url: '/rooms/batch',
    method: 'delete',
    data: ids
  })
}
