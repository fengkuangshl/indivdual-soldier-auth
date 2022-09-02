import request from '@/fetch'
import { Name, PermissionEnum, PermissionForm, PermissionResponse } from './interface/sys-permission'
export const SysPermissionSaveOrUpdateApi = (permission: PermissionForm): Promise<KWResponse.Result> => request.post('permission/saveOrUpdate', permission)
export const DeleteSysPermissionApi = (id: number): Promise<KWResponse.Result> => request.delete('permission/delete/' + id)
export const SysPermissionPagedApi = (pageRequest: KWRequest.PageRequest<Name>): Promise<KWResponse.PageResult<PermissionResponse>> => request.post('permission/findSysPermissionByPaged', pageRequest)
export const GetPermissionEnumApi = (): Promise<KWResponse.Result<Array<PermissionEnum>>> => request.get('permission/getPermissionEnum')
