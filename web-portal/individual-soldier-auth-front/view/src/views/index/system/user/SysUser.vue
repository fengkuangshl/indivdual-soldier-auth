<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>用户管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <!-- <el-breadcrumb-item>后台管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item> -->
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.nickName" v-hasPermissionQueryPage="userPermission">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchUser"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addUser" v-hasPermissionAdd="userPermission">添加用户</el-button>
        </el-col>
      </el-row>
      <KWTable url="user/findSysUserByPaged" style="width: 100%" v-hasPermissionQueryPage="userPermission"
        ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="userName" sortable="custom" label="帐号"> </el-table-column>
        <el-table-column prop="nickName" sortable="custom" label="昵称"> </el-table-column>
        <el-table-column prop="phone" sortable="custom" label="手机"> </el-table-column>
        <el-table-column prop="sex" label="性别" sortable="custom" :formatter="
            row => {
              return row.sex.text
            }
          ">
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column>
        <el-table-column prop="isEnabled" label="状态" sortable="custom">
          <template v-slot="scope">
            <el-switch v-model="scope.row.enabled" v-hasPermission="userPermission+'::USER::UPDATE::ENABLED'"
              active-color="#13ce66" inactive-color="#ff4949" @change="userStatusChanged(scope.row, scope.row.enabled)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" v-hasPermissionUpdate="userPermission" size="mini"
              @click="showEditDialog(scope.row.id)"></el-button>
            <el-tooltip effect="dark" content="重置密码" v-hasPermission="userPermission+'::USER::RESET::PASSWORD'"
              placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini" @click="passwordReset(scope.row.id)">
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditUserClosed" :visible.sync="userDialogVisble" width="20%">
      <el-form :model="userForm" :rules="userFormRules" ref="userFormRef" label-width="70px">
        <el-form-item label="帐号" prop="userName">
          <el-input v-model="userForm.userName" style="max-width: 220px;" :disabled="userNameDisabled"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="nickName">
          <el-input v-model="userForm.nickName" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="userForm.phone" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="userForm.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
          <el-select v-model="userForm.roleIds" clearable multiple filterable placeholder="请选择">
            <el-option v-for="item in roleOptions" :key="item.id" :label="item.name" :value="item.id"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { Sex, Type, UserForm, UserInfo, UserSearchRequest, UserStatusChange } from './interface/sys-user'
import { SysRoleSearchRequest, SysRole } from '../sys-role/interface/sys-role'
import { UserStatusChangeRequestApi, UserGetApi, UserSaveOrUpdateApi, ResetPasswordApi } from './user-api'
import { FindAllSysRoleApi } from '../sys-role/sys-role-api'
import KWTable from '@/components/table/Table.vue'
import FormValidatorRule from '@/common/utils/form-validator'

@Component({
  components: {
    KWTable
  }
})
export default class User extends Vue {
  t: UserSearchRequest = { nickName: '' }

  title = ''
  userDialogVisble = false
  userNameDisabled = true
  userForm: UserForm = { nickName: '', phone: '', sex: '男', userName: '', roleIds: new Array<number>(), type: Type.普通 }
  @Ref('userFormRef')
  readonly userFormRef!: ElForm

  userPermission = 'system::user::SysUser'

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<UserSearchRequest, UserInfo>

  readonly userFormRules: { userName: Array<KWRule.Rule | KWRule.MixinRule>; nickName: Array<KWRule.Rule | KWRule.MixinRule>; phone: Array<KWRule.Rule | KWRule.ValidatorRule>; roleIds: Array<KWRule.Rule> } = {
    userName: [
      { required: true, message: '请输入帐号', trigger: 'blur' },
      { min: 3, max: 10, message: '用户名的长度3~10个字符之间', trigger: 'blur' }
    ],
    nickName: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 10, message: '用户名的长度3~10个字符之间', trigger: 'blur' }
    ],
    phone: [
      { required: true, message: '请输入手机', trigger: 'blur' },
      { validator: FormValidatorRule.checkMobeli, trigger: 'blur' }
    ],
    roleIds: [{ required: true, message: '请选择角色', trigger: ['blur', 'change'] }]
  }

  roleOptions: Array<SysRole> | [] = []
  userRolePage: KWRequest.PageRequest<SysRoleSearchRequest> = {
    pageSize: 1000000, // 每页的数据条数
    pageNo: 1 // 默认开始页面
  }

  async userStatusChanged(userInfo: UserInfo, enabled: boolean): Promise<void> {
    console.log(userInfo)
    const req: UserStatusChange = { id: userInfo.id, isEnabled: enabled }
    console.log(req)
    const { code, msg }: KWResponse.Result = await UserStatusChangeRequestApi(req)
    if (code !== 200) {
      userInfo.isEnabled = !userInfo.isEnabled
      this.$message.error(msg || '更新用户状态失败!')
    } else {
      this.$message.success('更新用户状态成功!')
    }
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: number): Promise<void> {
    this.title = '编辑用户'
    this.userNameDisabled = true
    const res = await UserGetApi(id)
    this.userForm = res.data
    const sex = res.data.sex as Model.EnumEntity
    this.userForm.sex = sex.text
    const roleDatas = res.data.sysRoles
    console.log(roleDatas)
    this.userForm.roleIds = new Array<number>()
    if (roleDatas && roleDatas.length > 0) {
      for (const key in roleDatas) {
        if (Object.hasOwnProperty.call(roleDatas, key)) {
          const element = roleDatas[key]
          this.userForm.roleIds.push(element.id)
        }
      }
    }
    console.log(res)
    this.getUserRole()
    this.userDialogVisble = true
  }

  async getUserRole(): Promise<void> {
    const { data: res } = await FindAllSysRoleApi()
    console.log('1111', res)
    this.roleOptions = res
  }

  aditUserClosed(): void {
    this.userDialogVisble = false
    this.userFormRef.resetFields()
  }

  editUserInfo(): void {
    this.userFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      // const roleIds = this.userForm.roleId as Array<number>
      // this.userForm.roleId = roleIds.join(',')
      this.userForm.sex = this.userForm.sex === '男' ? Sex.男 : Sex.女
      if (this.userForm.type != null) {
        const t = this.userForm.type as Model.EnumEntity
        this.userForm.type = t.stringValue
      }
      const { code, msg } = await UserSaveOrUpdateApi(this.userForm)
      if (code !== 200) {
        this.$message.error(msg || '操作用户信息失败!')
      } else {
        this.userDialogVisble = false
        this.searchUser()
        this.$message.success('操作用户信息成功!')
      }
    })
  }

  addUser(): void {
    this.title = '添加用户'
    this.userNameDisabled = false
    this.userDialogVisble = true
    this.getUserRole()
    this.$nextTick(() => {
      this.userFormRef.resetFields()
      this.userForm = { nickName: '', phone: '', sex: '男', userName: '', roleIds: new Array<number>(), type: Type.普通 }
    })
  }

  passwordReset(id: number): void {
    this.$confirm('确定要重置密码, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await ResetPasswordApi(id)
        if (code !== 200) {
          this.$message.error(msg || '操作用户信息失败!')
        } else {
          this.$message.success('操作用户信息成功!')
        }
      })
      .catch(e => {
        console.log(e)
        this.$message({
          type: 'info',
          message: '已取消重置密码'
        })
      })
  }

  searchUser(): void {
    this.kwTableRef.loadByCondition(this.t)
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
