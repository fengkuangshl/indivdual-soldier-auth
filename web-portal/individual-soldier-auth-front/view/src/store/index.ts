import Vue from 'vue'
import Vuex from 'vuex'
import { IMenuCollapseState } from './menu-collapse-store'
import { IMenuState } from './menu-store'
import { IPermissionState } from './permission-store'
import { IUserState } from './user-store'
import StorageSupport from './storage'
Vue.use(Vuex)
/** 暴露sessionStorage */
export const session = new StorageSupport(window.sessionStorage)

/** 暴露localStorage */
export const local = new StorageSupport(window.localStorage)

interface KWRootStore {
  menuCollapse: IMenuCollapseState
  menu: IMenuState
  permission: IPermissionState
  user: IUserState
}

export default new Vuex.Store<KWRootStore>({})
