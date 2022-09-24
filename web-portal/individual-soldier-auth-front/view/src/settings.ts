interface ISettings {
  title: string // Overrides the default title
  activePath: string
  accessToken: string
  refreshToken: string
  developmentHttpDomain: string
  userAcceptanceTestHttpDomain: string
  productionHttpDomain: string
  menuTypeItem: string
  menuTypeDirectory: string
  defaultAvatar: string
  isEnablePermission: boolean
  developmentWsDomain: string
  userAcceptanceTestWsDomain: string
  productionWsDomain: string
  isEnableWebSocket: boolean
}

// You can customize below settings :)
const settings: ISettings = {
  title: 'key-win后台管理',
  activePath: 'activePath',
  accessToken: 'access_token',
  refreshToken: 'refresh_token',
  developmentHttpDomain: 'http://127.0.0.1:9902/',
  userAcceptanceTestHttpDomain: 'http://localhost:9200',
  productionHttpDomain: 'http://127.0.0.1:9200',
  menuTypeItem: '菜单',
  menuTypeDirectory: '目录',
  defaultAvatar: require('./assets/head.png'),
  isEnablePermission: true,
  developmentWsDomain: 'ws://127.0.0.1:9902/ws/',
  userAcceptanceTestWsDomain: 'ws://127.0.0.1:9902/ws/',
  productionWsDomain: 'ws://127.0.0.1:9902/ws/',
  isEnableWebSocket: true
}

export default settings
