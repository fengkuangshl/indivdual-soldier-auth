<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>设备认证管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>设备认证列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.projectNo" v-hasPermissionQueryPage="deviceAuthPermissionPrefix">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchDeviceAuth">
            </el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <!-- <el-button data="primary" @click="addDeviceAuth" v-hasPermissionAdd="deviceAuthPermissionPrefix">添加客户信息
          </el-button> -->
        </el-col>
      </el-row>
      <KWTable url="deviceAuth/findDeviceAuthByPaged" v-hasPermissionQueryPage="deviceAuthPermissionPrefix"
        :callbackFn="callbackFn" style="width: 100%" ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <!-- <el-table-column prop="sequence" sortable="custom" label="客户编号">
          <template slot-scope="scope">
            <KWCell :gap="15" label="" style="width: 100px">
              <KWText :value="scope.row.sequence" :row="1" />
            </KWCell>
          </template>
        </el-table-column> -->
        <el-table-column prop="companyName" sortable="custom" label="客户名称">
          <template slot-scope="scope">
            <KWCell :gap="15" label="" style="width: 100px">
              <KWText :value="scope.row.companyName" :row="1" />
            </KWCell>
          </template>
        </el-table-column>
        <el-table-column prop="projectNo" sortable="custom" label="项目号"> </el-table-column>
        <!-- <el-table-column prop="projectName" sortable="custom" label="项目名称">
          <template slot-scope="scope">
            <KWCell :gap="15" label="" style="width: 100px">
              <KWText :value="scope.row.projectName" :row="1" />
            </KWCell>
          </template>
        </el-table-column> -->
        <el-table-column prop="androidId" label="AndroidId" sortable="custom"></el-table-column>
        <el-table-column prop="serialNumber" label="Serial Number" sortable="custom"></el-table-column>
        <el-table-column prop="authCode" label="授权码" sortable="custom"></el-table-column>
        <el-table-column prop="isVerify" label="是否验证日期" sortable="custom" :formatter="
          row => {
            if (row.isVerify) {
              return '是'
            } else {
              return '否'
            }
          }
        "></el-table-column>
        <el-table-column prop="expireDeviceDate" label="授权到期日期" sortable="custom">
          <template slot-scope="scope"
            v-if="scope.row.expireDeviceDate!== null">{{ scope.row.expireDeviceDate | dateFormat }}</template>
        </el-table-column>
        <!-- <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column> -->
        <el-table-column prop="isOnLine" label="设备状态" align="center">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.isOnLine?'在线':'离线'" placement="right">
              <i class="el-icon-s-opportunity " v-if="scope.row.isOnLine" style="color: green;font-size:30px" />
              <i class="el-icon-s-opportunity " v-if="scope.row.isOnLine===false" style="font-size:30px" />
            </el-tooltip>

          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" v-hasPermissionUpdate="deviceAuthPermissionPrefix" icon="el-icon-edit" size="mini"
              @click="showEditDialog(scope.row)"></el-button>
            <el-button type="danger" v-hasPermissionDelete="deviceAuthPermissionPrefix" icon="el-icon-delete"
              size="mini" @click="deleteDeviceAuth(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditDeviceAuthClosed" :visible.sync="deviceAuthDialogVisble" width="25%">
      <el-form :model="deviceAuthForm" ref="deviceAuthFormRef" :rules="deviceAuthFormRules" label-width="120px">
        <el-form-item label="客户编号：">
          {{deviceAuthForm.sequence}}
        </el-form-item>
        <el-form-item label="客户名称：">
          {{deviceAuthForm.companyName}}
        </el-form-item>
        <el-form-item label="项目号：">
          {{deviceAuthForm.projectNo}}
        </el-form-item>
        <el-form-item label="项目名称：">
          {{deviceAuthForm.projectName}}
        </el-form-item>
        <el-form-item label="授权码：">
          {{deviceAuthForm.authCode}}
        </el-form-item>
        <el-form-item label="AndroidId：">
          {{deviceAuthForm.androidId}}
        </el-form-item>
        <el-form-item label="Serial Number：">
          {{deviceAuthForm.serialNumber}}
        </el-form-item>
        <el-form-item label="设备名称：">
          {{deviceAuthForm.brand}}
        </el-form-item>
        <el-form-item label="硬件名称：">
          {{deviceAuthForm.hardware}}
        </el-form-item>
        <el-form-item label="硬件识别码：">
          {{deviceAuthForm.fingerPrint}}
        </el-form-item>
        <el-form-item label="软件版本：">
          {{deviceAuthForm.softwareVersion}}
        </el-form-item>
        <el-form-item label="设备状态：">
          <b style="color:red"> {{deviceAuthForm.isOnLine?'在线':'离线'}}</b>
        </el-form-item>
        <el-form-item label="是否校验日期：">
          {{deviceAuthForm.isVerify?'是':'否'}}
        </el-form-item>
        <el-divider v-if="deviceAuthForm.isVerify"></el-divider>
        <el-form-item label="授权到期日期" v-if="deviceAuthForm.isVerify" prop="expireDeviceDate">
          <el-date-picker v-model="expireDeviceDate" @input="onDatePickerChange" type="date" placeholder="授权到期日期"
            style="max-width: 220px;">
          </el-date-picker>&nbsp;&nbsp;&nbsp;&nbsp;
          <el-button @click="expireDeviceDate=deviceAuthForm.expireDeviceDate">重置</el-button>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deviceAuthDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editDeviceAuthInfo" :disabled="deviceAuthForm.isOnLine?false:true"><b
            style="color:red">手动认证（请确保设备在线）</b></el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { DeviceAuthDetail, DeviceAuthForm, DeviceAuthSearchRequest, DeviceStatus } from './interface/device-auth'
