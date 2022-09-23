import request from '@/fetch'
import { DeviceAuthDetail, DeviceAuthForm, DeviceAuthSeachRequest } from './interface/device-auth'

export const DeviceAuthPagedApi = (pageRequest: KWRequest.PageRequest<DeviceAuthSeachRequest>): Promise<KWResponse.PageResult<DeviceAuthDetail>> => request.post('deviceAuth/findDeviceAuthByPaged', pageRequest)
export const DeviceAuthSaveOrUpdateApi = (deviceAuth: DeviceAuthForm): Promise<KWResponse.Result> => request.post('deviceAuth/saveOrUpdate', deviceAuth)
export const DeleteDeviceAuthApi = (id: number): Promise<KWResponse.Result> => request.delete('deviceAuth/delete/' + id)
export const DeviceAuthGetApi = (id: number): Promise<KWResponse.Result<DeviceAuthDetail>> => request.get('deviceAuth/get/' + id)
