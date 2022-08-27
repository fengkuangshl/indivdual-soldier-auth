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
}

export interface SysRoleMenuPermissionResponse {
  data: Array<Object>,
  title: Array<RoleMenuPermissionDetail>
}
