import PermissionPrefixUtils from './permission-prefix'

const PermissionCodeUtils = {
  dictTypeGrantDictTypeGotoDictData: PermissionPrefixUtils.dictType + 'GRANT::DICT::TYPE::GO::TO::DICT::DATA',
  dictTypeGrantDictTypeGotoDictTree: PermissionPrefixUtils.dictType + 'GRANT::DICT::TYPE::GO::TO::DICT::TREE',
  menuGrantPagePermission: PermissionPrefixUtils.menu + 'GRANT::PAGE::PERMISSION',
  roleGrantPermission: PermissionPrefixUtils.role + 'ROLE::GRANT',
  userResetPasswrodPermission: PermissionPrefixUtils.user + 'USER::RESET::PASSWORD'
}

export default PermissionCodeUtils
