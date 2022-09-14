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
import hasPermissionEnabled from './permission/has-permission-enabled-directive'
import hasPermissionUpload from './permission/has-permission-upload-directive'
import hasPermissionDownload from './permission/has-permission-download-directive'
import hasPermissionImport from './permission/has-permission-import-directive'
import hasPermissionExport from './permission/has-permission-export-directive'

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
  hasPermissionUpdate: hasPermissionUpdate,
  hasPermissionEnabled: hasPermissionEnabled,
  hasPermissionUpload: hasPermissionUpload,
  hasPermissionDownload: hasPermissionDownload,
  hasPermissionImport: hasPermissionImport,
  hasPermissionExport: hasPermissionExport
}
export default directives
