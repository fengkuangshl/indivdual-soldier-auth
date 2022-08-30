import settings from '@/settings'
import { UserModule } from '@/store/user-store'
import { LoginSuccessUserInfo } from '@/views/index/system/user/interface/sys-user'

const PermissionUtil = {
  hasPermissionForQueryPaged: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getQueryPagedPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getQueryPagedPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + '::QUERY::PAGED'
  },

  hasPermissionForQueryList: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getQueryListPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },
  getQueryListPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + '::QUERY::LIST'
  },

  hasPermissionForGetId: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getGetIdPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getGetIdPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + '::QUERY::ID'
  },

  hasPermissionForAdd: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getAddPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getAddPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + '::ADD'
  },

  hasPermissionForUpdate: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getUpdatePermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getUpdatePermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + '::MODIFY'
  },

  hasPermissionForDelete: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getDeletePermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  hasPermissions(codes: Array<string>): boolean {
    for (const key in codes) {
      if (Object.prototype.hasOwnProperty.call(codes, key)) {
        const code = codes[key]
        if (!PermissionUtil.hasPermission(code)) {
          return false
        }
      }
    }
    return true
  },

  hasAnyPermission(codes: Array<string>): boolean {
    for (const key in codes) {
      if (Object.prototype.hasOwnProperty.call(codes, key)) {
        const code = codes[key]
        if (PermissionUtil.hasPermission(code)) {
          return true
        }
      }
    }
    return false
  },

  hasRoles(roles: Array<string>): boolean {
    for (const key in roles) {
      if (Object.prototype.hasOwnProperty.call(roles, key)) {
        const code = roles[key]
        if (!PermissionUtil.hasRole(code)) {
          return false
        }
      }
    }
    return true
  },

  hasAnyRole(roles: Array<string>): boolean {
    for (const key in roles) {
      if (Object.prototype.hasOwnProperty.call(roles, key)) {
        const code = roles[key]
        if (PermissionUtil.hasRole(code)) {
          return true
        }
      }
    }
    return false
  },

  getDeletePermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + '::DELETE'
  },

  hasPermission: (code: string): boolean => {
    if (settings.permissionEnable === false) {
      return true
    }
    const permissions = (UserModule.loginUser as LoginSuccessUserInfo).permissions
    const hasPermissions = permissions.filter(permission => {
      return code.includes(permission.permissionCode)
    })
    if (hasPermissions.length === 0) {
      return false
    }
    return true
  },

  hasRole: (code: string): boolean => {
    if (settings.permissionEnable === false) {
      return true
    }
    const sysRoles = (UserModule.loginUser as LoginSuccessUserInfo).user.sysRoles
    const hasPermissions = sysRoles.filter(sysRole => {
      return code.includes(sysRole.code)
    })
    if (hasPermissions.length === 0) {
      return false
    }
    return true
  }
}

export default PermissionUtil
