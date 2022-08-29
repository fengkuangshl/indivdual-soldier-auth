// time-directive.ts
import { DirectiveOptions } from 'vue'
import { DirectiveBinding } from 'vue/types/options'
import settings from '@/settings'
import PermissionUtil from '@/common/utils/permission-util'

const hasPermissionQueryList: DirectiveOptions = {
  bind(el: HTMLElement, binding: DirectiveBinding) {
    if (settings.permissionEnable === true) {
      const { value } = binding

      if (!PermissionUtil.hasPermissionForQueryList(value)) {
        if (el.parentNode != null) {
          el.parentNode.removeChild(el)
        } else {
          el.style.display = 'none'
        }
        // 或者：el.style.display = 'none'
      }
    }
  }
}
export default hasPermissionQueryList
