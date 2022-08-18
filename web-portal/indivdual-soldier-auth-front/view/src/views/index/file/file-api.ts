import request from '@/fetch'
import { FileInfo, SearchContent } from './interface/file'

export const FilePagedApi = (pageRequest: KWRequest.PageRequest<SearchContent>): Promise<KWResponse.PageResult<FileInfo>> => request.post('file/getFileInfoByPaged', pageRequest)
export const FileUploadApi = (formData: FormData, bizType: string): Promise<KWResponse.Result<FileInfo>> => {
  const config = {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return request.post('upload/' + bizType + '/file', formData, config)
}
export const FileDelete = (id: string): Promise<KWResponse.Result> => request.delete('files/' + id)
