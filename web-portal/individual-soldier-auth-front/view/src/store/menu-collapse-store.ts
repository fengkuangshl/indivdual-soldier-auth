import { VuexModule, Mutation, Action, getModule, Module } from 'vuex-module-decorators'
import store from '@/store'

export interface IMenuCollapseState {
  isCollapseMenu: boolean
}

@Module({ dynamic: true, store, name: 'menuCollapse' })
class MenuCollapseStore extends VuexModule implements IMenuCollapseState {
  public isCollapseMenu = false

  get getCollapseMenuState(): boolean {
    return this.isCollapseMenu
  }

  @Mutation
  public CHANGE_COLLAPSEMENU(isCollapseMenu: boolean): void {
    this.isCollapseMenu = isCollapseMenu
  }

  @Action({ commit: 'CHANGE_COLLAPSEMENU' })
  public changeCollapseMenu(isCollapseMenu: boolean): boolean {
    console.log('isCollapseMenu:' + isCollapseMenu)
    // this.CHANGE_COLLAPSEMENU(isCollapseMenu)
    return isCollapseMenu
  }
}

export const MenuCollapseModule = getModule(MenuCollapseStore)
