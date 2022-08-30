// has-permission-get-id-directive.ts
import { DirectiveOptions } from 'vue'
import { DirectiveBinding } from 'vue/types/options'
import PermissionUtil from '@/common/utils/permission-util'

const hasPermissionGetId: DirectiveOptions = {
  bind(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    if (!PermissionUtil.hasPermissionForGetId(value)) {
      if (el.parentNode != null) {
        el.parentNode.removeChild(el)
      } else {
        el.style.display = 'none'
      }
      // 或者：el.style.display = 'none'
    }
  }
}
export default hasPermissionGetId
