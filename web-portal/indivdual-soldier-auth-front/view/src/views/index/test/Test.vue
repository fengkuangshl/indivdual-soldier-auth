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
          <el-input placeholder="请输入内容" v-model="page.t.nickName">
            <el-button slot="append" icon="el-icon-search" @click="searchUser"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addUser">添加用户</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData.data" stripe border style="margin-top:20px;width: 100%">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="userName" label="帐号" width="180"> </el-table-column>
        <el-table-column prop="nickName" label="昵称" width="180"> </el-table-column>
        <el-table-column prop="phone" label="手机"> </el-table-column>
        <el-table-column prop="sex" label="性别" :formatter="
          row => {
            return row.sex === 0 ? '男' : '女'
          }
        " width="100">
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" :formatter="formatterDate" width="180"> </el-table-column>
        <el-table-column prop="enabled" label="状态" width="100">
          <template v-slot="scope">
            <el-switch v-model="scope.row.enabled" active-color="#13ce66" inactive-color="#ff4949"
              @change="userStatuChanged(scope.row)"> </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <el-tooltip effect="dark" content="重置密码" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini" @click="passwordReset(scope.row.id)">
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.pageNo"
        :page-sizes="[10, 20, 50, 100]" :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.count"> </el-pagination>
    </el-card>
    <el-dialog title="修改用户" @close="aditUserClosed" :visible.sync="userDialogVisble" width="20%">
      <el-form :model="userForm" :rules="userFormRules" ref="userFormRef" label-width="70px">
        <el-form-item label="帐号">
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
          <el-select v-model="userForm.roleIds" multiple filterable allow-create default-first-option placeholder="请选择">
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
import { UserForm, UserInfo, UserSearchRequest, UserStatuChange, Sex, Type } from '@/views/index/system/user/interface/sys-user'
import { SysRoleSearchRequest, SysRole } from '@/views/index/system/sys-role/interface/sys-role'
import { UserPagedApi, UserStatuChangeRequestApi, UserGetApi, UserSaveOrUpdateApi, ResetPasswordApi } from '@/views/index/system/user/user-api'
import { SysRolePagedApi } from '@/views/index/system/sys-role/sys-role-api'

@Component
export default class User extends Vue {
  page: KWRequest.PageRequest<UserSearchRequest> = {
    pageSize: 10, // 每页的数据条数
    pageNo: 1, // 默认开始页面
    t: {
      nickName: ''
    }
  }

  tableData: KWResponse.PageResult<UserInfo> = {
    pageNo: 0,
    pageSize: 0,
    count: 0,
    code: 0,
    data: [],
    msg: '',
    totalPage: 0
  }

  userDialogVisble = false
  userNameDisabled = true
  userForm: UserForm = { nickName: '', phone: '', sex: Sex.男, userName: '', roleIds: new Array<number>(), type: Type.普通 }
  @Ref('userFormRef')
  readonly userFormRef!: ElForm

  readonly userFormRules: { nickName: Array<KWRule.Rule>; phone: Array<KWRule.Rule>; roleIds: Array<KWRule.Rule> } = {
    nickName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入手机', trigger: 'blur' }],
    roleIds: [{ required: true, message: '请选择角色', trigger: ['blur', 'change'] }]
  }

  roleOptions: Array<SysRole> | [] = []
  userRolePage: KWRequest.PageRequest<SysRoleSearchRequest> = {
    pageSize: 10, // 每页的数据条数
    pageNo: 1 // 默认开始页面
  }

  created(): void {
    this.getUserList()
  }

  async getUserList(): Promise<void> {
    const res: KWResponse.PageResult<UserInfo> = await UserPagedApi(this.page)
    this.tableData = res
    console.log(this.tableData)
  }

  handleCurrentChange(newPage: number): void {
    console.log(newPage)
    this.page.pageNo = newPage
    this.getUserList()
  }

  handleSizeChange(newSize: number): void {
    // console.log(newSize)
    this.page.pageSize = newSize
    this.getUserList()
  }

  formatterDate(row: UserInfo): string {
    var date = new Date(row.createDate) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-'
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
    var D = date.getDate() + ' '
    var h = date.getHours() + ':'
    var m = date.getMinutes() + ':'
    var s = date.getSeconds()
    return Y + M + D + h + m + s
  }

  async userStatuChanged(userInfo: UserInfo): Promise<void> {
    console.log(userInfo)
    const req: UserStatuChange = { id: userInfo.id, isEnabled: userInfo.isEnabled }
    console.log(req)
    const { code, msg }: KWResponse.Result = await UserStatuChangeRequestApi(req)
    if (code !== 200) {
      userInfo.isEnabled = !userInfo.isEnabled
      this.$message.error(msg || '更新用户状态失败!')
    } else {
      this.$message.success('更新用户状态成功!')
    }
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: number): Promise<void> {
    this.userNameDisabled = true
    const res = await UserGetApi(id)
    this.userForm = res.data
    // this.userForm.sex = this.userForm.sex === 0 ? '男' : '女'
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
    const { data: res } = await SysRolePagedApi(this.userRolePage)
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
      // this.userForm.roleId = roleIds[0] // roleIds.join(',')
      // this.editUserForm.sex = this.editUserForm.sex === '男' ? 0 : 1
      const { code, msg } = await UserSaveOrUpdateApi(this.userForm)
      if (code !== 200) {
        this.$message.error(msg || '操作用户信息失败!')
      } else {
        this.userDialogVisble = false
        this.getUserList()
        this.$message.success('操作用户信息成功!')
      }
    })
  }

  addUser(): void {
    this.userForm = { nickName: '', phone: '', sex: Sex.男, userName: '', roleIds: new Array<number>(), type: Type.普通 }
    this.userNameDisabled = false
    this.userDialogVisble = true
    this.getUserRole()
    this.$nextTick(() => {
      this.userFormRef.resetFields()
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
    this.getUserList()
  }
}
</script>

<style lang="less" scoped>
</style>
