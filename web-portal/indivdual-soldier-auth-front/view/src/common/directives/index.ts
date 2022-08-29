import drag from './drag-directive'
import src from './src-directive'
import time from './time-directive'
import { DirectiveOptions } from 'vue'
import hasPermission from './permission/has-permission'
import hasPermissionAdd from './permission/has-permission-add'
import hasPermissionDelete from './permission/has-permission-delete'
import hasPermissionGetId from './permission/has-permission-get-id'
import hasPermissionQueryList from './permission/has-permission-query-list'
import hasPermissionQueryPage from './permission/has-permission-query-paged'
import hasPermissionUpdate from './permission/has-permission-update'

const directives: { [key: string]: DirectiveOptions } = {
  drag: drag,
  src: src,
  time: time,
  hasPermission: hasPermission,
  hasPermissionAdd: hasPermissionAdd,
  hasPermissionDelete: hasPermissionDelete,
  hasPermissionGetId: hasPermissionGetId,
  hasPermissionQueryList: hasPermissionQueryList,
  hasPermissionQueryPage: hasPermissionQueryPage,
  hasPermissionUpdate: hasPermissionUpdate
}
export default directives
