<template>
  <div class="el-header-div">
    <div>
      <!-- <div class="toggle-button" @click="toggleCollpase">|||</div> -->
      <i :class="collapseMenuIcon" @click="toggleCollpase"></i>
      <span>后台管理系统</span>
    </div>
    <div>
      <el-tooltip class="fullscreen" :content="tipFullScrollContent" effect="dark" placement="bottom" fullscreen>
        <i :class="fullScrollClass" @click="fullScroll" />
      </el-tooltip>
      <el-tooltip content="站内消息" effect="dark" placement="bottom">
      </el-tooltip>
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <!-- src="@/assets/404-images/404-cloud.png" -->
        <div class="avatar-wrapper">
          <img v-src="headImgUrl" class="user-avatar" />
          <div>
            <span>{{ nickName }}</span>
            <i class="el-icon-caret-bottom" />
          </div>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="userInfoCenter">个人信息</el-dropdown-item>
          <el-dropdown-item @click.native="showEditDialog">修改密码</el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出系统</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog title="修改密码" @close="aditUserPasswordClosed" :visible.sync="userPasswordDialogVisble" width="20%">
      <el-form :model="userForm" :rules="userFormRules" ref="userFormRef" label-width="100px">
        <el-form-item label="原 密 码" prop="password">
          <el-input v-model="userForm.password" type="password" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="新 密 码" prop="newPassword">
          <el-input v-model="userForm.newPassword" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePassword">
          <el-input v-model="userForm.rePassword" style="max-width: 220px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userPasswordDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editUserPassword">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { UserModule } from '@/store/user-store'
import { MenuCollapseModule } from '@/store/menu-collapse-store'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { LoginSuccessUserInfo, ModifyPassword } from '@/views/index/system/user/interface/sys-user'
import { UpdatePasswordApi, LogoutApi } from '@/views/index/system/user/user-api'
import { local } from '@/store'
import settings from '@/settings'
import { MenuModule } from '@/store/menu-store'
import { PermissionModule } from '@/store/permission-store'
@Component
export default class HeaderNav extends Vue {
  fullScrollClass = 'iconfont icon-quanping_o'
  tipFullScrollContent = '全屏浏览'
  collapseMenuIcon = 'el-icon-s-fold'
  userPasswordDialogVisble = false
  userForm: ModifyPassword = { id: '0', password: '', newPassword: '', rePassword: '' }
  @Ref('userFormRef')
  readonly userFormRef!: ElForm

