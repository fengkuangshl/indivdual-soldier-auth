// has-permission-add-directive.ts
import { DirectiveOptions } from 'vue'
import { DirectiveBinding } from 'vue/types/options'
import PermissionUtil from '@/common/utils/permission-util'

const hasPermissionAdd: DirectiveOptions = {
  bind(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    if (!PermissionUtil.hasPermissionForAdd(value)) {
      if (el.parentNode != null) {
        el.parentNode.removeChild(el)
      } else {
        el.style.display = 'none'
      }
      // 或者：el.style.display = 'none'
    }
  }
}
export default hasPermissionAdd
