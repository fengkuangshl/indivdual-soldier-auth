<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>{{  navigationTitle  }}</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>字典数据列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.value" v-hasPermissionQueryPage="sysDictDataPermissionPrefix">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchDictData"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button data="primary" @click="addDictData" v-hasPermissionAdd="sysDictDataPermissionPrefix">添加字典数据
          </el-button>
        </el-col>
      </el-row>
      <KWTable url="sysDictData/getSysDictDataByPaged" v-hasPermissionQueryPage="sysDictDataPermissionPrefix"
        :defaultLoadData="false" style="width: 100%" ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="label" sortable="custom" label="标签"> </el-table-column>
        <el-table-column prop="value" sortable="custom" label="键值"> </el-table-column>
        <el-table-column prop="sort" label="排序" sortable="custom"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" sortable="custom"
          v-hasPermissionEnabled="sysDictDataPermissionPrefix">
          <template v-slot="scope">
            <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949"
              @change="sysDictDataStatusChanged(scope.row, scope.row.status)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" v-hasPermissionUpdate="sysDictDataPermissionPrefix" icon="el-icon-edit"
              size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <el-button type="danger" v-hasPermissionDelete="sysDictDataPermissionPrefix" icon="el-icon-delete"
              size="mini" @click="deleteSysDictData(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditDictDataClosed" :visible.sync="sysDictDataDialogVisble" width="23%">
      <el-form :model="sysDictDataForm" :rules="sysDictDataFormRules" ref="sysDictDataFormRef" label-width="100px">
        <el-form-item label="字典键值" prop="value">
          <el-input v-model="sysDictDataForm.value" style="max-width: 220px;" :disabled="sysDictDataValueDisabled">
          </el-input>
        </el-form-item>
        <el-form-item label="字典标签" prop="label">
          <el-input v-model="sysDictDataForm.label" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="字典排序" prop="sort">
          <el-input v-model="sysDictDataForm.sort" type="number" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="是否为默认值" prop="isDefault">
          <el-radio-group v-model="sysDictDataForm.isDefault">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="字典状态" prop="status">
          <el-radio-group v-model="sysDictDataForm.status">
            <el-radio label="启用"></el-radio>
            <el-radio label="禁用"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="扩展属性1" prop="attr1">
          <el-input v-model="sysDictDataForm.attr1" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性2" prop="attr2">
          <el-input v-model="sysDictDataForm.attr2" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性3" prop="attr3">
          <el-input v-model="sysDictDataForm.attr3" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性4" prop="attr4">
          <el-input v-model="sysDictDataForm.attr4" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性5" prop="attr5">
          <el-input v-model="sysDictDataForm.attr5" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="sysDictDataForm.remark" style="max-width: 220px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="sysDictDataDialogVisble = false">取 消</el-button>
        <el-button data="primary" @click="editDictDataInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { SysDictDataForm, SysDictData, SysDictDataStatusChange } from './interface/dict-data'
import { SysDictDataGetApi, DeleteSysDictDataApi, SysDictDataSaveOrUpdateApi, SysDictDataStatusChangeRequestApi } from './dict-data-api'
import KWTable from '@/components/table/Table.vue'
import FormValidatorRule from '@/common/form-validator/form-validator'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
@Component({
  components: {
    KWTable
  }
})
export default class DictData extends Vue {
  t: SysDictDataForm = {
    isDefault: false,
    sort: 0,
    label: '',
    value: '',
    remark: '',
    type: -1,
    attr1: '',
    attr2: '',
    attr3: '',
    attr4: '',
    attr5: '',
    status: true
  }

  title = ''
  navigationTitle = '字典数据管理'
  dictTypeId = -1
  sysDictDataDialogVisble = false
  sysDictDataValueDisabled = true
  sysDictDataForm: SysDictDataForm = this.t

  @Ref('sysDictDataFormRef')
  readonly sysDictDataFormRef!: ElForm

  sysDictDataPermissionPrefix = PermissionPrefixUtils.dictData

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<SysDictDataForm, SysDictData>

  readonly sysDictDataFormRules: { label: Array<KWRule.Rule | KWRule.MixinRule>; value: Array<KWRule.Rule | KWRule.MixinRule>; sort: Array<KWRule.Rule | KWRule.NumberRule> } = {
    label: [FormValidatorRule.requiredRule('请输入字典标签'), FormValidatorRule.mixinRul(2, 10, '字典标签的长度2~10个字符之间')],
    value: [FormValidatorRule.requiredRule('请输入字典键值'), FormValidatorRule.mixinRul(2, 10, '字典键值的长度2~10个字符之间')],
    sort: [FormValidatorRule.requiredRule('请输入字典排序'), FormValidatorRule.numberRule('请输入数字')]
  }

  async sysDictDataStatusChanged(sysDictData: SysDictData, enabled: boolean): Promise<void> {
    console.log(sysDictData)
    const req: SysDictDataStatusChange = { id: sysDictData.id, status: enabled }
    console.log(req)
    const { code, msg }: KWResponse.Result = await SysDictDataStatusChangeRequestApi(req)
    if (code !== 200) {
      sysDictData.status = !sysDictData.status
      this.$message.error(msg || '更新字典数据状态失败!')
    } else {
      this.$message.success('更新新字典数据状态成功!')
    }
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: number): Promise<void> {
    this.title = '编辑数据字典'
    this.sysDictDataValueDisabled = true
    const res = await SysDictDataGetApi(id)
    this.sysDictDataForm = res.data
    this.sysDictDataForm.isDefault = (this.sysDictDataForm.isDefault as boolean) ? '是' : '否'
    this.sysDictDataForm.status = (this.sysDictDataForm.status as boolean) ? '启用' : '禁用'
    console.log(res)
    this.sysDictDataDialogVisble = true
  }

  aditDictDataClosed(): void {
    this.sysDictDataDialogVisble = false
    this.sysDictDataFormRef.resetFields()
  }

  editDictDataInfo(): void {
    this.sysDictDataFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.sysDictDataForm.isDefault = (this.sysDictDataForm.isDefault as string) === '是'
      this.sysDictDataForm.status = (this.sysDictDataForm.status as string) === '启用'
      this.sysDictDataForm.type = this.dictTypeId
      const { code, msg } = await SysDictDataSaveOrUpdateApi(this.sysDictDataForm)
      if (code !== 200) {
        this.$message.error(msg || '操作字典数据信息失败!')
      } else {
        this.sysDictDataDialogVisble = false
        this.searchDictData()
        this.$message.success('操作字典数据信息成功!')
      }
    })
  }

  addDictData(): void {
    this.title = '添加字典数据'
    this.sysDictDataValueDisabled = false
    this.sysDictDataDialogVisble = true
    this.$nextTick(() => {
      this.sysDictDataFormRef.resetFields()
      this.sysDictDataForm = {
        isDefault: '否',
        sort: 0,
        label: '',
        value: '',
        remark: '',
        type: 0,
        attr1: '',
        attr2: '',
        attr3: '',
        attr4: '',
        attr5: '',
        status: '启用'
      }
    })
  }

  deleteSysDictData(id: number): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteSysDictDataApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchDictData()
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

  searchDictData(): void {
    this.kwTableRef.loadByCondition(this.t)
  }

  mounted(): void {
    if (this.$route.query.id != null) {
      this.sysDictDataForm.type = Number.parseInt(this.$route.query.id as string)
      this.dictTypeId = this.sysDictDataForm.type
    }
    if (this.$route.query.name != null) {
      this.navigationTitle = (this.$route.query.name as string) + '的字典数据管理'
    }
    this.searchDictData()
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
