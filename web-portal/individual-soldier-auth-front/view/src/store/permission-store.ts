import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import { RouteConfig } from 'vue-router'
import router, { constantRoutes } from '@/router'
import store from '@/store'
import { MenuModule } from './menu-store'
import { MenuResponse } from '@/views/index/system/menu/interface/sys-menu'

export interface IPermissionState {
  routes: Array<RouteConfig>
  dynamicRoutes: Array<RouteConfig>
}

@Module({ dynamic: true, store, name: 'permission' })
class PermissionStore extends VuexModule implements IPermissionState {
  public routes: Array<RouteConfig> = []
  public dynamicRoutes: Array<RouteConfig> = []
  public indexRoute: RouteConfig = {
    path: '/index',
    name: 'Index',
    redirect: '/home',
    component: () => import(/* webpackChunkName: "index" */ '@/views/index/Index.vue'),
    children: [
      // 这个是空白页面，重新加载当前页面会用到
      {
        name: 'blank',
        path: '/blank'
      },
      {
        path: '/home',
        name: 'home',
        component: () => import(/* webpackChunkName: "home" */ '@/views/index/home/Home.vue'),
        meta: {
          title: '首页'
        }
      }
    ]
  }

  public defaultRout: RouteConfig = {
    path: '/',
    redirect: '/index'
  }

  public route404: RouteConfig = {
    path: '*',
    name: '/404',
    component: () => import(/* webpackChunkName: "test" */ '@/components/404.vue')
  }

  get getDynamicRoutes() {
    return this.dynamicRoutes
  }

  @Mutation
  public SET_ROUTES(childrenRoutes: Array<RouteConfig>): void {
    this.routes = []
    this.dynamicRoutes = []
    if (childrenRoutes.length > 0) {
      childrenRoutes.forEach(item => {
        const children = this.indexRoute.children as Array<RouteConfig>
        children.push(item)
      })
    }
    // (this.indexRoute.children as Array<RouteConfig>).push(this.route404)
    this.routes = constantRoutes.concat(this.indexRoute)
    this.routes.push(this.defaultRout)
    this.routes.push(this.route404)
    this.dynamicRoutes = [this.indexRoute, this.defaultRout, this.route404]
    // router.addRoutes(this.dynamicRoutes)
    router.addRoute(this.indexRoute)
    router.addRoute(this.defaultRout)
    router.addRoute(this.route404)
  }

  @Mutation
  public CLEAR_ROUTES(): void {
    this.routes = []
    this.dynamicRoutes = []
    router.replace({ path: '/login' })
  }

  @Action({ commit: 'CLEAR_ROUTES' })
  public clearRoutes(): void {
    console.log('clearRoutes')
  }

  @Action
  public generateRoutes(): void {
    const menus: Array<MenuResponse> = MenuModule.getMenus
    const routes: Array<RouteConfig> = []
    asyncRouter(menus, routes)
    this.context.commit('SET_ROUTES', routes)
    // this.SET_ROUTES(routes)
  }
}

export const asyncRouter = (menus: Array<MenuResponse>, routes: Array<RouteConfig>): void => {
  if (menus != null && menus.length > 0) {
    menus.forEach(menu => {
      if (menu.subMenus != null && menu.subMenus.length > 0) {
        asyncRouter(menu.subMenus, routes)
      } else {
        /**
           * Note: sub-menu only appear when route children.length >= 1
           * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
           *
           * hidden: true                   if set true, item will not show in the sidebar(default is false)
           * alwaysShow: true               if set true, will always show the root menu
           *                                if not set alwaysShow, when item has more than one children route,
           *                                it will becomes nested mode, otherwise not show the root menu
           * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
           * name:'router-name'             the name is used by <keep-alive> (must set!!!)
           * meta : {
                roles: ['admin','editor']    control the page roles (you can set multiple roles)
                title: 'title'               the name show in sidebar and breadcrumb (recommend set)
                icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
                noCache: true                if set true, the page will no be cached(default is false)
                affix: true                  if set true, the tag will affix in the tags-view
                breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
                activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
              }
           */
        const route: RouteConfig = {
          path: menu.url,
          name: menu.url.substr(1),
          component: loadViewsd(menu.path),
          meta: {
            title: menu.name
          }
        }
        routes.push(route)
      }
    })
  }
}

export const loadViewsd = (view: string) => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  return (resolve: (...modules: any[]) => void): void => require([`@/views/index/${view}.vue`], resolve)
}

export const PermissionModule = getModule(PermissionStore)
