export interface MenuPermissionForm extends Model.Id {
  menuId: number
  permissionId: number
  checked: boolean

}

export interface MenuPermissionDetail extends Model.BaseField, MenuPermissionForm {
  permissionCode: string
  menuName: string
  permissionName: string
  propertyName: string
}

export interface SysMenuPermissionResponse {
  data: Array<Object>,
  title: Array<MenuPermissionDetail>
}
