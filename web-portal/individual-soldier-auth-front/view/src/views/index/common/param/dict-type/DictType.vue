<template>
  <div>
    <div class="navigation-breadcrumb">
      <div>字典类型管理</div>
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>字典类型列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="t.name">
            <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchDictType"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDictType">添加字典类型</el-button>
        </el-col>
      </el-row>
      <KWTable url="sysDictType/getSysDictTypeByPaged" style="width: 100%" ref="kwTableRef">
        <el-table-column type="index" width="80" label="序号"></el-table-column>
        <el-table-column prop="name" sortable="custom" label="字典名称"> </el-table-column>
        <el-table-column prop="code" sortable="custom" label="字典code"> </el-table-column>
        <el-table-column prop="type" label="字典类型" sortable="custom" :formatter="
            row => {
              return row.type.text
            }
          ">
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom">
          <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" sortable="custom">
          <template v-slot="scope">
            <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949"
              @change="sysDictTypeStatusChanged(scope.row, scope.row.status)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteSysDictType(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </KWTable>
    </el-card>
    <el-dialog :title="title" @close="aditDictTypeClosed" :visible.sync="sysDictTypeDialogVisble" width="20%">
      <el-form :model="sysDictTypeForm" :rules="sysDictTypeFormRules" ref="sysDictTypeFormRef" label-width="90px">
        <el-form-item label="字典code" prop="code">
          <el-input v-model="sysDictTypeForm.code" style="max-width: 220px;" :disabled="sysDictTypeCodeDisabled">
          </el-input>
        </el-form-item>
        <el-form-item label="字典名称" prop="name">
          <el-input v-model="sysDictTypeForm.name" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="字典类型" prop="type">
          <el-radio-group v-model="sysDictTypeForm.type">
            <el-radio :disabled="sysDictTypeCodeDisabled" label="列表"></el-radio>
            <el-radio :disabled="sysDictTypeCodeDisabled" label="树结构"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="sysDictTypeForm.remark" style="max-width: 220px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="sysDictTypeDialogVisble = false">取 消</el-button>
        <el-button type="primary" @click="editDictTypeInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { SysDictTypeForm, SysDictType, SysDictTypeStatusChange, Type } from './interface/dict-type'
import { SysDictTypeGetApi, DeleteSysDictTypeApi, SysDictTypeSaveOrUpdateApi, SysDictTypeStatusChangeRequestApi } from './dict-type-api'
import KWTable from '@/components/table/Table.vue'

@Component({
  components: {
    KWTable
  }
})
export default class DictType extends Vue {
  t: SysDictTypeForm = {
    type: null,
    remark: '',
    code: '',
    name: '',
    status: true
  }

  title = ''
  sysDictTypeDialogVisble = false
  sysDictTypeCodeDisabled = true
  sysDictTypeForm: SysDictTypeForm = {
    type: '列表',
    remark: '',
    code: '',
    name: '',
    status: true
  }

  @Ref('sysDictTypeFormRef')
  readonly sysDictTypeFormRef!: ElForm

  sysDictTypePermission = 'system::sysDictType::SysDictType'

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<SysDictTypeForm, SysDictType>

  readonly sysDictTypeFormRules: { code: Array<KWRule.Rule | KWRule.MixinRule>; name: Array<KWRule.Rule | KWRule.MixinRule> } = {
    code: [
      { required: true, message: '请输入字典code', trigger: 'blur' },
      { min: 3, max: 10, message: '字典code的长度3~10个字符之间', trigger: 'blur' }
    ],
    name: [
      { required: true, message: '请输入字典名称', trigger: 'blur' },
      { min: 3, max: 10, message: '字典名称的长度3~10个字符之间', trigger: 'blur' }
    ]
  }

  sysDictTypeRolePage: KWRequest.PageRequest<SysDictTypeForm> = {
    pageSize: 10, // 每页的数据条数
    pageNo: 1 // 默认开始页面
  }

  async sysDictTypeStatusChanged(sysDictType: SysDictType, enabled: boolean): Promise<void> {
    console.log(sysDictType)
    const req: SysDictTypeStatusChange = { id: sysDictType.id, status: enabled }
    console.log(req)
    const { code, msg }: KWResponse.Result = await SysDictTypeStatusChangeRequestApi(req)
    if (code !== 200) {
      sysDictType.status = !sysDictType.status
      this.$message.error(msg || '更新字典类型状态失败!')
    } else {
      this.$message.success('更新新字典类型状态成功!')
    }
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: number): Promise<void> {
    this.title = '编辑用户'
    this.sysDictTypeCodeDisabled = true
    const res = await SysDictTypeGetApi(id)
    this.sysDictTypeForm = res.data
    const type = res.data.type as Model.EnumEntity
    this.sysDictTypeForm.type = type.text
    console.log(res)
    this.sysDictTypeDialogVisble = true
  }

  aditDictTypeClosed(): void {
    this.sysDictTypeDialogVisble = false
    this.sysDictTypeFormRef.resetFields()
  }

  editDictTypeInfo(): void {
    this.sysDictTypeFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.sysDictTypeForm.type = this.sysDictTypeForm.type === '列表' ? Type.列表 : Type.树结构
      const { code, msg } = await SysDictTypeSaveOrUpdateApi(this.sysDictTypeForm)
      if (code !== 200) {
        this.$message.error(msg || '操作字典类型信息失败!')
      } else {
        this.sysDictTypeDialogVisble = false
        this.searchDictType()
        this.$message.success('操作字典类型信息成功!')
      }
    })
  }

  addDictType(): void {
    this.title = '添加用户'
    this.sysDictTypeCodeDisabled = false
    this.sysDictTypeDialogVisble = true
    this.$nextTick(() => {
      this.sysDictTypeFormRef.resetFields()
      this.sysDictTypeForm = {
        type: '列表',
        remark: '',
        code: '',
        name: '',
        status: true
      }
    })
  }

  deleteSysDictType(id: number): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteSysDictTypeApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchDictType()
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

  searchDictType(): void {
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
