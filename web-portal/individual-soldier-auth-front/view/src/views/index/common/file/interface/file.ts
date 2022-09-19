export type Name = Model.Name

export interface FileInfoBase extends Model.Name {
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

export interface FileInfo extends Model.BaseField, FileInfoBase {}
