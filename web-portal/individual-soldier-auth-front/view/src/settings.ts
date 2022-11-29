interface ISettings {
  title: string // Overrides the default title
  activePath: string
  accessToken: string
  refreshToken: string
  menuTypeItem: string
  menuTypeDirectory: string
  defaultAvatar: string
  isEnablePermission: boolean
  isEnableWebSocket: boolean
}

// You can customize below settings :)
const settings: ISettings = {
  title: 'key-win后台管理',
  activePath: 'activePath',
  accessToken: 'access_token',
  refreshToken: 'refresh_token',
  menuTypeItem: '菜单',
  menuTypeDirectory: '目录',
  defaultAvatar: require('./assets/head.png'),
  isEnablePermission: true,
  isEnableWebSocket: false
}

export default settings
