import Vue from 'vue'
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
Vue.use(VueViewer)
VueViewer.setDefaults({
  Options: { inline: true, button: true, navbar: true, title: true, toolbar: true, tooltip: true, movable: true, zoomable: true, rotatable: true, scalable: true, transition: true, fullscreen: true, keyboard: true, url: 'data-source' }
})
