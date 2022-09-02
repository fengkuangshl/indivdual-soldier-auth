import settings from '@/settings'

export function getHttpDomain(): string {
  let baseURL = settings.developmentHttpDomain
  if (process.env.NODE_ENV === 'development') {
    // 设置默认本地开发
    baseURL = settings.developmentHttpDomain
  } else if (process.env.VUE_APP_CURRENTMODE === 'uat') {
    // 测试
    baseURL = settings.userAcceptanceTestHttpDomain
  } else if (process.env.VUE_APP_CURRENTMODE === 'production') {
    // 默认正式
    baseURL = settings.productionHttpDomain
  }
  return baseURL
}

export function getWsDomain(): string {
  let baseURL = settings.developmentWsDomain
  if (process.env.NODE_ENV === 'development') {
    // 设置默认本地开发
    baseURL = settings.developmentWsDomain
  } else if (process.env.VUE_APP_CURRENTMODE === 'uat') {
    // 测试
    baseURL = settings.userAcceptanceTestWsDomain
  } else if (process.env.VUE_APP_CURRENTMODE === 'production') {
    // 默认正式
    baseURL = settings.productionWsDomain
  }
  return baseURL
}
