import drag from './drag-directive'
import src from './src-directive'
import time from './time-directive'
import { DirectiveOptions } from 'vue'
import hasPermission from './permission/has-permission-directive'
import hasPermissionAdd from './permission/has-permission-add-directive'
import hasPermissionDelete from './permission/has-permission-delete-directive'
import hasPermissionGetId from './permission/has-permission-get-id-directive'
import hasPermissionQueryList from './permission/has-permission-query-list-directive'
import hasPermissionQueryPage from './permission/has-permission-query-paged-directive'
import hasPermissionUpdate from './permission/has-permission-update-directive'

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
