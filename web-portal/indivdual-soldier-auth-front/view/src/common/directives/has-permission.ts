// time-directive.ts
import { DirectiveOptions } from 'vue'
import { DirectiveBinding } from 'vue/types/options'
import { UserModule } from '@/store/user-store'
import { LoginSuccessUserInfo } from '@/views/index/system/user/interface/sys-user'

const hasPermission: DirectiveOptions = {
  bind(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    const permissionAll = '*::*::*'
    const permissions = (UserModule.loginUser as LoginSuccessUserInfo).permissions
    if (value && value.length > 0) {
      const permissionFlag = value
      const hasPermissions = permissions.filter(permission => {
        return permissionAll === permission.permissionCode || permissionFlag.includes(permission.permissionCode)
      })
      if (hasPermissions.length === 0) {
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
export default hasPermission
