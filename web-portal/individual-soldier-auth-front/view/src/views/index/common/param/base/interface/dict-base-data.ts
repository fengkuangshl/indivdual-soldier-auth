export interface Enabled {
  status: boolean | string
}
export interface SysDictBaseData extends Enabled {
  sort: number
  label?: string // 为什么加问号呢？为了炒蛋的TreeData的label属性保持一致
  value: string
  remark: string
  type: string
  attr1: string
  attr2: string
  attr3: string
  attr4: string
  attr5: string
}
