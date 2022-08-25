import request from '@/fetch'
import { MenuPermissionForm, SysMenuPermissionResponse } from './interface/sys-menu-permission'

export const SysMenuPermissionSaveOrUpdateApi = (menuPermissions: Array<MenuPermissionForm>): Promise<KWResponse.Result> => request.post('pagePermission/saveOrUpdateBatch', menuPermissions)
export const GetPagePermissionApi = (): Promise<KWResponse.Result<SysMenuPermissionResponse>> => request.get('pagePermission/get/page')
