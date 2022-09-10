import { Enabled, SysDictBaseData } from '../../base/interface/dict-base-data'

export interface SysDictDataForm extends SysDictBaseData {
  isDefault: boolean | string
}

export interface SysDictData extends SysDictDataForm, Model.BaseField {}

export interface SysDictDataStatusChange extends Model.Id, Enabled {}
