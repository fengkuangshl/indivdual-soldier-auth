<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>数据日志信息管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>数据日志信息列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.searchContent" v-hasPermissionQueryPage="dataLogPermissionPrefix">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchDataLog">
            </el-button>
          </el-input>
        </el-col>
        <el-col :span="4">

        </el-col>
      </el-row>
      <KWTable url="data/log/findDataLogByPaged" v-hasPermissionQueryPage="dataLogPermissionPrefix" style="width: 100%"
        ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="createUserName" sortable="custom" label="操作人员">
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column>
        <el-table-column prop="content" sortable="custom" label="操作内容" width="1000">
          <template slot-scope="scope">
            <KWCell :gap="15" label="" style="width: 700px">
              <KWText :value="scope.row.content" :row="1" />
            </KWCell>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="danger" v-hasPermissionDelete="dataLogPermissionPrefix" icon="el-icon-delete" size="mini"
              @click="deleteDataLog(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator'
import { DataLogDetail, DataLogSearchRequest } from './interface/data-log'
import { DeleteDataLogApi } from './data-log-api'
import KWTable from '@/components/table/Table.vue'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
import KWCell from '@/components/cell/Cell.vue'
import KWText from '@/components/text/Text.vue'

@Component({
  components: {
    KWTable,
    KWCell,
    KWText
  }
})
export default class DataLog extends Vue {
  expireDeviceDate: Date | string = ''
  t: DataLogSearchRequest = {
    searchContent: '',
    fkId: ''
  }

  dataLogPermissionPrefix = PermissionPrefixUtils.dataLog

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<DataLogSearchRequest, DataLogDetail>

  deleteDataLog(id: number): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteDataLogApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchDataLog()
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

  searchDataLog(): void {
    this.kwTableRef.loadByCondition(this.t)
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
