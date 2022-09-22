<template>
  <el-select ref="select" :value="value" placeholder="请选择" clearable :disabled="disabled" :filterable="filterable"
    :filter-method="filterMethod" @clear="clear" @visible-change="visibleChange">
    <el-option ref="option" class="tree-select__option" :value="optionData.id" :label="optionData.name">
      <el-tree ref="tree" class="tree-select__tree" :node-key="nodeKey" :data="data" :props="props"
        :default-expanded-keys="[value]" highlight-current :expand-on-click-node="false"
        :filter-node-method="filterNode" @node-click="handleNodeClick" />
    </el-option>
  </el-select>
</template>

<script lang="ts">
import { ElSelect } from 'element-ui/types/select'
import { ElTree } from 'element-ui/types/tree'
import { Component, Prop, Ref, Vue, Watch } from 'vue-property-decorator'
import { OptionData, TreeSelectData } from './interface/tree-select'
/**
 * KWTreeSelect<T extends TreeSelectData> 是一个SELECT的组件中嵌套树的对象
 * TreeSelectData：接受的数据类型父类，
 * 最后此组件主要是借鉴：https://zhuanlan.zhihu.com/p/415995268
 * https://github.com/cyounggao/vue-cy-admin/blob/master/src/components/Common/CyTreeSelect.vue
 */
@Component
export default class KWTreeSelect<T extends TreeSelectData> extends Vue {
  optionData: OptionData = { id: -1, name: '' }

  filterFlag = false

  // select 的自身引用
  @Ref('select')
  readonly select!: ElSelect

  @Ref('tree')
  readonly tree!: ElTree<string | number | null, T>

  // v-model绑定
  @Prop({ type: [String, Number], default: '' })
  private value!: string | number

  // 树形的数据
  @Prop({
    type: Array,
    default: function () {
      return []
    }
  })
  private data!: Array<T>

  // 每个树节点用来作为唯一标识的属性
  @Prop({ type: [String, Number], default: 'id' })
  private nodeKey!: string | number

  @Prop({ type: Boolean, default: false })
  private filterable!: boolean

  @Prop({ type: Boolean, default: false })
  private disabled!: boolean

  // tree的props配置
  @Prop({
    type: Object,
    default: function () {
      return {
        label: 'label',
        children: 'children',
        hasChildren: 'hasChildren'
      }
    }
  })
  private props!: { children: string; label: string; hasChildren: string }

  @Watch('value', { immediate: true })
  onValue(val: string | number): void {
    console.log(val)
    if (!this.isEmpty(this.data)) {
      this.init(val)
    }
  }

  @Watch('data', { immediate: true })
  onData(val: Array<T>): void {
    console.log(val)
    if (!this.isEmpty(val)) {
      this.init(this.value)
    }
  }

  // 是否为空
  isEmpty(val: Array<T>): boolean {
    for (const key in val) {
      return false
    }
    return true
  }

  handleNodeClick(data: T): void {
    this.$emit('input', (data as never)[this.nodeKey])
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const s = this.select as any
    s.visible = false
  }

  init(val: string | number): void {
    if (val) {
      this.$nextTick(() => {
        this.tree.setCurrentKey(val)
        const node = this.tree.getNode(val)
        if (node) {
          this.optionData.id = val
          this.optionData.name = node.label
        }
      })
    } else {
      this.$nextTick(() => {
        this.tree.setCurrentKey(null)
      })
    }
  }

  visibleChange(e: boolean): void {
    if (e) {
      this.filterFlag && this.tree.filter('')
      this.filterFlag = false
      const selectDom = this.tree.$el.querySelector('.is-current')
      setTimeout(() => {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const s = this.select as any
        s.scrollToOption({ $el: selectDom })
      }, 0)
    }
  }

  clear(): void {
    this.$emit('input', '')
  }

  filterMethod(val: string): void {
    this.filterFlag = true
    this.tree.filter(val)
  }

  filterNode(value: string, data: T): boolean {
    if (!value) return true
    const label = this.props.label || 'name'
    return ((data as never)[label] as string).indexOf(value) !== -1
  }
}
</script>

<style lang="less">
.tree-select__option {
  height: auto;
  line-height: 1;
  padding: 0;
  background-color: #fff;
}

.tree-select__tree {
  padding: 4px 20px;
  font-weight: 400;
  .el-tree-node.is-current > .el-tree-node__content {
    color: #ff9900;
    font-weight: 700;
  }
}
</style>
