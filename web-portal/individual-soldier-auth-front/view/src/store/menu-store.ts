import { VuexModule, Mutation, Action, getModule, Module } from 'vuex-module-decorators'
import store from '@/store'
import { MenuResponse } from '@/views/index/system/menu/interface/sys-menu'

export interface IMenuState {
  menus: Array<MenuResponse>
}

@Module({ dynamic: true, store, name: 'menu' })
class MenuStore extends VuexModule implements IMenuState {
  public menus: Array<MenuResponse> = []

  get getMenus() {
    return this.menus
  }

  @Mutation
  public CHANGE_MENU(menus: Array<MenuResponse>): void {
    this.menus = menus
  }

  @Action
  public changeMenu(menus: Array<MenuResponse>): void {
    console.log('action:' + menus)
    this.context.commit('CHANGE_MENU', menus)
  }

  @Action({ commit: 'CHANGE_MENU' })
  public clearMenu(): Array<MenuResponse> {
    return new Array<MenuResponse>()
  }
}

export const MenuModule = getModule(MenuStore)
