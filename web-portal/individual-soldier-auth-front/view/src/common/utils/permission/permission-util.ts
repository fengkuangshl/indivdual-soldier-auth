import settings from '@/settings'
import { UserModule } from '@/store/user-store'
import { LoginSuccessUserInfo } from '@/views/index/system/user/interface/sys-user'
import { PermissionBaseEnum } from './permission-base-enum'

const PermissionUtil = {
  hasPermissionForUpload: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getUploadPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getUploadPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.UPLOAD
  },

  hasPermissionForDownload: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getDownloadPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getDownloadPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.DOWNLOAD
  },

  hasPermissionForImport: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getImportPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getImportPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.IMPORT
  },

  hasPermissionForExport: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getExportPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getExportPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.EXPORT
  },

  hasPermissionForQueryPaged: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getQueryPagedPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getQueryPagedPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.QUERY_PAGED
  },

  hasPermissionForQueryList: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getQueryListPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },
  getQueryListPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.QUERY_LIST
  },

  hasPermissionForGetId: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getGetIdPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getGetIdPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.QUERY_ID
  },

  hasPermissionForEnabled: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getEnabledPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getEnabledPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.UPDATE_ENABLED
  },

  hasPermissionForAdd: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getAddPermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },
  getAddPermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.ADD
  },

  hasPermissionForUpdate: (permissionPrefix: string): boolean => {
    const code = PermissionUtil.getUpdatePermissionExpression(permissionPrefix)
    if (PermissionUtil.hasPermission(code)) {
      return true
    }
    return false
  },

  getUpdatePermissionExpression: (permissionPrefix: string): string => {
    return permissionPrefix + PermissionBaseEnum.MODIFY
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
    return permissionPrefix + PermissionBaseEnum.DELETE
  },

  hasPermission: (code: string): boolean => {
    if (settings.isEnablePermission === false) {
      return true
    }
    const allPermission = '*::*::*'
    const permissions = (UserModule.loginUser as LoginSuccessUserInfo).permissions
    const hasPermissions = permissions.filter(permission => {
      return allPermission === permission.permissionCode || code.includes(permission.permissionCode)
    })
    if (hasPermissions.length === 0) {
      return false
    }
    return true
  },

  hasRole: (code: string): boolean => {
    if (settings.isEnablePermission === false) {
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
