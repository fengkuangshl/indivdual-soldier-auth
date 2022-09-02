export type SysRoleSearchRequest = Model.Name
export type SysRoleForm = Model.CodeField
export interface SysRole extends Model.BaseField, SysRoleForm {
  userId: string | null
}
