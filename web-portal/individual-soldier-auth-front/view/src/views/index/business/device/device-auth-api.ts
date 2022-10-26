import request from '@/fetch'
import { DeviceAuthDetail, DeviceAuthForm, DeviceAuthSearchRequest } from './interface/device-auth'

export const DeviceAuthPagedApi = (pageRequest: KWRequest.PageRequest<DeviceAuthSearchRequest>): Promise<KWResponse.PageResult<DeviceAuthDetail>> => request.post('deviceAuth/findDeviceAuthByPaged', pageRequest)
export const DeviceAuthSaveOrUpdateApi = (deviceAuth: DeviceAuthForm): Promise<KWResponse.Result> => request.post('deviceAuth/saveOrUpdate', deviceAuth)
export const UpdateExpireDeviceDateAndSendAuthInfo = (deviceAuth: DeviceAuthForm): Promise<KWResponse.Result> => request.post('deviceAuth/updateExpireDeviceDateAndSendAuthInfo', deviceAuth)
export const DeleteDeviceAuthApi = (id: string): Promise<KWResponse.Result> => request.delete('deviceAuth/delete/' + id)
export const DeviceAuthGetApi = (id: string): Promise<KWResponse.Result<DeviceAuthDetail>> => request.get('deviceAuth/get/' + id)
