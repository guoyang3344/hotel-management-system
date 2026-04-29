import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, logout, getInfo } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)
  const menus = ref([])
  const permissions = ref([])

  const isLoggedIn = computed(() => !!token.value)

  async function handleLogin(loginForm) {
    try {
      const res = await login(loginForm)
      token.value = res.data.token
      userInfo.value = res.data.user
      menus.value = res.data.menus
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('userInfo', JSON.stringify(res.data.user))
      localStorage.setItem('menus', JSON.stringify(res.data.menus))
      return res
    } catch (error) {
      throw error
    }
  }

  async function handleLogout() {
    try {
      await logout()
    } finally {
      logoutUser()
    }
  }

  function logoutUser() {
    token.value = ''
    userInfo.value = null
    menus.value = []
    permissions.value = []
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('menus')
  }

  async function fetchUserInfo() {
    try {
      const res = await getInfo()
      userInfo.value = res.data.user
      menus.value = res.data.menus
      localStorage.setItem('userInfo', JSON.stringify(res.data.user))
      localStorage.setItem('menus', JSON.stringify(res.data.menus))
      return res
    } catch (error) {
      throw error
    }
  }

  function restoreFromStorage() {
    const storedUserInfo = localStorage.getItem('userInfo')
    const storedMenus = localStorage.getItem('menus')
    if (storedUserInfo) {
      userInfo.value = JSON.parse(storedUserInfo)
    }
    if (storedMenus) {
      menus.value = JSON.parse(storedMenus)
    }
  }

  return {
    token,
    userInfo,
    menus,
    permissions,
    isLoggedIn,
    handleLogin,
    handleLogout,
    logoutUser,
    fetchUserInfo,
    restoreFromStorage
  }
})
