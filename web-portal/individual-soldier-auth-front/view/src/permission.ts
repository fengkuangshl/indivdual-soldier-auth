import router from './router'
import { NavigationGuardNext, RawLocation, Route, RouteConfig, RouteMeta } from 'vue-router'
import { PermissionModule } from '@/store/permission-store'
import getPageTitle from './common/utils/page-title'
import { MenuModule } from './store/menu-store'
import { MenuResponse } from './views/index/system/menu/interface/sys-menu'
import { CurrentMenuApi } from './views/index/system/menu/menu-api'
import { LoginSuccessUserInfo } from './views/index/system/user/interface/sys-user'
import { UserInfoApi } from './views/index/system/user/user-api'
import { UserModule } from './store/user-store'
import { local } from './store'
import settings from '@/settings'
// 导入NProgress 对应的js和css
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { Message } from 'element-ui'
import { SocketModule } from './store/web-socket-store'

// const whiteList = ['/login', '/auth-redirect', '/registe', '/404']

router.beforeEach(async (to: Route, from: Route, next: NavigationGuardNext): Promise<void> => {
  NProgress.start()
  // to 将访问哪一个路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数,表示放行
  //   next() 放行 next('/login') 强制跳转
  if (to.path === '/login') {
    return next()
  }
  // 获取token
  const refreshToken = local.getAny(settings.refreshToken)
  if (!refreshToken) {
    return next('/login')
  } else {
    const dynamicRoutes: Array<RouteConfig> = PermissionModule.getDynamicRoutes
    if (dynamicRoutes.length === 0) {
      getUserInfo(to, from, next)
    } else {
      next()
    }
  }
})

router.afterEach((to: Route) => {
  NProgress.done()
  // set page title
  document.title = getPageTitle((to.meta as RouteMeta).title)
})

export const getUserInfo = async (to: Route, from: Route, next: NavigationGuardNext): Promise<void> => {
  const { code, data, msg }: KWResponse.Result<LoginSuccessUserInfo> = await UserInfoApi()
  console.log(data)
  if (code === 200) {
    UserModule.changeUser(data)
    getMenus(to, from, next)
  } else {
    Message.error(msg || '获取用户失败！')
  }
}

export const getMenus = async (to: Route, from: Route, next: NavigationGuardNext): Promise<void> => {
  const { code, data, msg }: KWResponse.Result<Array<MenuResponse>> = await CurrentMenuApi()
  if (code === 200) {
    console.log(data)
    const menus: Array<MenuResponse> = data // data.filter(item => item.name.indexOf('vue') > -1) // 暂时先这么处理
    // this.menus = data
    MenuModule.changeMenu(menus)
    PermissionModule.generateRoutes()
    // router.addRoutes(PermissionModule.getDynamicRoutes)
    if (settings.isEnableWebSocket) {
      SocketModule.initSocket()
    }

    next({ ...to, replace: true } as RawLocation)
  } else {
    Message.error(msg || '获取当前用户菜单失败！')
  }
}
