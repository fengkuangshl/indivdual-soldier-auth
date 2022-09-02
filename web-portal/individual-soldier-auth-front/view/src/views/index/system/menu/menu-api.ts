import request from '@/fetch'
import { MenuForm, MenuResponse, MenuRole, Name, RoleIdAndMenuIds } from './interface/sys-menu'

export const CurrentMenuApi = (): Promise<KWResponse.Result<Array<MenuResponse>>> => request.get('menu/current', {})
export const GetMenuByRoleIdApi = (roleId: number): Promise<KWResponse.Result<Array<MenuRole>>> => request.get('menu/' + roleId)
export const SaveMenuRoleApi = (roleIdAndMenuIds: RoleIdAndMenuIds): Promise<KWResponse.Result> => request.post('menu/granted', roleIdAndMenuIds)
export const SysMenuPagedApi = (pageRequest: KWRequest.PageRequest<Name>): Promise<KWResponse.PageResult<MenuResponse>> => request.post('menu/getSysMeunByPaged', pageRequest)
export const DeleteSysMenuApi = (id: number): Promise<KWResponse.Result> => request.delete('menu/delete/' + id)
export const SysMuenSaveOrUpdateApi = (menuForm: MenuForm): Promise<KWResponse.Result> => request.post('menu/saveOrUpdate', menuForm)
export const GetMenuByIdApi = (id: number): Promise<KWResponse.Result<MenuResponse>> => request.get('menu/get/' + id)
export const GetOnesApi = (): Promise<KWResponse.Result<Array<MenuResponse>>> => request.get('menu/getOnes', {})
