<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>文件管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>文件列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.name" v-hasPermissionQueryPage="permissionPrefix">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchFile"></el-button>
          </el-input>
        </el-col>
        <el-col :span="7">
          <el-button type="primary" v-hasPermissionUpload="permissionPrefix" @click="addFile">上传文件
          </el-button>
        </el-col>
      </el-row>
      <KWTable url="file/getFileInfoByPaged" method="POST" v-hasPermissionQueryPage="permissionPrefix"
        style="width: 100%" ref="kwTableRef">
        <el-table-column prop="name" sortable label="文件名称">
          <template slot-scope="scope">
            <el-link type="primary" v-hasPermissionDownload="permissionPrefix" :href="scope.row.accessPath"
              target="_blank">{{ scope.row.name}}</el-link>
            <span v-if="hasPermission()">{{ scope.row.name}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="contentType" sortable label="文件类型"> </el-table-column>
        <el-table-column prop="bizType" sortable label="业务类型"> </el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="danger" icon="el-icon-delete" size="mini" v-hasPermissionDelete="permissionPrefix"
              @click="deleteFile(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditFileClosed" :visible.sync="fileDialogVisble" width="20%">
      <el-upload class="upload-demo" ref="upload" action="" :on-preview="handlePreview" :on-remove="handleRemove"
        :on-success="uploadSuccess" :http-request="httpRequest" :file-list="fileList" :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fileDialogVisble = false; clearFiles()">取 消</el-button>
        <el-button type="primary" @click="submitUpload">上传到服务器</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator'
import { FileInfo, Name } from './interface/file'
import { FileDeleteApi, FileUploadApi } from './file-api'
import KWTable from '@/components/table/Table.vue'
import { ElUpload, ElUploadInternalFileDetail, HttpRequestOptions } from 'node_modules/_element-ui@2.15.9@element-ui/types/upload'
import PermissionUtil from '@/common/utils/permission/permission-util'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
@Component({
  components: {
    KWTable
  }
})
export default class File extends Vue {
  t: Name = { name: '' }
  permissionPrefix = PermissionPrefixUtils.fileInfo
  fileList: Array<ElUploadInternalFileDetail> = []

  title = ''
  fileDialogVisble = false

  filePermission = 'system::file::SysFile'

  fileInfo: FileInfo = {
    md5: '',
    contentType: '',
    size: 0,
    physicalPath: '',
    path: '',
    accessPath: '',
    physicalPathProcess: '',
    pathProcess: '',
    fileSuffix: '',
    createDate: 0,
    updateDate: null,
    createUserId: '',
    updateUserId: null,
    enableFlag: false,
    createUserName: '',
    updateUserName: null,
    version: 0,
    id: 0,
    name: ''
  }

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<Name, FileInfo>

  @Ref('upload')
  readonly upload!: ElUpload

  aditFileClosed(): void {
    this.fileDialogVisble = false
  }

  addFile(): void {
    this.title = '上传文件'
    this.fileDialogVisble = true
  }

  hasPermission(): boolean {
    return !PermissionUtil.hasPermissionForDownload(this.permissionPrefix)
  }

  deleteFile(id: number): void {
    this.$confirm('确定要删除文件, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await FileDeleteApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchFile()
          this.$message.success('删除成功!')
        }
      })
      .catch(e => {
        console.log(e)
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
  }

  searchFile(): void {
    this.kwTableRef.loadByCondition(this.t)
  }

  submitUpload(): void {
    this.upload.submit()
  }

  handleRemove(file: ElUploadInternalFileDetail, fileList: Array<ElUploadInternalFileDetail>): void {
    console.log(file, fileList)
  }

  handlePreview(file: ElUploadInternalFileDetail): void {
    console.log(file)
  }

  async httpRequest(params: HttpRequestOptions): Promise<void> {
    console.log(params.file) // 拿到上传的文件
    var formdata = new FormData()
    formdata.append('file', params.file)
    const { code, msg } = await FileUploadApi(formdata, 'default')
    if (code !== 200) {
      this.$message.error(msg || '上传失败!')
    } else {
      this.searchFile()
      this.$message.success('上传成功!')
      this.fileDialogVisble = false
    }
  }

  uploadSuccess(): void {
    this.clearFiles()
  }

  clearFiles(): void {
    this.upload.clearFiles()
  }
}
</script>

<style lang="less" scoped>
.search-primary {
  background: #409eff !important;
  border-color: #409eff !important;
  color: #fff !important;
}

.search-primary:focus,
.search-primary:hover {
  background: #66b1ff !important;
  border-color: #66b1ff !important;
  color: #fff !important;
}
</style>