import { DeviceAuthGetApi, DeleteDeviceAuthApi, UpdateExpireDeviceDateAndSendAuthInfo } from './device-auth-api'
import KWTable from '@/components/table/Table.vue'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
import KWCell from '@/components/cell/Cell.vue'
import KWText from '@/components/text/Text.vue'
import EventHub, { deviceStatusNotification } from '@/common/event-hub/event-hub'

@Component({
  components: {
    KWTable,
    KWCell,
    KWText
  }
})
export default class DeviceAuth extends Vue {
  expireDeviceDate: Date | string = ''
  t: DeviceAuthSearchRequest = {
    authorizedQuantity: 0,
    startDate: '',
    endDate: '',
    startNum: '',
    endNum: '',
    sequence: '',
    authDeviceCode: '',
    authDeviceNum: 0,
    isVerify: false,
    companyName: '',
    leadName: '',
    leadMobile: '',
    projectNo: '',
    projectName: '',
    aPILevel: '',
    densityDpi: '',
    heightPixels: '',
    widthPixels: '',
    androidId: '',
    board: '',
    brand: '',
    buildTime: '',
    fingerPrint: '',
    hardware: '',
    macAddress: '',
    radio: '',
    serialNumber: '',
    softwareVersion: '',
    authCode: '',
    verifyCode: '',
    uniqueCode: '',
    expireDeviceDate: '',
    isOnLine: false
  }

  resultDatas: Array<DeviceAuthDetail> = new Array<DeviceAuthDetail>()

  title = ''
  deviceAuthDialogVisble = false
  deviceAuthForm: DeviceAuthForm = this.t

  @Ref('deviceAuthFormRef')
  readonly deviceAuthFormRef!: ElForm

  deviceAuthPermissionPrefix = PermissionPrefixUtils.dictData

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<DeviceAuthForm, DeviceAuth>

  onDatePickerChange(currentDate: Date): void {
    this.expireDeviceDate = currentDate
  }

  readonly deviceAuthFormRules: { expireDeviceDate: Array<KWRule.ValidatorRule> } = {
    expireDeviceDate: [{ validator: this.checkExpireDeviceDate, trigger: 'blur' }]
  }

  // 验证设备的授权到期日期
  checkExpireDeviceDate(rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void {
    if (this.deviceAuthForm.isVerify) {
      if (!this.expireDeviceDate) {
        cb(new Error('请选择设备到期日期'))
      }
      if (new Date().getTime() >= (this.expireDeviceDate as Date).getTime()) {
        cb(new Error('设备到期日期必须大于当前日期'))
      }
    }
    return cb()
  }

  // 展示编辑用于的对话框
  async showEditDialog(row: DeviceAuthDetail): Promise<void> {
    this.title = '编辑客户信息'
    const res = await DeviceAuthGetApi(row.id)
    this.deviceAuthForm = res.data
    this.deviceAuthForm.sequence = row.sequence
    this.deviceAuthForm.companyName = row.companyName
    this.deviceAuthForm.projectNo = row.projectNo
    this.deviceAuthForm.projectName = row.projectName
    this.deviceAuthForm.authCode = row.authCode
    this.deviceAuthForm.isVerify = row.isVerify
    this.expireDeviceDate = this.deviceAuthForm.expireDeviceDate === null ? '' : new Date(this.deviceAuthForm.expireDeviceDate)
    console.log(res)
    this.deviceAuthDialogVisble = true
  }

  aditDeviceAuthClosed(): void {
    this.deviceAuthDialogVisble = false
    this.expireDeviceDate = ''
    this.deviceAuthFormRef.resetFields()
  }

  editDeviceAuthInfo(): void {
    this.deviceAuthFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.$confirm('确定要手动认证吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          if (this.deviceAuthForm.isVerify) {
            this.deviceAuthForm.expireDeviceDate = this.expireDeviceDate
          } else {
            this.deviceAuthForm.expireDeviceDate = null
          }

          const { code, msg } = await UpdateExpireDeviceDateAndSendAuthInfo(this.deviceAuthForm)
          if (code !== 200) {
            this.$message.error(msg || '操作客户信息信息失败!')
            this.expireDeviceDate = ''
          } else {
            this.deviceAuthDialogVisble = false
            this.searchDeviceAuth()
            this.$message.success(msg || '认证成功!')
          }
        })
        .catch(e => {
          console.log(e)
          // this.$message({
          //   type: 'info',
          //   message: '已取消'
          // })
        })
    })
  }

  deleteDeviceAuth(id: number): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteDeviceAuthApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchDeviceAuth()
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

  searchDeviceAuth(): void {
    this.kwTableRef.loadByCondition(this.t)
  }

  mounted(): void {
    if (this.$route.query.uniqueCode != null) {
      this.t.uniqueCode = this.$route.query.uniqueCode as string
      this.searchDeviceAuth()
    }
    EventHub.$on(deviceStatusNotification, (entity: DeviceStatus): void => {
      if (entity) {
        if (this.resultDatas && this.resultDatas.length) {
          this.resultDatas.forEach(item => {
            if (item.uniqueCode === entity.uniqueCode) {
              item.isOnLine = entity.status
            }
          })
        }
        this.deviceAuthForm.isOnLine = entity.status
      }
    })
  }

  callbackFn(datas: Array<DeviceAuthDetail>): void {
    this.resultDatas = datas
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
