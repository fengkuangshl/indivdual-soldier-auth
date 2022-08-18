import { TreeData } from 'element-ui/types/tree'

export enum IsHiddenMenu {
  是 = 1,
  否 = 0
}
export enum IsMenu {
  是 = 1,
  否 = 2
}

export type Name = Model.Name
export interface RoleIdAndMenuIds {
  menuIds: Array<number> | null
  roleId: number | null
}
export interface MenuForm extends Name, Model.ParentId {
  css: string
  isHidden: IsHiddenMenu | boolean | string
  isMenu: IsMenu | number | string
  path: string
  sort: number
  url: string
  title: string
}
export interface MenuResponse extends Model.BaseField, Name, RoleIdAndMenuIds, MenuForm {
  subMenus: Array<MenuResponse> | null
}
export interface Id extends Name {
  id: number
}

export interface IMenuTree extends Name, TreeData {
  children: Array<IMenuTree>
}

export interface MenuRole extends Id {
  checked: boolean
  pId: number
  open: boolean
}
