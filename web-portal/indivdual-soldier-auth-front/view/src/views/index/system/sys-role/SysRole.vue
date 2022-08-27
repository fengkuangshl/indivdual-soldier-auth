<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>角色管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <!-- <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item> -->
        <el-breadcrumb-item>角色列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.name">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchRole"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addRole">添加角色</el-button>
        </el-col>
      </el-row>
      <KWTable url="role/findSysRoleByPaged" style="width: 100%" ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="name" sortable="custom" label="角色名称"> </el-table-column>
        <el-table-column prop="code" sortable="custom" label="code"> </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteRole(scope.row.id)"></el-button>
            <el-tooltip effect="dark" content="菜单权限管理" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-s-tools" size="mini" @click="grantPermission(scope.row)">
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditRoleClosed" :visible.sync="roleDialogVisble" width="20%">
      <el-form :model="sysRoleForm" :rules="sysRoleFormRules" ref="sysRoleFormRef" label-width="70px">
        <el-form-item label="角色" prop="name">
          <el-input v-model="sysRoleForm.name" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="code" prop="code">
          <el-input v-model="sysRoleForm.code" style="max-width: 220px;" :disabled="codeDisabled"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editRole">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { SysRole, SysRoleSearchRequest, SysRoleForm } from './interface/sys-role'
import { DeleteSysRoleApi, SysRoleSaveOrUpdateApi } from './sys-role-api'
import KWTable from '@/components/table/Table.vue'

@Component({
  components: {
    KWTable
  }
})
export default class Role extends Vue {
  t: SysRoleSearchRequest = { name: '' }

  title = ''
  roleDialogVisble = false
  menuRoleDialogVisble = false
  rolePermissionVisble = false
  codeDisabled = true
  editSysRoleId = -1
  sysRoleForm: SysRoleForm = { name: '', code: '' }
  @Ref('sysRoleFormRef')
  readonly sysRoleFormRef!: ElForm

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<SysRoleSearchRequest, SysRole>

  @Ref('rolePermissionFormRef')
  readonly rolePermissionFormRef!: ElForm

  defaultProps: { children: string; label: string } = { children: 'children', label: 'name' }
  checkedKeys: Array<number> = []
  defaultCheckedKeys: Array<number> = []


  readonly sysRoleFormRules: { name: Array<KWRule.Rule | KWRule.MixinRule>; code: Array<KWRule.Rule | KWRule.MixinRule> } = {
    name: [
      { required: true, message: '请输入帐号', trigger: 'blur' },
      { min: 3, max: 10, message: '用户名的长度3~10个字符之间', trigger: 'blur' }
    ],
    code: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 10, message: '用户名的长度3~10个字符之间', trigger: 'blur' }
    ]
  }

  readonly rolePermissionFormRules: { authIds: Array<KWRule.Rule> } = {
    authIds: [{ required: true, message: '请选择权限', trigger: ['blur', 'change'] }]
  }

  // 展示编辑用于的对话框
  async showEditDialog(role: SysRole): Promise<void> {
    this.title = '编辑用户'
    this.codeDisabled = true
    this.sysRoleForm = role
    this.roleDialogVisble = true
  }

  aditRoleClosed(): void {
    this.roleDialogVisble = false
    this.sysRoleFormRef.resetFields()
  }

  editRole(): void {
    this.sysRoleFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      const { code, msg } = await SysRoleSaveOrUpdateApi(this.sysRoleForm)
      if (code !== 200) {
        this.$message.error(msg || '操作用户信息失败!')
      } else {
        this.roleDialogVisble = false
        this.searchRole()
        this.$message.success('操作用户信息成功!')
      }
    })
  }

  addRole(): void {
    this.title = '添加角色'
    this.codeDisabled = false
    this.roleDialogVisble = true
    this.$nextTick(() => {
      this.sysRoleFormRef.resetFields()
      this.sysRoleForm = { name: '', code: '' }
    })
  }

  deleteRole(id: number): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteSysRoleApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchRole()
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

  searchRole(): void {
    this.kwTableRef.loadByCondition(this.t)
  }
  
  grantPermission(data: SysRole) {
    this.$router.push({
      path: '/sysrmpc',
      query: { id: data.id + '', name: data.name }
    })
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
