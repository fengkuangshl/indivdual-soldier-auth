import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

Vue.use(VueRouter)

export const constantRoutes: Array<RouteConfig> = [
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '@/views/login/Login.vue')
  }
  // ,
  // {
  //   path: '/index',
  //   name: 'Index',
  //   redirect: '/home',
  //   component: () => import(/* webpackChunkName: "index" */ '@/views/index/Index.vue'),
  //   children: [
  //     {
  //       path: '/home',
  //       name: 'home',
  //       component: () => import(/* webpackChunkName: "home" */ '@/views/index/home/Home.vue')
  //     },
  //     {
  //       path: '/test',
  //       name: 'Test',
  //       component: () => import(/* webpackChunkName: "test" */ '@/views/index/test/Test.vue')
  //     }
  //   ]
  // }
  // ,
  // {
  //   path: '*',
  //   name: '/404',
  //   component: () => import(/* webpackChunkName: "test" */ '@/components/404.vue')
  // }
]

const router = new VueRouter({
  routes: constantRoutes
})

// router.beforeEach((to, from, next) => {
//   // to 将访问哪一个路径
//   // from 代表从哪个路径跳转而来
//   // next 是一个函数,表示放行
//   //   next() 放行 next('/login') 强制跳转
//   if (to.path === '/login') return next()
//   // 获取token
//   const token = localStorage.getItem('access_token')
//   if (!token) return next('/login')
//   next()
// })

export default router
