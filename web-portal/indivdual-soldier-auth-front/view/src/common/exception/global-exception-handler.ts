import Vue from 'vue'
// 系统错误捕获
const errorHandler = (error: Error, vm?: Vue, info?: string): void => {
  console.error('抛出全局异常')
  console.error(vm)
  console.error(error)
  console.error(info)
}

Vue.config.errorHandler = errorHandler
Vue.prototype.$throw = (error: Error, vm?: Vue, info?: string) => errorHandler(error, vm, info)
// warn
const warnHandler = function(msg: string, vm: Vue, trace: string): void {
  // `trace` 是组件的继承关系追踪
  console.warn('warnHandler msg: %o, vm: %o, trace: %o', msg, vm, trace)
}
Vue.config.warnHandler = warnHandler
Vue.prototype.$warn = (error: Error, vm?: Vue, info?: string) => errorHandler(error, vm, info)

export default { errorHandler, warnHandler }