  validatePassword(rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void {
    if (value === '') {
      cb(new Error('请输入密码'))
    } else {
      console.log(this)
      if (this.userForm.rePassword !== '') {
        console.log(this.userFormRef.validateField)
        this.userFormRef.validateField('rePassword')
      }
      cb()
    }
  }

  // <!--二次验证密码-->
  validateRePassword(rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void {
    if (value === '') {
      cb(new Error('请再次输入密码'))
    } else if (value !== this.userForm.newPassword) {
      cb(new Error('两次输入密码不一致!'))
    } else {
      cb()
    }
  }

  readonly userFormRules: { password: Array<KWRule.Rule>; newPassword: Array<KWRule.Rule | KWRule.MixinRule | KWRule.ValidatorRule>; rePassword: Array<KWRule.Rule | KWRule.MixinRule | KWRule.ValidatorRule> } = {
    password: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
    newPassword: [
      { required: true, message: '请输入新密码', trigger: 'blur' },
      { min: 6, max: 15, message: '密码的长度6~15个字符之间', trigger: 'blur' },
      { validator: this.validatePassword, trigger: 'blur' }
    ],
    rePassword: [
      { required: true, message: '请再次输入密码', trigger: 'blur' },
      { min: 6, max: 15, message: '密码的长度6~15个字符之间', trigger: 'blur' },
      { validator: this.validateRePassword, trigger: 'blur' }
    ]
  }

  get nickName(): string {
    const user = (UserModule.loginUser as LoginSuccessUserInfo).user
    console.log(user && user.nickName)
    if (user !== null) {
      return user.nickName
    }
    return ''
  }

  get headImgUrl(): string | null {
    return (UserModule.loginUser as LoginSuccessUserInfo).user.headImgUrl as string
  }

  mounted(): void {
    this.$nextTick(() => {
      document.addEventListener('fullscreenchange', () => {
        this.isFullscreen()
      })
    })
  }

  destroyed(): void {
    this.$nextTick(() => {
      document.addEventListener('fullscreenchange', () => {
        this.isFullscreen()
      })
    })
  }

  toggleCollpase(): void {
    const isCollapseMenu = MenuCollapseModule.isCollapseMenu
    if (isCollapseMenu) {
      this.collapseMenuIcon = 'el-icon-s-fold'
    } else {
      this.collapseMenuIcon = 'el-icon-s-unfold'
    }
    MenuCollapseModule.changeCollapseMenu(!isCollapseMenu)
    console.log('MenuCollapseModule.getCollapseMenuState:', MenuCollapseModule.getCollapseMenuState)
  }

  userInfoCenter(): void {
    this.$router.push('/myinfo')
    console.log('userInfoCenter')
  }

  async logout(): Promise<void> {
    const { code, msg } = await LogoutApi()
    if (code !== 200) {
      this.$message.error(msg || '用户登出失败!')
    } else {
      local.clear(settings.accessToken)
      local.clear(settings.refreshToken)
      MenuModule.changeMenu([])
      UserModule.clearUser()
      PermissionModule.clearRoutes()
      location.reload()
    }
  }

  dropdownClick(): void {
    console.log('dropdownClick')
  }

  // 判断是否全屏
  public isFullscreen(): boolean {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const doc: any = document
    if (doc.fullscreenElement) {
      this.fullScrollClass = 'iconfont icon-quanping_o'
      this.tipFullScrollContent = '退出全屏'
      return true
    } else {
      this.fullScrollClass = 'iconfont icon-quxiaoquanping_o'
      this.tipFullScrollContent = '全屏浏览'
      return false
    }
  }

  // 点击按钮全屏事件
  public fullScroll(): void {
    if (this.isFullscreen()) {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const doc: any = document
      // 是全屏就退出全屏
      if (doc.exitFullscreen) {
        doc.exitFullscreen()
      } else if (doc.mozCancelFullScreen) {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const docAny = doc as any
        docAny.mozCancelFullScreen()
      } else if (doc.webkitCancelFullScreen) {
        doc.webkitCancelFullScreen()
      }
    } else {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const doc: any = document.documentElement
      // 否则将页面全屏
      if (doc.requestFullscreen) {
        doc.requestFullscreen()
      } else if (doc.mozRequestFullScreen) {
        // FireFox
        doc.mozRequestFullScreen()
      } else if (doc.webkitRequestFullScreen) {
        // Chrome等
        doc.webkitRequestFullScreen()
      } else if (doc.msRequestFullscreen) {
        // IE11
        doc.msRequestFullscreen()
      }
    }
  }

  // 展示编辑用于的对话框
  showEditDialog(): void {
    this.userPasswordDialogVisble = true
  }

  aditUserPasswordClosed(): void {
    this.userPasswordDialogVisble = false
    this.userFormRef.resetFields()
  }

  editUserPassword(): void {
    console.log(this)
    this.userFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.modifyPassword()
    })
  }

  modifyPassword(): void {
    this.$confirm('确定要修改密码, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        this.userForm.id = (UserModule.loginUser as LoginSuccessUserInfo).user.id
        const { code, msg } = await UpdatePasswordApi(this.userForm)
        if (code !== 200) {
          this.$message.error(msg || '用户密码修改失败!')
        } else {
          this.userPasswordDialogVisble = false
          this.$message.success('用户密码修改成功!')
        }
      })
      .catch(e => {
        console.log(e)
        this.$message({
          type: 'info',
          message: '已取消密码修改'
        })
      })
  }
}
</script>

<style lang="less" scoped>
.iconfont {
  font-family: 'iconfont' !important;
  font-size: 30px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.el-header-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #000;
  font-size: 20px;
  height: 100%;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
  .fullscreen {
    padding: 0 10px;
  }
  .avatar-container {
    margin-right: 30px;

    .avatar-wrapper {
      margin-top: 5px;
      position: relative;
      > div {
        float: right;
        cursor: pointer;
        margin-top: 10%;
        margin-left: -13px;
      }

      .user-avatar {
        cursor: pointer;
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }

      .el-icon-caret-bottom {
        cursor: pointer;
        position: absolute;
        right: -20px;
        top: 9px;
        font-size: 20px;
      }
    }
  }
}
</style>
