import { TreeSelectData } from '@/components/select-tree/interface/tree-select'
import { Enabled, SysDictBaseData } from '../../base/interface/dict-base-data'

export interface SysDictTreeSearch extends SysDictBaseData {
  cascadeCode: string
}

export interface SysDictTreeForm extends SysDictTreeSearch {
  parentId: number | string
}

export interface SysDictTree extends SysDictTreeForm, TreeSelectData {
  subDictTree: Array<SysDictTree>
}

export interface SysDictTreeStatusChange extends Model.Id, Enabled {}
