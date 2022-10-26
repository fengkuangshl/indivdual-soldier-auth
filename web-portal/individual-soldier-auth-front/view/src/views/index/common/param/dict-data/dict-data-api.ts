import request from '@/fetch'
import { SysDictDataForm, SysDictData, SysDictDataStatusChange } from './interface/dict-data'

export const SysDictDataPagedApi = (pageRequest: KWRequest.PageRequest<SysDictDataForm>): Promise<KWResponse.PageResult<SysDictData>> => request.post('sysDictData/getSysDictDataByPaged', pageRequest)
export const SysDictDataSaveOrUpdateApi = (dictData: SysDictDataForm): Promise<KWResponse.Result> => request.post('sysDictData/saveOrUpdate', dictData)
export const DeleteSysDictDataApi = (id: string): Promise<KWResponse.Result> => request.delete('sysDictData/delete/' + id)
export const SysDictDataStatusChangeRequestApi = (param: SysDictDataStatusChange): Promise<KWResponse.Result> => request.get('sysDictData/updateEnabled/' + param.id + '/' + param.status)
export const SysDictDataGetApi = (id: string): Promise<KWResponse.Result<SysDictData>> => request.get('sysDictData/get/' + id)
