<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../../assets/head.png" alt="" />
      </div>
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" class="login_form">
        <el-form-item prop="userName">
          <el-input v-model="loginForm.userName" prefix-icon="iconfont icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="iconfont icon-password" type="password"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { LoginRequest, LoginResponse } from './interface'
import { LoginApi } from './login-api'
import { local } from '@/store'
import settings from '@/settings'

@Component
export default class Login extends Vue {
  loginForm: LoginRequest = { userName: '', password: '' }
  readonly loginFormRules: { userName: Array<KWRule.Rule>; password: Array<KWRule.Rule> } = { userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }] }
  @Ref('loginFormRef')
  readonly loginFormRef!: ElForm

  resetLoginForm(): void {
    this.loginFormRef.resetFields()
  }

  login(): void {
    this.loginFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      console.log(valid)
      const { code, data, msg }: KWResponse.Result<LoginResponse> = await LoginApi(this.loginForm)
      if (code === 200) {
        // 登录成功
        local.save(settings.accessToken, data.access_token)
        local.save(settings.refreshToken, data.refresh_token)
        local.clear(settings.activePath)
        this.$router.push('/index')
      } else {
        this.$message.error(msg)
      }
    })
  }
}
</script>

<style lang="less" scoped>
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}
.login_box {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  .avatar_box {
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 10px;
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    box-shadow: 0 0 10px #ddd;
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eeeeee;
    }
  }
  .login_form {
    box-sizing: border-box;
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
  }
  .btns {
    display: flex;
    justify-content: flex-end;
  }
}
</style>
