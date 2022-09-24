<template>
  <div id="global-uploader" :class="{'global-uploader-single': !global}">
    <!-- 上传 -->
    <uploader ref="uploader" :options="initOptions" :fileStatusText="fileStatusText" :autoStart="false"
      @file-added="onFileAdded" @file-success="onFileSuccess" @file-progress="onFileProgress" @file-error="onFileError"
      class="uploader-app">
      <uploader-unsupport></uploader-unsupport>

      <uploader-btn id="global-uploader-btn" ref="uploadBtn">选择文件</uploader-btn>

      <uploader-list v-show="panelShow">
        <div class="file-panel" slot-scope="props" :class="{ collapse: collapse }">
          <div class="file-title">
            <div class="title">文件列表</div>
            <div class="operate">
              <el-button @click="collapse = !collapse" type="text" :title="collapse ? '展开' : '折叠'">
                <i class="iconfont" :class="collapse ? 'el-icon-full-screen' : 'el-icon-minus'"></i>
              </el-button>
              <el-button @click="close" type="text" title="关闭">
                <i class="el-icon-close"></i>
              </el-button>
            </div>
          </div>

          <ul class="file-list">
            <li class="file-item" :class="`file-${file.id}`" v-for="file in props.fileList" :key="file.id">
              <uploader-file :class="'file_' + file.id" ref="files" :file="file" :list="true"></uploader-file>
            </li>
            <div class="no-file" v-if="!props.fileList.length">
              <i class="iconfont icon-empty-file"></i> 暂无待上传文件
            </div>
          </ul>
        </div>
      </uploader-list>
    </uploader>
  </div>
</template>

<script lang="ts">
/**
 *  全局上传插件，两种调用方式
 *   1. 作为全局页面的组件，使用event bus
 *   调用方法：Bus.$emit('openUploader', {params: {}, options: {}})
 *               params: 发送给服务器的额外参数；
 *               options：上传选项，目前支持 target、testChunks、mergeFn、accept
 *
 *   监听函数：Bus.$on('fileAdded', fn); 文件选择后的回调
 *           Bus.$on('fileSuccess', fn); 文件上传成功的回调，监听后记得释放
 *
 *   2. 作为普通组件在单个页面中调用，使用props
 */
import { ACCEPT_CONFIG } from './config/config'
import Bus from '../../common/event-hub/event-hub'
import SparkMD5 from 'spark-md5'
import { Component, Prop, Vue, Watch } from 'vue-property-decorator'
import { IOption, CheckFile, IFileStatusText, IChunk, IUploaderFile, IUPloader } from './config/file-option'
import { getHttpDomain } from '@/common/utils/get-env'
import { FileInfoBase } from '@/views/index/common/file/interface/file'
@Component
export default class KWUploader extends Vue {
  initOptions = {
    target: getHttpDomain() + 'chunk',
    headers: {
      Authorization: 'Bearer ' + localStorage.getItem('access_token')
    },
    chunkSize: '2048000',
    fileParameterName: 'file',
    maxChunkRetries: 3,
    // 是否开启服务器分片校验
    testChunks: true,
    // 服务器分片校验函数，秒传及断点续传基础
    checkChunkUploadedByResponse: function (chunk: IChunk, message: string): boolean {
      let skip = false

      try {
        const objMessage = JSON.parse(message)
        if (objMessage.code === 200) {
          if (objMessage.data.skipUpload) {
            skip = true
          } else {
            skip = (objMessage.data.uploaded || []).indexOf(chunk.offset + 1) >= 0
          }
        }
      } catch (e) {}

      return skip
    },
    query: function (params: CheckFile): CheckFile {
      return {
        ...params
      }
    }
  }

  panelShow = false // 选择文件后，展示上传panel
  collapse = false
  customParams: CheckFile = {
    identifier: '',
    chunkNumber: 0,
    chunkSize: 0,
    currentChunkSize: 0,
    totalSize: 0,
    totalChunks: 0,
    filename: '',
    relativePath: '',
    bizType: '',
    name: ''
  }

  mergeFn = (param: FileInfoBase): Promise<KWResponse.Result<string>> => {
    console.info(param)
    // return new Promise({ code: 200, msg: '', data: '' })
    return Promise.resolve({ code: 200, msg: '', data: '' })
  }

  fileStatusText: IFileStatusText = {
    success: '上传成功',
    error: '上传失败',
    uploading: '上传中',
    paused: '已暂停',
    waiting: '等待上传'
  }

  @Prop({ type: Boolean, default: true })
  private global!: boolean

  // 发送给服务器的额外参数
  @Prop({
    type: Object,
    default: function () {
      return {}
    }
  })
  private params!: CheckFile

  @Prop({
    type: Object,
    default: function () {
      return {}
    }
  })
  private options!: IOption

