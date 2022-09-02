<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>菜单管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <!-- <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item> -->
        <el-breadcrumb-item>菜单列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-hasPermissionQueryPage="menuPermission" v-model="t.name">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchMenu"></el-button>
          </el-input>
        </el-col>
        <el-col :span="7">
          <el-button type="primary" @click="addMenu" v-hasPermissionAdd="menuPermission">添加菜单</el-button>
          <el-button type="primary" @click="setPagePermission"
            v-hasPermission="menuPermission+'::GRANT::PAGE::PERMISSION'">菜单页面权限设置</el-button>
        </el-col>
      </el-row>
      <KWTable url="menu/getMenuAll" method="GET" v-hasPermissionQueryPage="menuPermission"
        :tableDataFilter="tableDataFilter" :renderPreFn="menuTreeAssemble" :treeProps="treeProps"
        :isPagination="isPagination" style="width: 100%" ref="kwTableRef">
        <el-table-column prop="name" sortable label="菜单名称"> </el-table-column>
        <el-table-column prop="path" sortable label="菜单路由"> </el-table-column>
        <el-table-column prop="url" sortable label="菜单URL"> </el-table-column>
        <el-table-column prop="css" sortable label="样式"> </el-table-column>
        <el-table-column prop="sort" sortable label="排序"> </el-table-column>
        <el-table-column prop="isMenu" sortable label="类型" :formatter="
          row => {
            if (row.isMenu == 2) {
              return '按钮'
            }
            if (row.parentId == -1) {
              return '目录'
            } else {
              return '菜单'
            }
          }
        ">
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" v-hasPermissionUpdate="menuPermission" size="mini"
              @click="showEditDialog(scope.row.id)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" v-hasPermissionDelete="menuPermission"
              @click="deleteMenu(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditMenuClosed" :visible.sync="menuDialogVisble" width="20%">
      <el-form :model="sysMenuForm" :rules="sysMenuFormRules" ref="sysMenuFormRef" label-width="90px">
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="sysMenuForm.name" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="菜单路由" prop="path">
          <el-input v-model="sysMenuForm.path" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="菜单URL" prop="url">
          <el-input v-model="sysMenuForm.url" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" prop="css">
          <el-input v-model="sysMenuForm.css" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="是否隐藏" prop="css">
          <el-radio-group v-model="sysMenuForm.isHidden">
            <el-radio label="否"></el-radio>
            <el-radio label="是"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否为菜单" prop="isMenu">
          <el-radio-group v-model="sysMenuForm.isMenu">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序号" prop="sort">
          <el-input v-model="sysMenuForm.sort" type="number" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="sysMenuForm.parentId" placeholder="请选择">
            <el-option v-for="item in sysMenuOptions" :key="item.id" :label="item.name" :value="item.id"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editMenu">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { Name, MenuResponse, MenuForm } from './interface/sys-menu'
import { DeleteSysMenuApi, SysMuenSaveOrUpdateApi, GetMenuByIdApi, GetOnesApi } from './menu-api'
import KWTable from '@/components/table/Table.vue'
import settings from '@/settings'

@Component({
  components: {
    KWTable
  }
})
export default class Menu extends Vue {
  t: Name = { name: '' }

  title = ''
  menuDialogVisble = false
  isPagination = false
  treeProps = { children: 'subMenus' }
  sysMenuForm: MenuForm = { name: '', css: '', isHidden: '', isMenu: '', parentId: -1, path: '', sort: 0, url: '', title: '' }
  sysMenuOptions: Array<MenuResponse> = []
  @Ref('sysMenuFormRef')
  readonly sysMenuFormRef!: ElForm

  menuPermission = 'system::menu::SysMenu'

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<Name, MenuResponse>

  readonly sysMenuFormRules: { name: Array<KWRule.Rule | KWRule.MixinRule>; sort: Array<KWRule.Rule> } = {
    name: [
      { required: true, message: '请输入菜单名称', trigger: 'blur' },
      { min: 3, max: 10, message: '菜单名称的长度3~10个字符之间', trigger: 'blur' }
    ],
    sort: [{ required: true, message: '请输入排序', trigger: 'blur' }]
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: number): Promise<void> {
    this.title = '编辑用户'
    this.getOnes()
    const { code, data, msg } = await GetMenuByIdApi(id)
    if (code === 200) {
      this.menuDialogVisble = true
      this.sysMenuForm = data
      this.sysMenuForm.isHidden = this.sysMenuForm.isHidden === true ? '是' : '否'
      this.sysMenuForm.isMenu = this.sysMenuForm.isMenu === 1 ? '是' : '否'
    } else {
      this.$message.error(msg || '获取菜单失败！')
    }
  }

  aditMenuClosed(): void {
    this.menuDialogVisble = false
    this.sysMenuFormRef.resetFields()
  }

