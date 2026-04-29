import request from '@/utils/request'

export function getPosts(params) {
  return request({
    url: '/posts',
    method: 'get',
    params
  })
}

export function getPost(id) {
  return request({
    url: `/posts/${id}`,
    method: 'get'
  })
}

export function createPost(data) {
  return request({
    url: '/posts',
    method: 'post',
    data
  })
}

export function updatePost(data) {
  return request({
    url: '/posts',
    method: 'put',
    data
  })
}

export function deletePost(id) {
  return request({
    url: `/posts/${id}`,
    method: 'delete'
  })
}

export function deletePosts(ids) {
  return request({
    url: '/posts/batch',
    method: 'delete',
    data: ids
  })
}

export function getAllPosts() {
  return request({
    url: '/posts/listAll',
    method: 'get'
  })
}
