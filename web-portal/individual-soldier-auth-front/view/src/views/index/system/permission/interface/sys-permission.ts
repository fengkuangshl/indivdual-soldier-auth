export type Name = Model.Name

export interface PermissionForm extends Name {
  permission: string
}

export interface PermissionResponse extends Model.BaseField, PermissionForm {}

export interface PermissionEnum {
  stringValue: string
  text: string
  code: string
}
