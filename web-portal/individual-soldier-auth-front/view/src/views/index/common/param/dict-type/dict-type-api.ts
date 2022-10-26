import request from '@/fetch'
import { SysDictTypeForm, SysDictType, SysDictTypeStatusChange } from './interface/dict-type'

export const SysDictTypePagedApi = (pageRequest: KWRequest.PageRequest<SysDictTypeForm>): Promise<KWResponse.PageResult<SysDictType>> => request.post('sysDictType/getSysDictTypeByPaged', pageRequest)
export const SysDictTypeSaveOrUpdateApi = (dictType: SysDictTypeForm): Promise<KWResponse.Result> => request.post('sysDictType/saveOrUpdate', dictType)
export const DeleteSysDictTypeApi = (id: string): Promise<KWResponse.Result> => request.delete('sysDictType/delete/' + id)
export const SysDictTypeStatusChangeRequestApi = (param: SysDictTypeStatusChange): Promise<KWResponse.Result> => request.get('sysDictType/updateEnabled/' + param.id + '/' + param.status)
export const SysDictTypeGetApi = (id: string): Promise<KWResponse.Result<SysDictType>> => request.get('sysDictType/get/' + id)
