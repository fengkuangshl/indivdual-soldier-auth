import request from '@/fetch'
import { DataLogDetail, DataLogSearchRequest } from './interface/data-log'

export const DataLogPagedApi = (pageRequest: KWRequest.PageRequest<DataLogSearchRequest>): Promise<KWResponse.PageResult<DataLogDetail>> => request.post('data/log/getDataLogByPaged', pageRequest)
export const DeleteDataLogApi = (id: number): Promise<KWResponse.Result> => request.delete('data/log/delete/' + id)
export const DataLogGetApi = (id: number): Promise<KWResponse.Result<DataLogDetail>> => request.get('data/log/get/' + id)
