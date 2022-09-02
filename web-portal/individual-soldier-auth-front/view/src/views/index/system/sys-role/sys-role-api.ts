import request from '@/fetch'
import { SysRoleSearchRequest, SysRole, SysRoleForm } from './interface/sys-role'

export const SysRolePagedApi = (pageRequest: KWRequest.PageRequest<SysRoleSearchRequest>): Promise<KWResponse.PageResult<SysRole>> => request.post('role/getSysRolesByPaged', pageRequest)
export const SysRoleSaveOrUpdateApi = (role: SysRoleForm): Promise<KWResponse.Result> => request.post('role/saveOrUpdate', role)
export const DeleteSysRoleApi = (id: number): Promise<KWResponse.Result> => request.delete('role/delete/' + id)
export const FindAllSysRoleApi = (): Promise<KWResponse.Result<Array<SysRole>>> => request.get('role/getRoleAll')
