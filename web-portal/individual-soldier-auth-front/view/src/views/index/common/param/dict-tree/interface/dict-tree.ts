import { TreeSelectData } from '@/components/select-tree/interface/tree-select'
import { Enabled, SysDictBaseData } from '../../base/interface/dict-base-data'

export interface SysDictTreeForm extends SysDictBaseData {
  parentId: number | string
}

export interface SysDictTree extends SysDictTreeForm, TreeSelectData {
  subDictTree: Array<SysDictTree>
}

export interface SysDictTreeStatusChange extends Model.Id, Enabled {}
