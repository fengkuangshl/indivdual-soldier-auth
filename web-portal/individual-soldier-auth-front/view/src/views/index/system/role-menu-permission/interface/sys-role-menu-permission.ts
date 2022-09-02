export interface RoleMenuPermissionForm extends Model.Id {
  roleId: number
  menuId: number
  permissionId: number
  menuPermissionId: number
  checked: boolean
}

export interface RoleMenuPermissionDetail extends Model.BaseField, RoleMenuPermissionForm {
  menuName: string
  permissionName: string
  propertyName: string
  enable: boolean
}

export interface SysRoleMenuPermissionTableDataType {
  [x: string]: RoleMenuPermissionDetail | Array<SysRoleMenuPermissionTableDataType> | string // 动态添加属性
}

export interface SysRoleMenuPermissionResponse {
  data: Array<SysRoleMenuPermissionTableDataType>
  title: Array<RoleMenuPermissionDetail>
}
