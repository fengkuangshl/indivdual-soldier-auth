<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>客户信息管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>客户信息列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.projectNo" v-hasPermissionQueryPage="customerInfoPermissionPrefix">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchCustomerInfo">
            </el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button data="primary" @click="addCustomerInfo" v-hasPermissionAdd="customerInfoPermissionPrefix">添加客户信息
          </el-button>
        </el-col>
      </el-row>
      <KWTable url="customer/findCustomerInfoByPaged" v-hasPermissionQueryPage="customerInfoPermissionPrefix"
        style="width: 100%" ref="kwTableRef">
        <el-table-column prop="sequence" sortable="custom" label="客户编号"></el-table-column>
        <el-table-column prop="companyName" sortable="custom" label="客户名称"> </el-table-column>
        <el-table-column prop="projectNo" sortable="custom" label="项目号"> </el-table-column>
        <el-table-column prop="projectName" sortable="custom" label="项目名称"> </el-table-column>
        <el-table-column prop="leadName" label="联系人" sortable="custom"></el-table-column>
        <el-table-column prop="leadMobile" label="联系电话" sortable="custom"></el-table-column>
        <el-table-column prop="authDeviceCode" label="授权码" sortable="custom"></el-table-column>
        <el-table-column prop="authDeviceNum" label="授权设备数量" sortable="custom"></el-table-column>
        <el-table-column prop="authorizedQuantity" label="已授权台数" sortable="custom"></el-table-column>
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
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" v-hasPermissionUpdate="customerInfoPermissionPrefix" icon="el-icon-edit"
              size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <el-button type="danger" v-hasPermissionDelete="customerInfoPermissionPrefix" icon="el-icon-delete"
              size="mini" @click="deleteCustomerInfo(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditCustomerInfoClosed" :visible.sync="customerInfoDialogVisble" width="23%">
      <el-form :model="customerInfoForm" :rules="customerInfoFormRules" ref="customerInfoFormRef" label-width="100px">
        <el-form-item label="客户编号" prop="sequence">
          <el-input v-model="customerInfoForm.sequence" style="max-width: 220px;"
            :disabled="customerInfoSequenceDisabled">
          </el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="companyName">
          <el-input v-model="customerInfoForm.companyName" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="客户地址" prop="companyAddress">
          <el-input v-model="customerInfoForm.companyAddress" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="客户电话" prop="companyPhone">
          <el-input v-model="customerInfoForm.companyPhone" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="联系人姓名" prop="leadName">
          <el-input v-model="customerInfoForm.leadName" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="联系人手机" prop="leadMobile">
          <el-input v-model="customerInfoForm.leadMobile" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="项目号" prop="projectNo">
          <el-input v-model="customerInfoForm.projectNo" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="customerInfoForm.projectName" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="授权码" prop="authDeviceCode">
          <el-input v-model="customerInfoForm.authDeviceCode" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="授权设备数" prop="authDeviceNum">
          <el-input v-model="customerInfoForm.authDeviceNum" type="number" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="是否校验日期" prop="isDefault">
          <el-radio-group @change="isVerifChange" v-model="customerInfoForm.isVerify">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="授权到期日期">
          <el-date-picker v-model="expireDeviceDate" type="date" placeholder="授权到期日期" style="max-width: 220px;">
          </el-date-picker>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="customerInfoDialogVisble = false">取 消</el-button>
        <el-button data="primary" @click="editCustomerInfoInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { CustomerInfoForm, CustomerInfoSeachRequest } from './interface/customer'
import { CustomerInfoGetApi, DeleteCustomerInfoApi, CustomerInfoSaveOrUpdateApi } from './customer-api'
import KWTable from '@/components/table/Table.vue'
import FormValidatorRule from '@/common/form-validator/form-validator'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
@Component({
  components: {
    KWTable
  }
})
export default class CustomerInfo extends Vue {
  expireDeviceDate: Date | string = ''
  t: CustomerInfoSeachRequest = {
    authorizedQuantity: 0,
    startDate: '',
    endDate: '',
    startNum: '',
    endNum: '',
    sequence: '',
    authDeviceCode: '',
    expireDeviceDate: null,
    authDeviceNum: 0,
    isVerify: false,
    companyName: '',
    companyAddress: '',
    companyPhone: '',
    leadName: '',
    leadMobile: '',
    leadPhone: '',
    projectNo: '',
    projectName: ''
  }

  title = ''
  customerInfoDialogVisble = false
  customerInfoSequenceDisabled = true
  customerInfoForm: CustomerInfoForm = this.t

