import request from '@/fetch'
import { SysDictTreeForm, SysDictTree, SysDictTreeStatusChange } from './interface/dict-tree'

export const SysDictTreePagedApi = (pageRequest: KWRequest.PageRequest<SysDictTreeForm>): Promise<KWResponse.PageResult<SysDictTree>> => request.post('sysDictTree/findSysDictTreeByPaged', pageRequest)
export const SysDictTreeSaveOrUpdateApi = (dictTree: SysDictTreeForm): Promise<KWResponse.Result> => request.post('sysDictTree/saveOrUpdate', dictTree)
export const DeleteSysDictTreeApi = (id: string): Promise<KWResponse.Result> => request.delete('sysDictTree/delete/' + id)
export const SysDictTreeStatusChangeRequestApi = (param: SysDictTreeStatusChange): Promise<KWResponse.Result> => request.get('sysDictTree/updateEnabled/' + param.id + '/' + param.status)
export const SysDictTreeGetApi = (id: string): Promise<KWResponse.Result<SysDictTree>> => request.get('sysDictTree/get/' + id)
export const GetSysDictTreeByTypeApi = (type: string): Promise<KWResponse.Result<Array<SysDictTree>>> => request.get('sysDictTree/getDictTree/' + type)
