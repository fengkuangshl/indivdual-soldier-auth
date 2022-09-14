<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>权限管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <!-- <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item> -->
        <el-breadcrumb-item>权限列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.name" v-hasPermissionQueryPage="permissionPrefix">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchPermission"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addPermission" v-hasPermissionAdd="permissionPrefix">
            添加权限</el-button>
        </el-col>
      </el-row>
      <KWTable url="permission/findSysPermissionByPaged" v-hasPermissionQueryPage="permissionPrefix" style="width: 100%"
        ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="name" sortable="custom" label="权限名称"> </el-table-column>
        <el-table-column prop="permission" sortable="custom" label="权限标识"> </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermissionUpdate="permissionPrefix"
              @click="showEditDialog(scope.row)">
            </el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" v-hasPermissionDelete="permissionPrefix"
              @click="deletePermission(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditPermissionClosed" :visible.sync="permissionDialogVisble" width="20%">
      <el-form :model="sysPermissionForm" :rules="sysPermissionFormRules" ref="sysPermissionFormRef" label-width="90px">
        <el-form-item label="权限标识" prop="permission">
          <el-input v-model="sysPermissionForm.permission" style="max-width: 220px;" placeholder="请输入权限Code"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <!-- <el-input v-model="sysPermissionForm.name"></el-input> -->
          <el-select v-model="sysPermissionForm.name" filterable allow-create default-first-option
            placeholder="请选择或输入权限名称" style="max-width: 220px;" @change="permissionChange">
            <el-option v-for="item in permissionOptions" :key="item.value" :label="item.text" :value="item.text">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="permissionDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editPermission">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { Name, PermissionResponse, PermissionForm, PermissionEnum } from './interface/sys-permission'
import KWTable from '@/components/table/Table.vue'
import { SysPermissionSaveOrUpdateApi, DeleteSysPermissionApi, GetPermissionEnumApi } from './permission-api'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
@Component({
  components: {
    KWTable
  }
})
export default class Permission extends Vue {
  t: Name = { name: '' }

  title = ''
  permissionDialogVisble = false
  sysPermissionForm: PermissionForm = { name: '', permission: '' }
  @Ref('sysPermissionFormRef')
  readonly sysPermissionFormRef!: ElForm

  permissionPrefix = PermissionPrefixUtils.permission
  permissionOptions: Array<PermissionEnum> | [] = []

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<Name, PermissionResponse>

  readonly sysPermissionFormRules: { name: Array<KWRule.Rule | KWRule.MixinRule>; permission: Array<KWRule.Rule> } = {
    name: [
      { required: true, message: '请输入名称', trigger: 'blur' },
      { min: 3, max: 10, message: '名称的长度3~10个字符之间', trigger: 'blur' }
    ],
    permission: [{ required: true, message: '请输入权限标识', trigger: 'blur' }]
  }

  // 展示编辑用于的对话框
  async showEditDialog(permission: PermissionResponse): Promise<void> {
    this.title = '编辑权限'
    this.getPermissionEnum()
    this.sysPermissionForm = { id: permission.id, name: permission.name, permission: permission.permission } as PermissionForm
    this.permissionDialogVisble = true
  }

  aditPermissionClosed(): void {
    this.permissionDialogVisble = false
    this.sysPermissionFormRef.resetFields()
  }

  editPermission(): void {
    this.sysPermissionFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      const { code, msg } = await SysPermissionSaveOrUpdateApi(this.sysPermissionForm)
      if (code !== 200) {
        this.$message.error(msg || '操作权限信息失败!')
      } else {
        this.permissionDialogVisble = false
        this.searchPermission()
        this.$message.success('操作权限信息成功!')
      }
    })
  }

  addPermission(): void {
    this.title = '添加权限'
    this.permissionDialogVisble = true
    this.$nextTick(() => {
      this.sysPermissionFormRef.resetFields()
      this.sysPermissionForm = { name: '', permission: '' }
      this.getPermissionEnum()
    })
  }

  deletePermission(id: number): void {
    this.$confirm('确定要重置密码, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteSysPermissionApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchPermission()
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

  searchPermission(): void {
    this.kwTableRef.loadByCondition(this.t)
  }

  async getPermissionEnum(): Promise<void> {
    const { data: res } = await GetPermissionEnumApi()
    this.permissionOptions = res
  }

  permissionChange(): void {
    console.log(this.sysPermissionForm.name)
    for (const key in this.permissionOptions) {
      if (Object.prototype.hasOwnProperty.call(this.permissionOptions, key)) {
        const element = this.permissionOptions[key]
        if (element.text === this.sysPermissionForm.name) {
          this.sysPermissionForm.permission = element.code
          this.sysPermissionFormRef.validate()
          return
        }
      }
    }
  }
}
</script>

<style lang="less" scoped>
.el-select {
  width: 860px;
}

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
