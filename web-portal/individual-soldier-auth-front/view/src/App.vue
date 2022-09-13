<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
@Component({
  components: {},
  filters: {},
  directives: {}
})
export default class App extends Vue {}
</script>

<style>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
}

html {
  font-size: 62.5%;
  height: 100%;
  overflow: hidden;
}

body {
  font-size: 1.4rem;
  height: 100%;
  overflow: hidden;
}

/* body > div {
  height: 100%;
} */
* {
  scrollbar-width: thin;
}
::-webkit-scrollbar {
  width: 6px;
  height: 1px;
}
.el-table__body-wrapper::-webkit-scrollbar {
  width: 8px; /*滚动条宽度*/
  height: 8px; /*滚动条高度*/
}
.el-table__body-wrapper::-webkit-scrollbar-track {
  border-radius: 10px; /*滚动条的背景区域的圆角*/
  -webkit-box-shadow: inset 0 0 6px rgba(238, 238, 238, 0.3);
  background-color: #eeeeee; /*滚动条的背景颜色*/
}
.el-table__body-wrapper::-webkit-scrollbar-thumb {
  border-radius: 10px; /*滚动条的圆角*/
  -webkit-box-shadow: inset 0 0 6px rgba(145, 143, 0143, 0.3);
  background-color: rgb(145, 143, 143); /*滚动条的背景颜色*/
}
</style>
