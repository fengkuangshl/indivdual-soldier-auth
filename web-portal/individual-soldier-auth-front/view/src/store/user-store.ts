import { VuexModule, Mutation, Action, getModule, Module } from 'vuex-module-decorators'
import store from '@/store'
import { LoginSuccessUserInfo } from '@/views/index/system/user/interface/sys-user'

export interface IUserState {
  user: LoginSuccessUserInfo | null
}

@Module({ dynamic: true, store, name: 'user' })
class UserStore extends VuexModule implements IUserState {
  public user: LoginSuccessUserInfo | null = null

  get loginUser(): LoginSuccessUserInfo | null {
    return this.user
  }

  @Mutation
  public SET_USERINFO(user: LoginSuccessUserInfo | null): void {
    this.user = user
  }

  @Action({ commit: 'SET_USERINFO' })
  public changeUser(user: LoginSuccessUserInfo): LoginSuccessUserInfo {
    return user
    // this.CHANGE_USER(user)
  }

  @Action({ commit: 'SET_USERINFO' })
  public clearUser(): null {
    return null
  }
}

export const UserModule = getModule(UserStore)
