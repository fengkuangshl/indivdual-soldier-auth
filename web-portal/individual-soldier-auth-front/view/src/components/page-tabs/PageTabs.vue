<template>
  <div class="__common-layout-pageTabs">
    <el-scrollbar>
      <div class="__tabs">
        <div class="__tab-item" v-for="item in openedPageRouters" :class="{
            '__is-active': item.fullPath == $route.fullPath
          }" :key="item.fullPath" @click="onClick(item)" @contextmenu.prevent="showContextMenu($event, item)">
          {{ item.meta.title }}
          <span class="el-icon-close" @click.stop="onClose(item)" @contextmenu.prevent.stop=""
            :style="openedPageRouters.length <= 1 ? 'width:0;' : ''"></span>
        </div>
      </div>
    </el-scrollbar>
    <div v-show="contextMenuVisible">
      <ul :style="{ left: contextMenuLeft + 'px', top: contextMenuTop + 'px' }" class="__contextmenu">
        <li>
          <el-button type="text" @click="reload()" size="mini">
            重新加载
          </el-button>
        </li>
        <li>
          <el-button type="text" @click="closeOtherLeft" :disabled="false" size="mini">关闭左边</el-button>
        </li>
        <li>
          <el-button type="text" @click="closeOtherRight" :disabled="false" size="mini">关闭右边</el-button>
        </li>
        <li>
          <el-button type="text" @click="closeOther" size="mini">关闭其他</el-button>
        </li>
      </ul>
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Prop, Vue, Watch } from 'vue-property-decorator'
import { Route, RouteMeta } from 'vue-router'

@Component
export default class PageTabs extends Vue {
  @Prop({ default: {} })
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  keepAliveComponentInstance: any

  @Prop({ default: 'blank' })
  blankRouteName!: string // 空白路由的name值

  contextMenuVisible = false // 右键菜单是否显示

  contextMenuLeft = 0 // 右键菜单显示位置
  contextMenuTop = 0 // 右键菜单显示位置
  contextMenuTargetPageRoute: Route | null = null // 右键所指向的菜单路由
  openedPageRouters: Array<Route> = [] // 已打开的路由页面

  @Watch('$route', { immediate: true })
  routechange(to: Route, from: Route): void {
    console.log(to, from)
    this.openPage(to)
  }

  mounted(): void {
    // 添加点击关闭右键菜单
    window.addEventListener('click', this.closeContextMenu)
  }

  destroyed(): void {
    window.removeEventListener('click', this.closeContextMenu)
  }

  // 隐藏右键菜单
  closeContextMenu(): void {
    this.contextMenuVisible = false
    this.contextMenuTargetPageRoute = null
  }

  openPage(route: Route): void {
    if (route.name === this.blankRouteName) {
      return
    }
    const isExist = this.openedPageRouters.some(item => item.fullPath === route.fullPath)
    if (!isExist) {
      const openedPageRoute = this.openedPageRouters.find(item => item.path === route.path)
      // 判断页面是否支持不同参数多开页面功能，如果不支持且已存在path值一样的页面路由，那就替换它
      if (!(route.meta as RouteMeta).canMultipleOpen && openedPageRoute != null) {
        this.delRouteCache(openedPageRoute.fullPath)
        this.openedPageRouters.splice(this.openedPageRouters.indexOf(openedPageRoute), 1, route)
      } else {
        this.openedPageRouters.push(route)
      }
    }
  }

  // 点击页面标签卡时
  onClick(route: Route): void {
    if (route.fullPath !== this.$route.fullPath) {
      this.$router.push(route.fullPath)
    }
  }

  // 关闭页面标签时
  onClose(route: Route): void {
    let index: number = this.openedPageRouters.indexOf(route)
    this.delPageRoute(route)
    if (route.fullPath === this.$route.fullPath) {
      // 删除页面后，跳转到上一页面
      index = index === 0 ? 0 : index - 1
      const r: Route = this.openedPageRouters[index]
      this.$router.replace({ name: r.name as string })
    }
  }

  // 右键显示菜单
  // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/explicit-module-boundary-types
  showContextMenu(e: any, route: Route): void {
    this.contextMenuTargetPageRoute = route
    this.contextMenuLeft = e.layerX
    this.contextMenuTop = e.layerY
    this.contextMenuVisible = true
  }

  // 重载页面
  reload(): void {
    const contextMenuTargetPageRoute = this.contextMenuTargetPageRoute as Route
    this.delRouteCache(contextMenuTargetPageRoute.fullPath)
    if (contextMenuTargetPageRoute.fullPath === this.$route.fullPath) {
      this.$router.replace({ name: this.blankRouteName }).then(() => {
        this.$router.replace({ name: contextMenuTargetPageRoute.name as string })
      })
    }
  }