  editMenu(): void {
    this.sysMenuFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.sysMenuForm.isMenu = this.sysMenuForm.isMenu === '是' ? '1' : '2'
      this.sysMenuForm.isHidden = this.sysMenuForm.isHidden === '是'
      const { code, msg } = await SysMuenSaveOrUpdateApi(this.sysMenuForm)
      if (code !== 200) {
        this.$message.error(msg || '操作用户信息失败!')
      } else {
        this.menuDialogVisble = false
        this.searchMenu()
        this.$message.success('操作用户信息成功!')
      }
    })
  }

  addMenu(): void {
    this.title = '添加菜单'
    this.menuDialogVisble = true
    this.getOnes()
    this.$nextTick(() => {
      this.sysMenuFormRef.resetFields()
      this.sysMenuForm = { name: '', css: '', isHidden: '否', isMenu: '是', parentId: -1, path: '', sort: 0, url: '', title: '' }
    })
  }

  deleteMenu(id: number): void {
    this.$confirm('确定要重置密码, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteSysMenuApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchMenu()
          this.$message.success('删除成功!')
        }
      })
      .catch(e => {
        console.log(e)
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
  }

  searchMenu(): void {
    this.kwTableRef.loadByCondition(this.t)
  }

  async getOnes(): Promise<void> {
    const { code, msg, data } = await GetOnesApi()
    if (code === 200) {
      this.sysMenuOptions = []
      const rootMenus: MenuResponse = {
        id: -1,
        createDate: 0,
        updateDate: 0,
        createUserId: '',
        updateUserId: '',
        enableFlag: true,
        createUserName: '',
        updateUserName: '',
        version: 0,
        parentId: -1,
        name: '顶级目录',
        url: 'javascript:;',
        path: '',
        css: '',
        sort: 9999,
        isMenu: 1,
        isHidden: false,
        subMenus: null,
        roleId: null,
        menuIds: null,
        title: ''
      }
      this.sysMenuOptions.push(rootMenus)
      this.sysMenuOptions.push(...data)
    } else {
      this.$message.error(msg || '获取一级菜单失败！')
    }
  }

  tableDataFilter(datas: Array<MenuResponse>): Array<MenuResponse> {
    return datas.filter(data => {
      if (this.t.name === '') {
        return true
      }
      if (data.name.toLowerCase().includes(this.t.name.toLowerCase()) || data.path.toLowerCase().includes(this.t.name.toLowerCase()) || data.url.toLowerCase().includes(this.t.name.toLowerCase()) || data.css.toLowerCase().includes(this.t.name.toLowerCase()) || (data.sort + '').toLowerCase().includes(this.t.name.toLowerCase()) || settings.menuTypeDirectory.toLowerCase().includes(this.t.name.toLowerCase())) {
        return true
      }
      const subMenus = data.subMenus
      if (subMenus !== undefined && subMenus != null) {
        for (const key in subMenus) {
          if (Object.prototype.hasOwnProperty.call(subMenus, key)) {
            const subMenu = subMenus[key]
            if (subMenu.name.toLowerCase().includes(this.t.name.toLowerCase()) || subMenu.path.toLowerCase().includes(this.t.name.toLowerCase()) || subMenu.url.toLowerCase().includes(this.t.name.toLowerCase()) || subMenu.css.toLowerCase().includes(this.t.name.toLowerCase()) || (subMenu.sort + '').toLowerCase().includes(this.t.name.toLowerCase()) || settings.menuTypeItem.toLowerCase().includes(this.t.name.toLowerCase())) {
              return true
            }
          }
        }
      }
    })
  }

  menuTreeAssemble(datas: Array<MenuResponse>): Array<MenuResponse> {
    console.log(datas)
    const menus: Array<MenuResponse> = new Array<MenuResponse>()
    const subMenuMap: Map<number, Array<MenuResponse>> = new Map<number, Array<MenuResponse>>()
    for (const key in datas) {
      if (Object.prototype.hasOwnProperty.call(datas, key)) {
        const element = datas[key]
        if (element.parentId === -1) {
          menus.push(element)
        } else {
          let subMenus: Array<MenuResponse> = subMenuMap.get(element.parentId) as Array<MenuResponse>
          if (subMenus === undefined || subMenus === null) {
            subMenus = new Array<MenuResponse>()
          }
          subMenus.push(element)
          subMenuMap.set(element.parentId, subMenus)
        }
      }
    }
    for (const key in menus) {
      if (Object.prototype.hasOwnProperty.call(menus, key)) {
        const item = menus[key]
        const subItem: Array<MenuResponse> = subMenuMap.get(item.id) as Array<MenuResponse>
        if (item.subMenus === undefined || item.subMenus === null) {
          item.subMenus = new Array<MenuResponse>()
        }
        if (subItem !== undefined) {
          item.subMenus.push(...subItem)
        }
      }
    }
    return menus
  }

  setPagePermission(): void {
    this.$router.push('/sysmenpermission')
  }
}
</script>

<style lang="less" scoped>
.search-primary {
  background: #409eff !important;
  border-color: #409eff !important;
  color: #fff !important;
}

.search-primary:focus,
.search-primary:hover {
  background: #66b1ff !important;
  border-color: #66b1ff !important;
  color: #fff !important;
}
</style>
