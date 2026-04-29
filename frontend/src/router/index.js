import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: () => import('@/layout/index.vue'),
    redirect: '/index',
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/index/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      },
      {
        path: 'room/list',
        name: 'RoomList',
        component: () => import('@/views/room/roomList.vue'),
        meta: { title: '房间列表', icon: 'OfficeBuilding' }
      },
      {
        path: 'room/type',
        name: 'RoomTypeList',
        component: () => import('@/views/room/roomTypeList.vue'),
        meta: { title: '房型管理', icon: 'Setting' }
      },
      {
        path: 'system/user',
        name: 'UserList',
        component: () => import('@/views/system/userList.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'system/role',
        name: 'RoleList',
        component: () => import('@/views/system/roleList.vue'),
        meta: { title: '角色管理', icon: 'UserFilled' }
      },
      {
        path: 'system/menu',
        name: 'MenuList',
        component: () => import('@/views/system/menuList.vue'),
        meta: { title: '菜单管理', icon: 'Menu' }
      },
      {
        path: 'system/dept',
        name: 'DeptList',
        component: () => import('@/views/system/deptList.vue'),
        meta: { title: '部门管理', icon: 'OfficeBuilding' }
      },
      {
        path: 'system/post',
        name: 'PostList',
        component: () => import('@/views/system/postList.vue'),
        meta: { title: '岗位管理', icon: 'UserAdd' }
      },
      {
        path: 'system/dict',
        name: 'DictList',
        component: () => import('@/views/system/dictList.vue'),
        meta: { title: '字典管理', icon: 'Reading' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  
  if (to.path === '/login') {
    if (userStore.isLoggedIn) {
      next('/index')
    } else {
      next()
    }
  } else {
    if (userStore.isLoggedIn) {
      if (!userStore.userInfo) {
        try {
          await userStore.fetchUserInfo()
        } catch (error) {
          userStore.logoutUser()
          next('/login')
          return
        }
      }
      next()
    } else {
      next('/login')
    }
  }
})

export default router