  @Watch('params', { immediate: true })
  onParams(data: CheckFile): void {
    if (data) {
      this.customParams = data
    }
  }

  @Watch('options', { immediate: true })
  onOptions(data: IOption): void {
    if (data) {
      setTimeout(() => {
        this.customizeOptions(data)
      }, 0)
    }
  }

  mounted(): void {
    Bus.$on(
      'openUploader',
      (
        res = {
          params: { identifier: '', chunkNumber: 0, chunkSize: 0, currentChunkSize: 0, totalSize: 0, totalChunks: 0, filename: '', relativePath: '', bizType: '', name: '' },
          options: {}
        }
      ) => {
        this.customParams = res.params

        this.customizeOptions(res.options)

        if (this.$refs.uploadBtn) {
          const btn = (this.$refs.uploadBtn as Vue).$el as HTMLElement
          btn.click()
        }
      }
    )
  }

  get uploader(): IUPloader {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    return (this.$refs.uploader as any).uploader
  }

  // 自定义options
  customizeOptions(opts: IOption): void {
    if (opts) {
      // 自定义上传url
      if (opts.target) {
        this.uploader.opts.target = opts.target
      }

      // 是否可以秒传、断点续传
      if (opts.testChunks !== undefined) {
        this.uploader.opts.testChunks = opts.testChunks
      }

      // merge 的方法，类型为Function，返回Promise
      this.mergeFn = opts.mergeFn || this.mergeFn
      // 自定义文件上传类型
      const input = document.querySelector('#global-uploader-btn input') as Element
      let accept = ACCEPT_CONFIG.getAll()
      if (opts.accept) {
        accept = opts.accept
      }
      input.setAttribute('accept', accept.join())
    }
  }

  onFileAdded(file: IUploaderFile): void {
    this.panelShow = true
    this.emit('fileAdded')

    // 将额外的参数赋值到每个文件上，以不同文件使用不同params的需求
    // file.params = this.customParams
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    Object.keys(this.customParams).forEach(prop => {
      const f = file as any
      f[prop] = (this.customParams as any)[prop]
    })

    // 计算MD5
    this.computeMD5(file).then(result => this.startUpload(result))
  }

