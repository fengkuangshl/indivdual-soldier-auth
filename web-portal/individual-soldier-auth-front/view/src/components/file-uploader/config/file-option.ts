import { FileInfoBase } from '@/views/index/common/file/interface/file'

export interface ChunkSize {
  chunkSize: number
}

export interface CheckFile extends Model.Name, ChunkSize {
  chunkNumber: number
  currentChunkSize: number
  totalSize: number
  totalChunks: number
  filename: string
  relativePath: string
  bizType: string
  identifier: string
}

export interface MergeFunction {
  (param: FileInfoBase): Promise<KWResponse.Result<string>>
}

// target、testChunks、mergeFn、accept
export interface IOption {
  target?: string
  testChunks?: boolean
  mergeFn?: MergeFunction
  accept?: Array<string>
  headers?: Array<{ [key: string]: string }>
}

export interface IFileStatusText {
  success: string
  error: string
  uploading: string
  paused: string
  waiting: string
}

export interface IChunk extends ChunkSize {
  endByte: number
  loaded: number
  offset: number
  pendingRetry: boolean
  preprocessState: number
  readState: number
  retries: number
  startByte: number
  tested: boolean
  total: number
  xhr: null
  bytes: null
}

export interface IOpts extends ChunkSize {
  allowDuplicateUploads: boolean
  checkChunkUploadedByResponse: (chunk: IChunk, message: string) => void
  chunkRetryInterval: null
  fileParameterName: string
  forceChunkSize: boolean
  generateUniqueIdentifier: null
  headers: { [key: string]: string }
  initFileFn: null
  initialPaused: boolean
  maxChunkRetries: number
  method: string
  onDropStopPropagation: boolean
  permanentErrors: Array<number>
  preprocess: null
  prioritizeFirstAndLastChunk: boolean
  progressCallbacksInterval: number
  simultaneousUploads: number
  singleFile: boolean
  speedSmoothingFactor: number
  successStatuses: Array<number>
  target: string
  testChunks: boolean
  testMethod: string
  uploadMethod: string
  withCredentials: boolean
}

export interface IUPloaderBase {
  aborted: boolean
  allError: boolean
  averageSpeed: number
  completed: boolean
  currentSpeed: number
  error: boolean
  id: number
  isFolder: boolean
  isRoot: boolean
  cancel: () => void
}

export interface IUPloader extends IUPloaderBase {
  fileStatusText: IFileStatusText
  opts: IOpts
  paused: boolean
  preventEvent: () => void
  support: boolean
  supportDirectory: boolean
}

export interface IUploaderFile extends IUPloaderBase {
  fileType: string
  name: string
  paused: boolean
  relativePath: string
  size: number
  uniqueIdentifier: string
  file: File
  params: CheckFile
  chunks: Array<IChunk>
  fileList: Array<File>
  files: Array<File>
  pause: () => void
  resume: () => void
}
