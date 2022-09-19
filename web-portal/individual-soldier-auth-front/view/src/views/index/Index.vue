<template>
  <div class="index">
    <el-container class="index-container">
      <LeftMenu :menusList="menus"></LeftMenu>
      <el-container>
        <el-header>
          <HeaderNav></HeaderNav>
        </el-header>
        <PageTabs :keep-alive-component-instance="keepAliveComponentInstance" />
        <el-main>
          <div ref="keepAliveContainer" style="padding-top:20px;background-color: #fff;">
            <keep-alive>
              <router-view :key="$route.fullPath" />
            </keep-alive>
          </div>
        </el-main>
        <el-footer>
          <div class="footer">
            Copyright © {{ new Date().getFullYear() }} key-win All rights reserved.
            <span class="pull-right">Version 2.0</span>
          </div>
        </el-footer>
      </el-container>
    </el-container>
    <KWUploader></KWUploader>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator'
import HeaderNav from '@/components/header/HeaderNav.vue'
import LeftMenu from '@/components/left/LeftMenu.vue'
import PageTabs from '@/components/page-tabs/PageTabs.vue'
import { MenuResponse } from '@/views/index/system/menu/interface/sys-menu'
// import * as qs from 'qs'
import { MenuModule } from '@/store/menu-store'
import KWUploader from '@/components/file-uploader/GlobalUploader.vue'
@Component({
  components: {
    HeaderNav,
    LeftMenu,
    PageTabs,
    KWUploader
  }
})
export default class Index extends Vue {
  menus: Array<MenuResponse> | [] = []
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  keepAliveComponentInstance: any = null

  @Ref('pageTabs')
  readonly pageTabs!: PageTabs

  created(): void {
    this.menus = MenuModule.getMenus
  }

  mounted(): void {
    if (this.$refs.keepAliveContainer) {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      this.keepAliveComponentInstance = (this.$refs.keepAliveContainer as any).childNodes[0].__vue__ // 获取keep-alive的控件实例对象
    }
  }
}
</script>

<style lang="less" scoped>
.index {
  height: 100%;
  position: relative;
  .index-container {
    height: 100%;
    width: 100%;
    top: 0px;
    left: 0px;
    position: absolute;
    .el-header {
      background-color: #fff;
      align-items: center;
      border-bottom: solid 1px #eee;
    }
    .el-main {
      background-color: #eee;
      padding: 10px;
    }
    .el-footer {
      height: 44px !important;
      left: 200px;
      right: 0;
      bottom: 0;
      height: 44px;
      line-height: 44px;
      padding: 0 15px;
      background-color: #fff;
    }
  }
}
</style>