  // 关闭其他页面
  closeOther(): void {
    const contextMenuTargetPageRoute = this.contextMenuTargetPageRoute as Route
    for (let i = 0; i < this.openedPageRouters.length; i++) {
      const r = this.openedPageRouters[i]
      if (r !== this.contextMenuTargetPageRoute) {
        this.delPageRoute(r)
        i--
      }
    }
    if (contextMenuTargetPageRoute.fullPath !== this.$route.fullPath) {
      this.$router.replace({ name: contextMenuTargetPageRoute.name as string })
    }
  }

  // 根据路径获取索引
  getPageRouteIndex(fullPath: string): number {
    for (let i = 0; i < this.openedPageRouters.length; i++) {
      if (this.openedPageRouters[i].fullPath === fullPath) {
        return i
      }
    }
    return -1
  }

  // 关闭左边页面
  closeOtherLeft(): void {
    const contextMenuTargetPageRoute = this.contextMenuTargetPageRoute as Route
    let index = this.openedPageRouters.indexOf(this.contextMenuTargetPageRoute as Route)
    const currentIndex = this.getPageRouteIndex(this.$route.fullPath)
    if (index > currentIndex) {
      this.$router.replace({ name: contextMenuTargetPageRoute.name as string })
    }
    for (let i = 0; i < index; i++) {
      const r = this.openedPageRouters[i]
      this.delPageRoute(r)
      i--
      index--
    }
  }

  // 关闭右边页面
  closeOtherRight(): void {
    const contextMenuTargetPageRoute = this.contextMenuTargetPageRoute as Route
    const index = this.openedPageRouters.indexOf(this.contextMenuTargetPageRoute as Route)
    const currentIndex = this.getPageRouteIndex(this.$route.fullPath)
    for (let i = index + 1; i < this.openedPageRouters.length; i++) {
      const r = this.openedPageRouters[i]
      this.delPageRoute(r)
      i--
    }
    if (index < currentIndex) {
      this.$router.replace({ name: contextMenuTargetPageRoute.name as string })
    }
  }

  // 删除页面
  delPageRoute(route: Route): void {
    const routeIndex = this.openedPageRouters.indexOf(route)
    if (routeIndex >= 0) {
      this.openedPageRouters.splice(routeIndex, 1)
    }
    this.delRouteCache(route.fullPath)
  }

  // 删除页面缓存
  delRouteCache(key: string): void {
    const cache = this.keepAliveComponentInstance.cache
    const keys = this.keepAliveComponentInstance.keys
    for (let i = 0; i < keys.length; i++) {
      if (keys[i] === key) {
        keys.splice(i, 1)
        if (cache[key] != null) {
          delete cache[key]
        }
        break
      }
    }
  }
}
</script>
<style lang="less" scoped>
.__common-layout-pageTabs {
  position: relative;
  .__contextmenu {
    // width: 100px;
    margin: 0;
    border: 1px solid #e4e7ed;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 14px;
    color: #333;
    box-shadow: 1px 1px 3px 0 rgba(0, 0, 0, 0.1);
    li {
      margin: 0;
      padding: 0px 15px;
      &:hover {
        background: #f2f2f2;
        cursor: pointer;
      }
      button {
        color: #2c3e50;
      }
    }
  }

  &::before {
    content: '';
    border-bottom: 1px solid #dcdfe6;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    height: 100%;
  }
  .__tabs {
    display: flex;
    box-shadow: 0 1px 2px 0 rgb(0 0 0 / 10%);
    .__tab-item {
      white-space: nowrap;
      padding: 8px 6px 8px 18px;
      font-size: 12px;
      border-right: 1px solid #dcdfe6;
      border-left: none;
      border-bottom: 0px;
      line-height: 20px;
      cursor: pointer;
      transition: color 0.3s cubic-bezier(0.645, 0.045, 0.355, 1), padding 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
      &:first-child {
        border-left: 1px solid #dcdfe6;
        border-top-left-radius: 2px;
        margin-left: 10px;
      }
      &:last-child {
        border-top-right-radius: 2px;
        margin-right: 10px;
      }
      &:not(.__is-active):hover {
        color: #409eff;
        .el-icon-close {
          width: 12px;
          margin-right: 0px;
        }
      }
      &.__is-active {
        padding-right: 12px;
        //border-bottom: 1px solid #fff;
        color: #409eff;
        border-top: solid 1px #b3b3b3;
        background-color: #fcfcfc;
        .el-icon-close {
          width: 12px;
          margin-right: 0px;
          margin-left: 2px;
        }
      }
      .el-icon-close {
        width: 0px;
        height: 12px;
        overflow: hidden;
        border-radius: 50%;
        font-size: 12px;
        margin-right: 12px;
        transform-origin: 100% 50%;
        transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
        vertical-align: text-top;
        &:hover {
          background-color: #c0c4cc;
          color: #fff;
        }
      }
    }
  }
}
</style>
