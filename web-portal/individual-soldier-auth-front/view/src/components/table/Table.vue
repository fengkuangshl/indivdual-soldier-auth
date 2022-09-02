<template>
  <div>
    <el-table
      :$ready="false"
      :border="true"
      :data="tableDataFilter(pageResult.data)"
      :highlight-current-row="true"
      :row-key="rowKey"
      :default-expand-all="isExpandAll"
      :tree-props="treeProps"
      @cell-click="doTableCellClick"
      @current-change="doChangeTableCurrent"
      @row-click="doTableRowClick"
      @selection-change="doChangeTableSelection"
      @sort-change="doSortChange"
      ref="tableRef"
    >
      <slot></slot>
    </el-table>
    <el-pagination
      :hide-on-single-page="hideOnSinglePage"
      :current-page="pageResult.pageNo"
      :layout="pageLayout"
      :page-count="pageResult.count"
      :page-size="pageResult.pageSize"
      :page-sizes="pageSizes"
      :pager-count="5"
      :total="pageResult.count"
      @current-change="doChangePageCurrent"
      @size-change="doChangePageSize"
    ></el-pagination>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Ref, Vue } from 'vue-property-decorator'
import { cellCallbackParams, ElTable, SortOrder } from 'element-ui/types/table'
import request from '@/fetch'
import { session } from '@/store'
import { ElTableColumn } from 'element-ui/types/table-column'

/**
 * KWTable<T, RT> 是table+分页的组件对象
 * T：查询条件泛型
 * RT：数据结果泛型
 * 1、可以根据url查询后台数据
 * 2、可选择是否需要分页，默认分页
 * 3、不管是否分页都提供了GET和POST两种方式
 * 4、@Prop所注解的都是对外开放的输入条件，除url是调用者必须提供，其它均可选
 * 5、提供选中行的数据及table的各种点击事件
 * 6、提供table的渲染之前和之后及对table的内存数据检索的回调函数
 * 最后此组件主要是借鉴：https://gitee.com/virens/vue-demo/blob/master/src/components/table/VirTable.vue
 */
@Component
export default class KWTable<T, RT> extends Vue {
  // 内部分页查询对象
  pageRequest: KWRequest.PageRequest<T> = {
    pageSize: 10, // 每页的数据条数
    pageNo: 1 // 默认开始页面
  }

  // 内部分页返回对象
  pageResult: KWResponse.PageResult<RT> = {
    pageNo: 0,
    pageSize: 0,
    count: 0,
    code: 0,
    data: [],
    msg: '',
    totalPage: 0
  }

  // table的自身引用
  @Ref('tableRef')
  readonly tableRef!: ElTable

  // 查询的url
  @Prop({ required: true, type: String })
  private url!: string // url

  // 页数
  @Prop({ default: () => 1, type: Number })
  private pageNo!: number

  // 查询条件
  @Prop({ default: () => null, type: Object })
  private param!: T

  // 每页条数
  @Prop({ default: () => 10, type: Number })
  private pageSize!: number

  // table rowKey
  @Prop({ default: 'id', type: [String, Function] })
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private rowKey!: string | ((row: any) => any)

  // 每页条数的可选列表
  @Prop({ default: () => [5, 10, 15, 20], type: [] })
  private pageSizes!: number[]

  // 分页布局的样式
  @Prop({ default: () => 'total, sizes, prev, pager, next, jumper', type: String })
  private pageLayout!: string

  // 如果树型结构table，是否需要展开所以树节点，默认是
  @Prop({ default: () => true, type: Boolean })
  private isExpandAll!: boolean

  // 如果树型结构table，children和hasChildren对应的实体属性的字段名称
  @Prop({
    default: () => ({
      children: 'children',
      hasChildren: 'hasChildren'
    }),
    type: Object
  })
  private treeProps!: { children: string; hasChildren: string }

  // 渲染table之前的数据处理回调函数
  @Prop({
    default: (datas: Array<RT>): Array<RT> => {
      return datas
    },
    type: Function
  })
  private renderPreFn!: (datas: Array<RT>) => Array<RT>

