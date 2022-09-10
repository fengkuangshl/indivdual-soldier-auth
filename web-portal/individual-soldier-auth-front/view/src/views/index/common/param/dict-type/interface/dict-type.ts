import { Enabled } from '../../base/interface/dict-base-data'

export interface SysDictTypeForm extends Model.CodeField, Enabled {
  type: Model.EnumEntity | string | null
  remark: string
}

export interface SysDictType extends Model.BaseField, SysDictTypeForm {}

export interface SysDictTypeStatusChange extends Model.Id, Enabled {}

export enum Type {
  列表 = 'NORMAL',
  树结构 = 'TREE'
}
