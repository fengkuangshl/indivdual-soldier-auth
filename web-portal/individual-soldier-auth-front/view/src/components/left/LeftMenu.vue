<template>
  <el-aside :width="collapseMenuState ? '64px' : '200px'" style="overflow:hidden">
    <div  v-if="collapseMenuState == false" style="padding: 10px 20px 10px 20px;" >
      <img src="../../assets/keywin.png" alt="" />
    </div>
    <div  v-if="collapseMenuState == true" style="padding: 10px 20px 10px 10px;">
      <img src="../../assets/logo.png" alt="" />
    </div>
    <el-menu background-color="#333744" text-color="#fff" active-text-color="#409EFF" unique-opened :collapse="collapseMenuState" :collapse-transition="false" router :default-active="activePath">
      <!-- 一级菜单 -->
      <template v-for="item in menusList">
        <el-submenu v-if="item.isHidden === false && item.isMenu === 1" :index="item.id+''" :key="item.id">
          <!-- 一级菜单模板区 -->
          <template slot="title">
            <i :class="item.css"></i>
            <span>{{ item.name }}</span>
          </template>
          <!--二级菜单-->
          <template v-for="subItem in item.subMenus">
            <el-menu-item v-if="subItem.isHidden === false && subItem.isMenu === 1" :index="subItem.url" :key="subItem.id" @click="saveActivePath(subItem.url)">
              <!-- 二级菜单模板区 -->
              <template slot="title">
                <i :class="subItem.css"></i>
                <span>{{ subItem.name }}</span>
              </template>
            </el-menu-item>
          </template>
        </el-submenu>
      </template>
    </el-menu>
  </el-aside>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { MenuResponse } from '@/views/index/system/menu/interface/sys-menu'
import { local } from '@/store'
import { MenuCollapseModule } from '@/store/menu-collapse-store'
import settings from '@/settings'

@Component({
  components: {}
})
export default class LeftMenu extends Vue {
  activePath = ''
  @Prop({ default: [] })
  readonly menusList!: Array<MenuResponse> | []

  created(): void {
    this.activePath = local.getStr(settings.activePath)
  }

  saveActivePath(activePath: string): void {
    local.save(settings.activePath, activePath)
    this.activePath = activePath
  }

  get collapseMenuState(): boolean {
    return MenuCollapseModule.getCollapseMenuState
  }
}
</script>

<style lang="less" scoped>
.el-aside {
  background-color: #333744;
  align-items: left;
  > div {
    display: flex;
    align-items: center;
    img {
      width: 156px;
      height: 40px;

      // border-radius: 50%;
    }
  }
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: #eaedf1;
}
.iconfont {
  padding-right: 10px;
}
.toggle-button {
  background: #4a5064;
  font-size: 10px;
  text-align: center;
  line-height: 24px;
  color: #fff;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
