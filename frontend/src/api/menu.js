import request from '@/utils/request'

export function getMenus(params) {
  return request({
    url: '/menus',
    method: 'get',
    params
  })
}

export function getMenu(id) {
  return request({
    url: `/menus/${id}`,
    method: 'get'
  })
}

export function createMenu(data) {
  return request({
    url: '/menus',
    method: 'post',
    data
  })
}

export function updateMenu(data) {
  return request({
    url: '/menus',
    method: 'put',
    data
  })
}

export function deleteMenu(id) {
  return request({
    url: `/menus/${id}`,
    method: 'delete'
  })
}

export function getMenuTree() {
  return request({
    url: '/menus/tree',
    method: 'get'
  })
}
