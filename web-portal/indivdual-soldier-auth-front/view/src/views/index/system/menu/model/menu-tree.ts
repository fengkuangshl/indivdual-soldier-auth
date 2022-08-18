import { IMenuTree } from '../interface/sys-menu'

export class MenuTree implements IMenuTree {
  id!: string
  name!: string
  children!: Array<IMenuTree>
}