  // 渲染table之后的数据处理回调函数
  @Prop({
    default: (datas: Array<RT>): void => {
      console.log(datas)
    },
    type: Function
  })
  private callbackFn!: (datas: Array<RT>) => void

  // 是否需要分页
  @Prop({ default: () => true, type: Boolean })
  private isPagination!: boolean

  // http的请求类型
  @Prop({ default: () => 'POST', type: String })
  private method!: KWRequest.MethodType

  // 对内存table数据的检索
  @Prop({
    default: (datas: Array<RT>): Array<RT> => {
      return datas
    },
    type: Function
  })
  private tableDataFilter!: (datas: Array<RT>) => Array<RT>

  // 是否展示分页控件
  private hideOnSinglePage = false

  // 当前选中行的数据
  private currentRow!: RT

  // 当前选中多行的数据数组
  private selectionRow: Array<RT> = []

  /**
   * 加载表格数据
   * pageNo:可选 当前页数
   * pageSize：可选 每页的条数
   * param：可选 查询条件
   * 主要是判断是否分页，调用不同的方法
   */
  public async load(pageNo?: number, pageSize?: number, param?: T): Promise<void> {
    this.hideOnSinglePage = false
    if (this.isPagination) {
      this.loadPagination(pageNo, pageSize, param)
    } else {
      this.loadDatas(param)
    }
  }

  /**
   * 不分页所调用的方法
   * param：可选 查询条件
   */
  public async loadDatas(param?: T): Promise<void> {
    this.hideOnSinglePage = true
    const condition = { params: param || this.pageRequest.t }
    const res: KWResponse.Result<Array<RT>> = this.method.toUpperCase() === 'POST' ? await request.post(this.url, param || this.param) : await request.get(this.url, condition)
    // ------------------组装数据开始------------------
    this.pageResult.data = this.renderPreFn(res.data) // 渲染前的回调函数
    this.callbackFn(res.data) // 渲染之后的回调函数
    // ------------------组装数据结束------------------
  }

  /**
   * pageNo:可选 当前页数
   * pageSize：可选 每页的条数
   * param：可选 查询条件
   * 主要是分页所调用的方法
   */
  public async loadPagination(pageNo?: number, pageSize?: number, param?: T): Promise<void> {
    // 如果传入空就使用之前的值
    this.pageRequest.pageNo = pageNo || this.pageRequest.pageNo
    this.pageRequest.pageSize = pageSize || this.pageRequest.pageSize
    this.pageRequest.t = param || this.pageRequest.t
    const params = {
      pageNo: this.pageRequest.pageNo,
      pageSize: this.pageRequest.pageSize,
      sortName: this.pageRequest.sortName,
      sortDir: this.pageRequest.sortDir,
      t: this.pageRequest.t || {}
    }
    const condition = {
      params: params
    }
    // 为什么要用params而不是this.pageRequest，因为万恶的T不允许有一个有空对象
    const res: KWResponse.PageResult<RT> = this.method.toUpperCase() === 'POST' ? await request.post(this.url, params) : await request.get(this.url, condition)
    // ------------------组装数据开始------------------
    this.pageResult.data = this.renderPreFn(res.data) // 渲染前的回调函数
    this.pageResult.pageNo = res.pageNo
    this.pageResult.pageSize = res.pageSize
    this.pageResult.count = res.count
    this.pageResult.code = res.code
    this.pageResult.totalPage = res.totalPage
    this.callbackFn(res.data) // 渲染之后的回调函数
    // ------------------组装数据结束------------------
  }

  /** 重载数据 */
  public reload(): void {
    this.load(this.pageNo, this.pageSize, this.param)
  }

  /**
   * 根据条件查询
   */
  public loadByCondition(param?: T): void {
    this.load(this.pageNo, this.pageSize, param)
  }

  /** 获取指定行 */
  public getRow(row: number): RT | undefined {
    if (row >= 0 && row < this.pageResult.count) {
      return this.pageResult.data[row]
    } else {
      return undefined
    }
  }

  /** 获取当前选择行 */
  public getCurrentRow(): RT {
    return this.currentRow
  }

  /** 设置当前选择行 */
  public setCurrentRow(row: number): void {
    if (row >= 0 && row < this.pageResult.count) {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      this.tableRef.setCurrentRow(this.pageResult.data[row] as any)
    }
  }

