import request from '@/fetch'
import { CustomerInfoDetail, CustomerInfoForm, CustomerInfoSearchRequest } from './interface/customer'

export const CustomerInfoPagedApi = (pageRequest: KWRequest.PageRequest<CustomerInfoSearchRequest>): Promise<KWResponse.PageResult<CustomerInfoDetail>> => request.post('customer/getCustomerInfoByPaged', pageRequest)
export const CustomerInfoSaveOrUpdateApi = (customerInfo: CustomerInfoForm): Promise<KWResponse.Result> => request.post('customer/saveOrUpdate', customerInfo)
export const DeleteCustomerInfoApi = (id: number): Promise<KWResponse.Result> => request.delete('customer/delete/' + id)
export const CustomerInfoGetApi = (id: number): Promise<KWResponse.Result<CustomerInfoDetail>> => request.get('customer/get/' + id)
