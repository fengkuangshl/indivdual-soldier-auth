export interface AuthIdsAndRoleId {
  authIds: Array<number>
  roleId: number | null
}

export type Name = Model.Name

export interface PermissionForm extends Name {
  permission: string
}

export interface PermissionResponse extends Model.BaseField, AuthIdsAndRoleId, PermissionForm {}
export interface PermissionRole extends Name, Model.Id {
  checked: true
  open: true
}
