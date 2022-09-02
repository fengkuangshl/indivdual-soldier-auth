import request from '@/fetch'
import { RoleMenuPermissionForm, SysRoleMenuPermissionResponse } from './interface/sys-role-menu-permission'

export const SysRoleMenuPermissionSaveOrUpdateApi = (roleMenuPermissions: Array<RoleMenuPermissionForm>): Promise<KWResponse.Result> => request.post('sysrmpc/saveOrUpdateBatch', roleMenuPermissions)
export const GetPagePermissionApi = (roleId: number): Promise<KWResponse.Result<SysRoleMenuPermissionResponse>> => request.get('sysrmpc/get/grant/' + roleId)
