<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>菜单权限管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>菜单权限列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="12" class="col-rigth">
          <el-button type="primary" :disabled="menuPermissionVisble" @click="saveData()">保存</el-button>
          <el-button @click="resetTableData()">重置</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableDatas" row-key="key" border default-expand-all
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" style="margin-top:20px;width: 100%">
        <el-table-column v-for="item in tableTiles" :key="item.propertyName" :prop="item.propertyName" align='center'
          :label="item.permissionName">
          <template v-slot="scope">
            {{ scope.row[item.propertyName].permissionName }}
            <el-checkbox v-on:change="onChane()"
              v-if="!(scope.row.children && scope.row.children.length > 0) && scope.row[item.propertyName].permissionId > 0"
              v-model="scope.row[item.propertyName].checked">
            </el-checkbox>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { GetPagePermissionApi, SysMenuPermissionSaveOrUpdateApi } from './menu-permission-api'
import { MenuPermissionDetail, MenuPermissionForm, SysMenuPermissionResponse } from './interface/sys-menu-permission'

@Component
export default class MenuPermission extends Vue {
  const tableDatas: Array<any> = new Array()
  const tableOriginalDatas: Array<any> = new Array()
  const tableTiles: Array<MenuPermissionDetail> = new Array()
  menuPermissionVisble = true

  created(): void {
    this.getMenuPermission()
  }

  async getMenuPermission(): Promise<void> {
    const { code, data, msg }: KWResponse.Result<SysMenuPermissionResponse> = await GetPagePermissionApi()
    this.initTableData(data.data)
    this.tableOriginalDatas = JSON.parse(JSON.stringify(this.tableDatas))
    this.tableTiles = data.title
    // console.log(this.tableData)
  }

  resetTableData(): void {
    this.menuPermissionVisble = true
    this.initTableData(JSON.parse(JSON.stringify(this.tableOriginalDatas)))
  }

  initTableData(datas: Array<any>): void {
    this.tableDatas = datas;
    if (this.tableDatas && this.tableDatas.length > 0) {
      this.setTableDataIndex(this.tableDatas, '')
    }
  }

  setTableDataIndex(datas: Array<any>, parentId: string): void {
    for (let index = 0; index < datas.length; index++) {
      const element = datas[index];
      element['key'] = index + parentId;
      if (element.children && element.children.length > 0) {
        this.setTableDataIndex(element.children, element['key'])
      }
    }
  }

  async saveData(): Promise<void> {
    console.log(this.tableDatas)
    if (!this.onChane()) {
      this.$message.success('页面权限数据未变，无需保存!')
      return
    }
    const saveDatas: Array<MenuPermissionForm> = new Array<MenuPermissionForm>()
    this.getTableCheckedData(saveDatas, this.tableDatas)
    console.log(saveDatas)
    if (saveDatas.length > 0) {
      const { code, data, msg }: KWResponse.Result = await SysMenuPermissionSaveOrUpdateApi(saveDatas)
      if (code !== 200) {
        this.$message.error(msg || '操作页面权限信息失败!')
      } else {
        this.getMenuPermission()
        this.$message.success('操作页面权限信息成功!')
      }
    }

  }

  getTableCheckedData(saveDatas: Array<MenuPermissionForm>, datas: Array<any>): void {
    datas.forEach(item => {
      if (item.children && item.children.length > 0) {
        this.getTableCheckedData(saveDatas, item.children)
      } else {
        this.tableTiles.forEach(title => {
          const entity = item[title.propertyName]
          if (entity.menuId && entity.permissionId) {
            const mpf: MenuPermissionForm = { id: entity.id, menuId: entity.menuId, permissionId: entity.permissionId, checked: entity.checked }
            saveDatas.push(mpf)
          }
        })
      }
    })
  }

  onChane(): boolean {
    const tableDatasStr = JSON.stringify(this.tableDatas)
    const tableOriginalDatasStr = JSON.stringify(this.tableOriginalDatas)
    if (tableDatasStr === tableOriginalDatasStr) {
      this.menuPermissionVisble = true
      return false
    } else {
      this.menuPermissionVisble = false
      return true
    }
  }
}
</script>

<style lang="less" scoped>
.col-rigth {
  width: auto !important;
  float: right !important;
}
</style>
