export interface RoleMenuPermissionForm extends Model.Id {
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

export interface SysRoleMenuPermissionTableDataType {
  [x: string]: RoleMenuPermissionDetail; //动态添加属性
}

export interface SysRoleMenuPermissionResponse {
  data: Array<SysRoleMenuPermissionTableDataType>,
  title: Array<RoleMenuPermissionDetail>
}
