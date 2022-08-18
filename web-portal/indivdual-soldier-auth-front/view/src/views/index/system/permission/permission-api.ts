import request from '@/fetch'
import { AuthIdsAndRoleId, Name, PermissionForm, PermissionResponse, PermissionRole } from './interface/sys-permission'

export const GetByPermissionRoleIdApi = (roleId: number): Promise<KWResponse.Result<Array<PermissionRole>>> => request.get('permission/' + roleId)
export const SaveMenuPermissionApi = (authIdsAndRoleId: AuthIdsAndRoleId): Promise<KWResponse.Result> => request.post('permission/granted', authIdsAndRoleId)
export const SysPermissionSaveOrUpdateApi = (permission: PermissionForm): Promise<KWResponse.Result> => request.post('permission/saveOrUpdate', permission)
export const DeleteSysPermissionApi = (id: number): Promise<KWResponse.Result> => request.delete('permission/delete/' + id)
export const SysPermissionPagedApi = (pageRequest: KWRequest.PageRequest<Name>): Promise<KWResponse.PageResult<PermissionResponse>> => request.post('permission/findSysPermissionByPaged', pageRequest)
