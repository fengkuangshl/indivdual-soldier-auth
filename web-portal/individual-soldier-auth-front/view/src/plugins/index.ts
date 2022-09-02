// 插件类型
import { DirectiveOptions, PluginObject } from 'vue'

// 全局资源
// import statics from './statics'; //静态属性/方法
import directives from '../common/directives' // 自定义指令
// import filters from './filters'; //实例属性/方法
// import mixins from './mixins'; // 混入
// import { $Http } from './mixins/provides/service'; // 混入

export const plugins: PluginObject<any> = {
  install(Vue) {
    // 静态属性/方法
    // Object.assign(Vue, statics);
    // 实例属性/方法
    // Object.assign(Vue.prototype, instances);
    // 自定义指令
    Object.keys(directives).forEach((key: string) => {
      Vue.directive(key, (directives as { [key: string]: DirectiveOptions })[key])
    })
    // 自定义过滤器
    // Object.keys(filters).forEach((key: string) => {
    //   Vue.filter(key, filters[key]);
    // });
  }
}
