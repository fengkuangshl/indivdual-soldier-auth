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
        <el-col :span="4">
          <el-tree :data="treeData" v-hasPermissionQueryList="sysDictTreePermissionPrefix" show-checkbox
            default-expand-all check-strictly :props="defaultProps" ref="treeLeft" highlight-current node-key="id"
            @node-click="handleNodeClick" @check-change="handleCheckChange">
          </el-tree>
        </el-col>
        <el-col :span="20">
          <el-row>
            <el-col :span="7">
              <el-input placeholder="请输入内容" v-model="t.value" v-hasPermissionQueryPage="sysDictTreePermissionPrefix">
                <el-button slot="append" class="search-primary" icon="el-icon-search" @click="searchDictTree">
                </el-button>
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-button tree="primary" v-hasPermissionAdd="sysDictTreePermissionPrefix" @click="addDictTree">添加字典数据
              </el-button>
            </el-col>
          </el-row>
          <KWTable url="sysDictTree/findSysDictTreeByPaged" v-hasPermissionQueryPage="sysDictTreePermissionPrefix"
            :defaultLoadData="false" style="width: 100%" ref="kwTableRef">
            <el-table-column type="index" width="80" label="序号"></el-table-column>
            <el-table-column prop="label" sortable="custom" label="标签"> </el-table-column>
            <el-table-column prop="value" sortable="custom" label="键值"> </el-table-column>
            <el-table-column prop="sort" label="排序" sortable="custom"></el-table-column>
            <el-table-column prop="createDate" label="创建时间" sortable="custom">
              <template slot-scope="scope">{{ scope.row.createDate | dateTimeFormat }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态" sortable="custom" v-if="hasPermissionEnabled()">
              <template v-slot="scope">
                <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949"
                  @change="sysDictTreeStatusChanged(scope.row, scope.row.status)">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button type="primary" icon="el-icon-edit" v-hasPermissionUpdate="sysDictTreePermissionPrefix"
                  size="mini" @click="showEditDialog(scope.row.id)">
                </el-button>
                <el-button type="danger" icon="el-icon-delete" v-hasPermissionDelete="sysDictTreePermissionPrefix"
                  size="mini" @click="deleteSysDictTree(scope.row.id)">
                </el-button>
              </template>
            </el-table-column>
          </KWTable>
        </el-col>
      </el-row>
    </el-card>
    <el-dialog :title="title" @close="aditDictTreeClosed" :visible.sync="sysDictTreeDialogVisble" width="23%">
      <el-form :model="sysDictTreeForm" :rules="sysDictTreeFormRules" ref="sysDictTreeFormRef" label-width="100px">
        <el-form-item label="父节点" prop="parentId">
          <KWTreeSelect v-model="sysDictTreeForm.parentId" filterable :data="treeData" v-on:input="input"
            :props="defaultProps" />
        </el-form-item>
        <el-form-item label="字典键值" prop="value">
          <el-input v-model="sysDictTreeForm.value" style="max-width: 220px;" :disabled="sysDictTreeValueDisabled">
          </el-input>
        </el-form-item>
        <el-form-item label="字典标签" prop="label">
          <el-input v-model="sysDictTreeForm.label" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="字典排序" prop="sort">
          <el-input v-model="sysDictTreeForm.sort" type="number" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="字典状态" prop="status">
          <el-radio-group v-model="sysDictTreeForm.status">
            <el-radio label="启用"></el-radio>
            <el-radio label="禁用"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="扩展属性1" prop="attr1">
          <el-input v-model="sysDictTreeForm.attr1" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性2" prop="attr2">
          <el-input v-model="sysDictTreeForm.attr2" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性3" prop="attr3">
          <el-input v-model="sysDictTreeForm.attr3" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性4" prop="attr4">
          <el-input v-model="sysDictTreeForm.attr4" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="扩展属性5" prop="attr5">
          <el-input v-model="sysDictTreeForm.attr5" style="max-width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="sysDictTreeForm.remark" style="max-width: 220px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="sysDictTreeDialogVisble = false">取 消</el-button>
        <el-button tree="primary" @click="editDictTreeInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ElForm } from 'element-ui/types/form'
import { Component, Vue, Ref } from 'vue-property-decorator'
import { ElTree } from 'element-ui/types/tree'
import { SysDictTreeForm, SysDictTree, SysDictTreeStatusChange, SysDictTreeSearch } from './interface/dict-tree'
import { SysDictTreeGetApi, DeleteSysDictTreeApi, SysDictTreeSaveOrUpdateApi, SysDictTreeStatusChangeRequestApi, GetSysDictTreeByTypeApi } from './dict-tree-api'
import KWTable from '@/components/table/Table.vue'
import KWTreeSelect from '@/components/select-tree/TreeSelect.vue'
import FormValidatorRule from '@/common/form-validator/form-validator'
import PermissionPrefixUtils from '@/common/utils/permission/permission-prefix'
import PermissionUtil from '@/common/utils/permission/permission-util'
@Component({
  components: {
    KWTable,
    KWTreeSelect
  }
})
export default class DictTree extends Vue {
  treeData: Array<SysDictTree> = [
    {
      id: '-1',
      cascadeCode: '',
      subDictTree: [],
      parentId: -100,
      sort: 0,
      value: '',
      remark: '',
      label: 'root',
      type: 0,
      attr1: '',
      attr2: '',
      attr3: '',
      attr4: '',
      attr5: '',
      status: true
    }
  ]

  defaultProps = {
    children: 'subDictTree',
    label: 'label',
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    disabled: function (data: SysDictTree, node: Node): boolean {
      if (data) {
        return !data.status
      }
      return false
    }
  }

  checkedKey = -100

  t: SysDictTreeSearch = {
    cascadeCode: '',
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
    status: true
  }

  title = ''
  navigationTitle = '字典数据管理'
  dictTypeId = '-1'
  sysDictTreeDialogVisble = false
  sysDictTreeValueDisabled = true
  sysDictTreeForm: SysDictTreeForm = {
    parentId: '',
    cascadeCode: '',
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
    status: true
  }

  @Ref('sysDictTreeFormRef')
  readonly sysDictTreeFormRef!: ElForm

  @Ref('treeLeft')
  readonly treeLeft!: ElTree<string | number | null, SysDictTree>

  sysDictTreePermissionPrefix = PermissionPrefixUtils.dictTree

  @Ref('kwTableRef')
  readonly kwTableRef!: KWTable<SysDictTreeSearch, SysDictTree>

  readonly sysDictTreeFormRules: { parentId: Array<KWRule.Rule>; label: Array<KWRule.Rule | KWRule.MixinRule>; value: Array<KWRule.Rule | KWRule.MixinRule>; sort: Array<KWRule.Rule | KWRule.NumberRule> } = {
    parentId: [FormValidatorRule.requiredRule('请选择父节点')],
    label: [FormValidatorRule.requiredRule('请输入字典标签'), FormValidatorRule.mixinRul(2, 10, '字典标签的长度2~10个字符之间')],
    value: [FormValidatorRule.requiredRule('请输入字典键值'), FormValidatorRule.mixinRul(2, 10, '字典键值的长度2~10个字符之间')],
    sort: [FormValidatorRule.requiredRule('请输入字典排序'), FormValidatorRule.numberRule('请输入数字')]
  }

  hasPermissionEnabled(): boolean {
    return PermissionUtil.hasPermissionForEnabled(this.sysDictTreePermissionPrefix)
  }

  async sysDictTreeStatusChanged(sysDictTree: SysDictTree, enabled: boolean): Promise<void> {
    console.log(sysDictTree)
    const req: SysDictTreeStatusChange = { id: sysDictTree.id, status: enabled }
    console.log(req)
    const { code, msg }: KWResponse.Result = await SysDictTreeStatusChangeRequestApi(req)
    if (code !== 200) {
      sysDictTree.status = !sysDictTree.status
      this.$message.error(msg || '更新字典数据状态失败!')
    } else {
      this.getSysDictTree()
      this.$message.success('更新新字典数据状态成功!')
    }
  }

  // 展示编辑用于的对话框
  async showEditDialog(id: string): Promise<void> {
    this.title = '编辑数据字典'
    this.sysDictTreeValueDisabled = true
    const res = await SysDictTreeGetApi(id)
    this.sysDictTreeForm = res.data
    this.sysDictTreeForm.status = (this.sysDictTreeForm.status as boolean) ? '启用' : '禁用'
    console.log(res)
    this.sysDictTreeDialogVisble = true
  }

  aditDictTreeClosed(): void {
    this.sysDictTreeDialogVisble = false
    this.sysDictTreeFormRef.resetFields()
    this.sysDictTreeForm.status = (this.sysDictTreeForm.status as string) === '启用'
  }

  editDictTreeInfo(): void {
    this.sysDictTreeFormRef.validate(async valid => {
      if (!valid) {
        return false
      }
      this.sysDictTreeForm.status = (this.sysDictTreeForm.status as string) === '启用'
      this.sysDictTreeForm.type = this.dictTypeId
      const { code, msg } = await SysDictTreeSaveOrUpdateApi(this.sysDictTreeForm)
      if (code !== 200) {
        this.$message.error(msg || '操作字典数据信息失败!')
      } else {
        this.sysDictTreeDialogVisble = false
        this.searchDictTree()
        this.getSysDictTree()
        this.$message.success('操作字典数据信息成功!')
      }
    })
  }

  addDictTree(): void {
    this.title = '添加字典数据'
    this.sysDictTreeValueDisabled = false
    this.sysDictTreeDialogVisble = true
    this.$nextTick(() => {
      const parentId = this.sysDictTreeForm.parentId
      const cascadeCode = this.sysDictTreeForm.cascadeCode
      this.sysDictTreeFormRef.resetFields()
      this.sysDictTreeForm = {
        parentId: parentId,
        cascadeCode: cascadeCode,
        sort: 0,
        label: '',
        value: '',
        remark: '',
        type: '0',
        attr1: '',
        attr2: '',
        attr3: '',
        attr4: '',
        attr5: '',
        status: '启用'
      }
    })
  }

  async getSysDictTree(): Promise<void> {
    const { code, data, msg } = await GetSysDictTreeByTypeApi(this.dictTypeId)
    if (code !== 200) {
      this.$message.error(msg || '字典树获取失败')
    } else {
      this.treeData[0].subDictTree = []
      this.treeData[0].subDictTree.push(...data)
      const key = this.checkedKey
      this.$nextTick(() => {
        this.checkedKey = key
        this.setChecked(this.treeData)
      })
    }
  }

  setChecked(treeData: Array<SysDictTree>, disable = true): void {
    if (treeData && treeData.length > 0) {
      for (const key in treeData) {
        if (Object.prototype.hasOwnProperty.call(treeData, key)) {
          const element = treeData[key]
          if (!disable) {
            element.status = disable
          }
          if (element.id === this.checkedKey) {
            this.treeLeft.setChecked(element, true, false)
          }
          if (element.subDictTree && element.subDictTree.length > 0) {
            this.setChecked(element.subDictTree, element.status as boolean)
          }
        }
      }
    }
  }

  input(val: string | number): void {
    console.log(val)
    this.sysDictTreeForm.parentId = val
  }

  deleteSysDictTree(id: string): void {
    this.$confirm('确定要删除, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        const { code, msg } = await DeleteSysDictTreeApi(id)
        if (code !== 200) {
          this.$message.error(msg || '删除失败!')
        } else {
          this.searchDictTree()
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

  searchDictTree(): void {
    this.kwTableRef.loadByCondition(this.t)
  }

  mounted(): void {
    if (this.$route.query.id != null) {
      this.sysDictTreeForm.type = this.$route.query.id as string
      this.dictTypeId = this.sysDictTreeForm.type
      this.t.type = this.sysDictTreeForm.type
    }
    if (this.$route.query.name != null) {
      this.navigationTitle = (this.$route.query.name as string) + '的字典数据管理'
    }
    this.searchDictTree()
    this.getSysDictTree()
  }

  handleNodeClick(data: SysDictTree): void {
    console.log(data)
  }

  handleCheckChange(data: SysDictTree, checked: boolean, indeterminate: boolean): void {
    console.log(data, checked, indeterminate)
    if (checked) {
      this.t.cascadeCode = data.cascadeCode
      this.sysDictTreeForm.parentId = data.id as number
      this.checkedKey = data.id as number
      this.treeLeft.setCheckedKeys([data.id])
    } else {
      if (this.checkedKey === data.id) {
        this.t.cascadeCode = ''
        this.sysDictTreeForm.parentId = ''
        this.checkedKey = -100
      }
    }
    // this.searchDictTree()
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