  @Ref('customerInfoFormRef')
  readonly customerInfoFormRef!: ElForm

  customerInfoPermissionPrefix = PermissionPrefixUtils.dictData

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<CustomerInfoForm, CustomerInfo>

  readonly customerInfoFormRules: { companyName: Array<KWRule.Rule>; companyAddress: Array<KWRule.Rule>; companyPhone: Array<KWRule.Rule>; leadName: Array<KWRule.Rule>; leadMobile: Array<KWRule.Rule | KWRule.ValidatorRule>; projectNo: Array<KWRule.Rule>; authDeviceCode: Array<KWRule.Rule | KWRule.MixinRule>; authDeviceNum: Array<KWRule.NumberRule>; expireDeviceDate: Array<KWRule.ValidatorRule> } = {
    companyName: [FormValidatorRule.requiredRule('请输入客户名称')],
    companyAddress: [FormValidatorRule.requiredRule('请输入客户地址')],
    companyPhone: [FormValidatorRule.requiredRule('请输入客户电话')],
    leadName: [FormValidatorRule.requiredRule('请输入联系人姓名')],
    leadMobile: [FormValidatorRule.requiredRule('请输入联系人手机'), { validator: FormValidatorRule.checkMobeli, trigger: 'blur' }],
    projectNo: [FormValidatorRule.requiredRule('请输入项目号')],
    authDeviceCode: [FormValidatorRule.requiredRule('请输入授权码'), FormValidatorRule.mixinRul(4, 10, '授权码值的长度4~10个字符之间')],
    authDeviceNum: [FormValidatorRule.numberRule('请输入授权设备台数')],
    expireDeviceDate: [{ validator: this.checkExpireDeviceDate, trigger: 'blur' }]
  }

  // 验证设备的授权到期日期
  checkExpireDeviceDate(rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void {
    if (this.customerInfoForm.isVerify === '是' && !value) {
      cb(new Error('请选择设备到期日期'))
    }
    return cb()
  }

  isVerifChange(): void {
    if (this.customerInfoForm.isVerify === '是') {
      this.expireDeviceDate = this.customerInfoForm.expireDeviceDate === null ? '' : this.customerInfoForm.expireDeviceDate
    } else {
      this.expireDeviceDate = ''
    }
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: number): Promise<void> {
    this.title = '编辑客户信息'
    this.customerInfoSequenceDisabled = true
    const res = await CustomerInfoGetApi(id)
    this.customerInfoForm = res.data
    this.expireDeviceDate = this.customerInfoForm.expireDeviceDate === null ? '' : this.customerInfoForm.expireDeviceDate
    // this.$set(this.customerInfoForm, 'expireDeviceDate', res.data.expireDeviceDate)
    // this.customerInfoForm.expireDeviceDate = this.customerInfoForm.expireDeviceDate === null ? '' : this.customerInfoForm.expireDeviceDate
    this.customerInfoForm.isVerify = (this.customerInfoForm.isVerify as boolean) ? '是' : '否'
    console.log(res)
    this.customerInfoDialogVisble = true
  }

  aditCustomerInfoClosed(): void {
    this.customerInfoDialogVisble = false
    this.customerInfoFormRef.resetFields()
  }

  editCustomerInfoInfo(): void {
    this.customerInfoFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.customerInfoForm.isVerify = (this.customerInfoForm.isVerify as string) === '是'
      this.customerInfoForm.expireDeviceDate = this.expireDeviceDate
      const { code, msg } = await CustomerInfoSaveOrUpdateApi(this.customerInfoForm)
      if (code !== 200) {
        this.$message.error(msg || '操作客户信息信息失败!')
      } else {
        this.customerInfoDialogVisble = false
        this.searchCustomerInfo()
        this.$message.success('操作客户信息信息成功!')
      }
    })
  }

  addCustomerInfo(): void {
    this.title = '添加客户信息'
    this.customerInfoSequenceDisabled = true
    this.customerInfoDialogVisble = true
    this.$nextTick(() => {
      this.customerInfoFormRef.resetFields()
      this.customerInfoForm = {
        sequence: '',
        authDeviceCode: '',
        expireDeviceDate: null,
        authDeviceNum: 0,
        isVerify: '是',
        companyName: '',
        companyAddress: '',
        companyPhone: '',
        leadName: '',
        leadMobile: '',
        leadPhone: '',
        projectNo: '',
        projectName: ''
      }
    })
  }

  deleteCustomerInfo(id: number): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteCustomerInfoApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchCustomerInfo()
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

  searchCustomerInfo(): void {
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
