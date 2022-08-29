interface ISettings {
  title: string // Overrides the default title
  activePath: string
  accessToken: string
  refreshToken: string
  developmentDomain: string
  userAcceptanceTestDomain: string
  productionDomain: string
  menuTypeItem: string
  menuTypeDirectory: string
  defaultAvatar: string
  permissionEnable: boolean
}

// You can customize below settings :)
const settings: ISettings = {
  title: 'key-win后台管理',
  activePath: 'activePath',
  accessToken: 'access_token',
  refreshToken: 'refresh_token',
  developmentDomain: 'http://127.0.0.1:9902/',
  userAcceptanceTestDomain: 'http://localhost:9200',
  productionDomain: 'http://127.0.0.1:9200',
  menuTypeItem: '菜单',
  menuTypeDirectory: '目录',
  defaultAvatar: require('./assets/head.png'),
  permissionEnable: true
}

export default settings
