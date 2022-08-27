<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>权限管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>权限列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="12" class="col-rigth">
          <el-button type="primary" :disabled="roleMenuPermissionVisble" @click="saveData()">保存</el-button>
          <el-button @click="resetTableData()">重置</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableDatas" row-key="key" border default-expand-all
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" style="margin-top:20px;width: 100%">
        <el-table-column v-for="item in tableTiles" :key="item.propertyName" :prop="item.propertyName" align='center'
          :label="item.permissionName">
          <template v-slot="scope">
            <el-checkbox v-on:change="checked => onChane(checked, scope.row, scope.row[item.propertyName].permissionId)"
              :disabled="scope.row[item.propertyName].enable"
              v-if="scope.row[item.propertyName].permissionId > 0 || scope.row[item.propertyName].menuId > 0"
              v-model="scope.row[item.propertyName].checked">
            </el-checkbox>&nbsp;&nbsp;{{ scope.row[item.propertyName].permissionName
            }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { GetPagePermissionApi, SysRoleMenuPermissionSaveOrUpdateApi } from './role-menu-permission-api'
import { RoleMenuPermissionDetail, RoleMenuPermissionForm, SysRoleMenuPermissionResponse } from './interface/sys-role-menu-permission'

@Component
export default class MenuPermission extends Vue {
  const tableDatas: Array<any> = new Array()
  const tableOriginalDatas: Array<any> = new Array()
  const tableTiles: Array<RoleMenuPermissionDetail> = new Array()
  roleMenuPermissionVisble = true
  roleId: number = -1

  created(): void {
    this.getMenuPermission()
  }

  async getMenuPermission(): Promise<void> {
    const { code, data, msg }: KWResponse.Result<SysRoleMenuPermissionResponse> = await GetPagePermissionApi(this.roleId)
    this.tableTiles = data.title
    this.initTableData(data.data)
    this.tableOriginalDatas = JSON.parse(JSON.stringify(this.tableDatas))

    // console.log(this.tableData)
  }
  resetTableData() {
    this.roleMenuPermissionVisble = true
    this.initTableData(JSON.parse(JSON.stringify(this.tableOriginalDatas)))
  }
  initTableData(datas: Array<any>) {
    this.tableDatas = datas;
    if (this.tableDatas && this.tableDatas.length > 0) {
      this.setTableDataIndex(this.tableDatas, '')
    }
  }
  getCheckboxStatusByCurrentRowFirstCol(rowData: any): boolean {
    let checked: boolean = false
    this.tableTiles.forEach(title => {
      const entity = rowData[title.propertyName]
      if (!entity.permissionId) {
        checked = entity.checked
        return
      }
    })
    return checked
  }
  setTableDataIndex(datas: Array<any>, parentId: string) {
    for (let index = 0; index < datas.length; index++) {
      const element = datas[index];
      const col01CheckBoxStatus = this.getCheckboxStatusByCurrentRowFirstCol(element)
      this.tableTiles.forEach(title => {
        const entity = element[title.propertyName]
        if (entity.menuId && entity.roleId && entity.menuPermissionId && entity.permissionId) {
          entity['enable'] = !col01CheckBoxStatus
        } else {
          entity['enable'] = false
        }

      })
      element['key'] = index + parentId;
      if (element.children && element.children.length > 0) {
        this.setTableDataIndex(element.children, element['key'])
      }
    }
  }
  async saveData(): Promise<void> {
    console.log(this.tableDatas)
    if (!this.checkTableBodyChange()) {
      this.$message.success('页面权限数据未变，无需保存!')
      return
    }
    const saveDatas: Array<RoleMenuPermissionForm> = new Array<RoleMenuPermissionForm>()
    this.getTableCheckedData(saveDatas, this.tableDatas)
    console.log(saveDatas)
    if (saveDatas.length > 0) {
      const { code, data, msg }: KWResponse.Result = await SysRoleMenuPermissionSaveOrUpdateApi(saveDatas)
      if (code !== 200) {
        this.$message.error(msg || '操作页面权限信息失败!')
      } else {
        this.getMenuPermission()
        this.$message.success('操作页面权限信息成功!')
      }
    }

  }
  getTableCheckedData(saveDatas: Array<RoleMenuPermissionForm>, datas: Array<any>) {
    datas.forEach(item => {
      this.tableTiles.forEach(title => {
        const entity = item[title.propertyName]
        if (entity.menuId && entity.roleId) {
          const mpf: RoleMenuPermissionForm = { id: entity.id, menuId: entity.menuId, permissionId: entity.permissionId, checked: entity.checked, menuPermissionId: entity.menuPermissionId, roleId: entity.roleId }
          saveDatas.push(mpf)
        }
      })
      if (item.children && item.children.length > 0) {
        this.getTableCheckedData(saveDatas, item.children)
      }
    })
  }

  checkTableBodyChange(): boolean {
    const tableDatasStr = JSON.stringify(this.tableDatas)
    const tableOriginalDatasStr = JSON.stringify(this.tableOriginalDatas)
    if (tableDatasStr === tableOriginalDatasStr) {
      this.roleMenuPermissionVisble = true
      return false
    } else {
      this.roleMenuPermissionVisble = false
      return true
    }
  }

  onChane(checked: boolean, item: any, permissionId: number): void {
    this.checkTableBodyChange()
    this.setRowCheckBox(checked, item, permissionId)
    if (item.children && item.children.length > 0) {
      item.children.forEach((cNode: any) => {
        const col01CheckBoxStatus = this.getCheckboxStatusByCurrentRowFirstCol(cNode)
        this.tableTiles.forEach(title => {
          const entity = cNode[title.propertyName]
          if (col01CheckBoxStatus && checked) {
            console.log('do nothing')
          } else {
            entity.checked = checked
            this.setRowCheckBox(checked, cNode, entity.permissionId)
          }

        })
      })
    }
  }
  setRowCheckBox(checked: boolean, item: any, permissionId: number): void {
    if (!permissionId) {
      this.tableTiles.forEach(title => {
        const entity = item[title.propertyName]
        if (entity.menuId && entity.roleId && entity.menuPermissionId && entity.permissionId) {
          if (!checked) {
            entity.checked = false
            entity.enable = true
          } else {
            entity.checked = true
            entity.enable = false
          }
        }
      })
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
