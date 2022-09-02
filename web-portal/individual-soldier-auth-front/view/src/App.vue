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

/*滚动条里面小方块*/
::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1);
  background: #cccccc;
}

/*滚动条里面轨道*/
::-webkit-scrollbar-track //scroll轨道背景
 {
  -webkit-box-shadow: none;
  border-radius: 10px;
  background-color: transparent;
}
</style>