  /** 清空被选中行 */
  public clearSelection(): void {
    return this.tableRef.clearSelection()
  }

  /** 获取被选中行 */
  public getSelectionRow(): Array<RT> {
    return this.selectionRow || []
  }

  /** 获取被选中行(KEY值) */
  public getSelectionKey(): Array<string> {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    return this.selectionRow.map((item: any) => {
      if (typeof this.rowKey === 'function') {
        return this.rowKey(item)
      } else {
        return item[this.rowKey]
      }
    })
  }

  /** 设置行被选中/取消选中 */
  public setSelectionRow(key: string, check: boolean): void {
    for (const item of this.pageResult.data) {
      // 如果Rowkey是函数->判断值是否一致
      if (typeof this.rowKey === 'function' && this.rowKey(item) === key) {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        this.tableRef.toggleRowSelection(item as any, check)
      }

      // 如果Rowkey是字符串->判断值是否一致
      if (typeof this.rowKey === 'string' /* && item[this.rowKey] === key */) {
        Object.keys(item).forEach(k => {
          if (k === key) {
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            this.tableRef.toggleRowSelection(item as any, check)
            return false
          }
        })
      }
    }
  }

  /** 组件创建时：从缓存中获取数据 & 注入实例到管理器 */
  private mounted() {
    // 加载缓存配置
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    let cacheParam: any = session.getObj(this.key('p'))
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    let cachePageNum: any = session.getAny(this.key('n'))
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    let cachePageSize: any = session.getAny(this.key('s'))

    // 加载配置条件 & 参数过滤
    cacheParam = Object.assign(cacheParam || {}, this.param)
    cachePageSize = cachePageSize || this.pageSize || 10
    cachePageNum = cachePageNum || this.pageNo || 1

    // 默认第一次加载数据
    this.load(cachePageNum, cachePageSize, cacheParam)
    this.$emit('table-page-init', cacheParam)
  }

  /** 组件销毁时：将表单参数、分页信息存入缓存 */
  private destroyed() {
    session.save(this.key('p'), this.pageRequest.t)
    session.save(this.key('n'), this.pageRequest.pageNo)
    session.save(this.key('s'), this.pageResult.pageSize)
  }

  /** 缓存KEY */
  private key(k: string) {
    return `page-cache-${k}-${this.url}`
  }

  /** 每页条数切换 */
  private doChangePageSize(pageSize: number) {
    this.load(1, pageSize)
    this.$emit('table-page-size', pageSize)
  }

  /** 页码切换 */
  private doChangePageCurrent(pageNo: number) {
    this.load(pageNo)
    this.$emit('table-page-num', pageNo)
  }

  /** 切换当前选择行 */
  private doChangeTableCurrent(currentRow: RT, lastRow: RT) {
    this.$emit('table-row-current', (this.currentRow = currentRow), lastRow)
  }

  /** 改变被选中行 */
  private doChangeTableSelection(selectionRows: RT[]) {
    this.$emit('table-row-selection', (this.selectionRow = selectionRows))
  }

  /** 行被点击 */
  private doTableRowClick(row: RT, event: PointerEvent, column: ElTableColumn) {
    this.$emit('table-row-click', row, column, event)
  }

  /** 单元格被点击 */
  private doTableCellClick(row: RT, column: ElTableColumn, cell: cellCallbackParams, event: PointerEvent) {
    this.$emit('table-cell-click', row, column, cell, event)
  }

  /** 排序事件 */
  private doSortChange(column: { column: ElTableColumn; prop: SortOrder; order: string }) {
    this.pageRequest.sortName = column.prop
    this.pageRequest.sortDir = column.order === 'ascending' ? 'ASC' : 'DESC'
    this.load()
    this.$emit('table-sort-change', column.column, column.prop, column.order)
  }
}
</script>

<style lang="less" scoped>
.el-pagination {
  margin-top: 15px;
  float: right;
}
/* .el-cascader-menu {
  height: 300px;
} */
// .el-cascader-menu__wrap,
// .el-scrollbar__wrap {
//   height: 162px !important;
// }
.el-table {
  margin-top: 15px;
}
</style>
