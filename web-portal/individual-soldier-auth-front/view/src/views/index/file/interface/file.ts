export type SearchContent = Model.Name

export interface FileInfo extends Model.BaseField {
  md5: string
  name: string
  contentType: string
  size: number
  physicalPath: string
  path: string
  accessPath: string
  physicalPathProcess: string
  pathProcess: string
  fileSuffix: string
}