  /**
   * 计算md5值，以实现断点续传及秒传
   * @param file
   * @returns Promise
   */
  computeMD5(file: IUploaderFile): Promise<any> {
    const fileReader = new FileReader()
    const time = new Date().getTime()
    const blobSlice = File.prototype.slice
    let currentChunk = 0
    const chunkSize = 10 * 1024 * 1000
    const chunks = Math.ceil(file.size / chunkSize)
    const spark = new SparkMD5.ArrayBuffer()
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const that = this
    // 文件状态设为"计算MD5"
    this.statusSet(file.id, 'md5')
    file.pause()

    // 计算MD5时隐藏”开始“按钮
    this.$nextTick(() => {
      const up = document.querySelector(`.file-${file.id} .uploader-file-resume`) as HTMLElement
      up.style.marginTop = '-15px'
    })

    loadNext()

    return new Promise((resolve, reject) => {
      fileReader.onload = (e: any) => {
        spark.append(e.target.result)

        if (currentChunk < chunks) {
          currentChunk++
          loadNext()

          // 实时展示MD5的计算进度
          this.$nextTick(() => {
            const md5ProgressText = '校验MD5 ' + ((currentChunk / chunks) * 100).toFixed(0) + '%'
            const f = document.querySelector(`.custom-status-${file.id}`) as HTMLElement
            f.innerText = md5ProgressText
          })
        } else {
          const md5 = spark.end()

          // md5计算完毕
          resolve({ md5, file })

          const up = document.querySelector(`.file-${file.id} .uploader-file-resume`) as HTMLElement
          up.style.marginTop = '16px'

          console.log(`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`)
        }
      }

      fileReader.onerror = function () {
        that.error(`文件${file.name}读取出错，请检查该文件`)
        file.cancel()
        // eslint-disable-next-line prefer-promise-reject-errors
        reject()
      }
    })

    function loadNext() {
      const start = currentChunk * chunkSize
      const end = start + chunkSize >= file.size ? file.size : start + chunkSize

      fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end))
    }
  }

  // md5计算完毕，开始上传
  startUpload(res: { md5: string; file: IUploaderFile }): void {
    res.file.uniqueIdentifier = res.md5
    res.file.resume()
    this.statusRemove(res.file.id)
  }

  onFileSuccess(rootFile: IUploaderFile, file: IUploaderFile, response: string, chunk: IChunk): void {
    const res = JSON.parse(response)

    // 服务端自定义的错误（即http状态码为200，但是是错误的情况），这种错误是Uploader无法拦截的
    if (res.code !== 200) {
      this.$notify({
        title: '操作',
        message: file.name + '上传出错啦！',
        type: 'error',
        duration: 2000
      })
      // 文件状态设为“失败”
      this.statusSet(file.id, 'failed')
      return
    }

    // 如果服务端返回了需要合并的参数
    if (res.data.needMerge) {
      // 文件状态设为“合并中”
      this.statusSet(file.id, 'merging')

      this.mergeFn({
        name: file.name,
        md5: file.uniqueIdentifier,
        contentType: '',
        size: 0,
        physicalPath: '',
        path: '',
        accessPath: '',
        physicalPathProcess: '',
        pathProcess: '',
        fileSuffix: ''
      })
        .then(() => {
          // 文件合并成功
          this.emit('fileSuccess')

          this.statusRemove(file.id)
        })
        .catch((e: any) => {
          console.error('出错啦！', e)
        })

      // 不需要合并
    } else {
      this.emit('fileSuccess')
      console.log('上传成功')
      this.$notify({
        title: '操作',
        message: file.name + '上传成功',
        type: 'success',
        duration: 2000
      })
    }
  }

  onFileProgress(rootFile: IUploaderFile, file: IUploaderFile, chunk: IChunk): void {
    console.log(`上传中 ${file.name}，chunk：${chunk.startByte / 1024 / 1024} ~ ${chunk.endByte / 1024 / 1024}`)
  }

  onFileError(rootFile: IUploaderFile, file: IUploaderFile, error: string, chunk: IChunk): void {
    console.log(chunk.retries)
    this.error(error)
  }

  close(): void {
    this.uploader.cancel()
    this.panelShow = false
  }

  /**
   * 新增的自定义的状态: 'md5'、'merging'、'transcoding'、'failed'
   * @param id
   * @param status
   */
  statusSet(id: number, status: string): void {
    const statusMap: { [key: string]: { text: string; bgc: string } } = {
      md5: {
        text: '校验MD5',
        bgc: '#fff'
      },
      merging: {
        text: '合并中',
        bgc: '#e2eeff'
      },
      transcoding: {
        text: '转码中',
        bgc: '#e2eeff'
      },
      failed: {
        text: '上传失败',
        bgc: '#e2eeff'
      }
    }

    this.$nextTick(() => {
      const statusTag = document.createElement('p')
      statusTag.className = `custom-status-${id} custom-status`
      statusTag.innerText = statusMap[status].text
      statusTag.style.backgroundColor = statusMap[status].bgc

      const statusWrap = document.querySelector(`.file_${id} .uploader-file-status`) as Element
      statusWrap.appendChild(statusTag)
    })
  }

  statusRemove(id: number): void {
    this.$nextTick(() => {
      const statusTag = document.querySelector(`.custom-status-${id}`) as Element
      statusTag.remove()
    })
  }

  emit(e: string): void {
    Bus.$emit(e)
    this.$emit(e)
  }

  error(msg: string): void {
    this.$notify({
      title: '错误',
      message: msg,
      type: 'error',
      duration: 2000
    })
  }
}
</script>

<style lang="less">
#global-uploader {
  &:not(.global-uploader-single) {
    position: fixed;
    z-index: 20;
    right: 15px;
    bottom: 15px;
    box-sizing: border-box;
  }

  .uploader-app {
    width: 520px;
  }

  .file-panel {
    background-color: #fff;
    border: 1px solid #e2e2e2;
    border-radius: 7px 7px 0 0;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);

    .file-title {
      display: flex;
      height: 40px;
      line-height: 40px;
      padding: 0 15px;
      border-bottom: 1px solid #ddd;

      .operate {
        flex: 1;
        text-align: right;

        i {
          font-size: 18px;
        }
      }
    }

    .file-list {
      position: relative;
      height: 240px;
      overflow-x: hidden;
      overflow-y: auto;
      background-color: #fff;
      transition: all 0.3s;

      .file-item {
        background-color: #fff;
      }
    }

    &.collapse {
      .file-title {
        background-color: #e7ecf2;
      }
      .file-list {
        height: 0;
      }
    }
  }

  .no-file {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 16px;
  }

  .uploader-file-icon {
    &:before {
      content: '' !important;
    }

    &[icon='image'] {
      background: url(./images/image-icon.png);
    }
    &[icon='audio'] {
      background: url(./images/audio-icon.png);
      background-size: contain;
    }
    &[icon='video'] {
      background: url(./images/video-icon.png);
    }
    &[icon='document'] {
      background: url(./images/text-icon.png);
    }
    &[icon='unknown'] {
      background: url(./images/zip.png) no-repeat center;
      background-size: contain;
    }
  }

  .uploader-file-actions > span {
    margin-right: 6px;
  }

  .custom-status {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 1;
  }
}

/* 隐藏上传按钮 */
#global-uploader-btn {
  position: absolute;
  clip: rect(0, 0, 0, 0);
}

.global-uploader-single {
  #global-uploader-btn {
    position: relative;
  }
}
</style>
