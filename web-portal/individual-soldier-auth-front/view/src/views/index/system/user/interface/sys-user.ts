import { MenuPermissionDetail } from '../../menu-permission/interface/sys-menu-permission'
import { SysRole } from '../../sys-role/interface/sys-role'

export enum Sex {
  男 = 'MALE',
  女 = 'FMALE'
}

export enum Type {
  普通 = 'NORMAL',
  ADMIN = 'ADMIN'
}

export interface UserSearchRequest {
  nickName: string
}

export interface UserForm extends UserSearchRequest {
  roleIds: Array<number>
  sex: Sex | string | Model.EnumEntity
  phone: string
  userName: string
  type: string | Type | Model.EnumEntity
}

export interface UserPassword {
  newPassword: string | null
  password: string
}

export interface ModifyPassword extends Model.Id, UserPassword {
  rePassword: string
}

export interface Enabled {
  isEnabled: boolean
}

export interface UserInfo extends UserForm, Model.BaseField, UserPassword, Enabled {
  headImgUrl: string | null
  avatar: string
  phone: string
  email: string
  isOnLine: boolean
  sysRoles: Array<SysRole>
}

export interface UserExt extends UserInfo {
  accountNonExpired: boolean
  accountNonLocked: boolean
  credentialsNonExpired: boolean
  permissions: Array<MenuPermissionDetail>
}

export interface LoginSuccessUserInfo {
  permissions: Array<MenuPermissionDetail>
  user: UserExt
}

export interface UserStatusChange extends Model.Id, Enabled {}

export interface UserStatuChangeRequest {
  params: UserStatusChange
}
