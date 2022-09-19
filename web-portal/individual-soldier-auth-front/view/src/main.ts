import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './plugins/viewer.js'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import './common/filters/global-filter'
import '@/permission'
import { plugins } from './plugins/index' // 引入全局插件
import uploader from 'vue-simple-uploader'

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

Vue.use(plugins)
Vue.use(uploader)
Vue.config.productionTip = false
