export type Name = Model.Name

export interface FileInfo extends Model.BaseField, Model.Name {
  md5: string
  contentType: string
  size: number
  physicalPath: string
  path: string
  accessPath: string
  physicalPathProcess: string
  pathProcess: string
  fileSuffix: string
}
