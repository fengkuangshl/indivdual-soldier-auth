import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { Message } from 'element-ui'
import settings from '@/settings'
import { local } from './store'
import { LoginResponse } from './views/login/interface/response'
import router from './router'

let baseURL = settings.developmentDomain
if (process.env.NODE_ENV === 'development') {
  // 设置默认本地开发
  baseURL = settings.developmentDomain
} else if (process.env.VUE_APP_CURRENTMODE === 'uat') {
  // 测试
  baseURL = settings.userAcceptanceTestDomain
} else if (process.env.VUE_APP_CURRENTMODE === 'production') {
  // 默认正式
  baseURL = settings.productionDomain
}

const instance: AxiosInstance = axios.create({
  baseURL: baseURL,
  timeout: 3000
})

function errorHandle(err: AxiosError): Promise<unknown> {
  if (err.code === 'ECONNABORTED' && err.message.indexOf('timeout') !== -1) {
    return onTimeoutError(err)
  } else if (err.response === undefined) {
    return onNetWorkError(err)
  }
  let message = ''
  switch (err.response?.status) {
    case 400:
      message = '请求错误'
      break
    case 401:
      return onAuthenticationError(err)
    case 403:
      message = '拒绝访问'
      break
    case 404:
      message = `请求地址出错: ${err.response?.config.url}`
      break
    case 408:
      message = '请求超时'
      break
    case 500:
      message = '服务器内部错误'
      break
    case 501:
      message = '服务未实现'
      break
    case 502:
      message = '网关错误'
      break
    case 503:
      message = '服务不可用'
      break
    case 504:
      message = '网关超时'
      break
    case 505:
      message = 'HTTP版本不受支持'
      break
    default:
      message = '其他情况'
  }
  Message.error((err.response.data as string) || message)
  return Promise.reject(err)
}

instance.interceptors.request.use(
  (config: AxiosRequestConfig): AxiosRequestConfig => {
    config.headers = config.headers || {}
    config.headers['x-requested-with'] = 'XMLHttpRequest'
    if (!config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    }
    const token: string | null = localStorage.getItem('access_token')
    if (token) {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  },
  (err: AxiosError) => {
    return errorHandle(err)
  }
)

instance.interceptors.response.use(
  (result: AxiosResponse): KWResponse.Result | KWResponse.PageResult | void => {
    return result.data as KWResponse.Result | KWResponse.PageResult | void
  },
  (err: AxiosError) => {
    return errorHandle(err)
  }
)

async function onAuthenticationError(error: AxiosError) {
  local.clear(settings.activePath)
  const { code, data }: KWResponse.Result<LoginResponse> = await getNewToken()
  if (code === 200) {
    local.save(settings.accessToken, data.access_token)
    local.save(settings.refreshToken, data.refresh_token)
    return await instance.request(error.config)
  } else {
    Message.error('用户失效，请重新登录！')
    local.clear(settings.accessToken)
    local.clear(settings.refreshToken)
    router.replace({
      path: '/login'
    })
    return error
  }
}

function onTimeoutError(err: AxiosError) {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const originalRequest = err.config as any
  if (originalRequest.retry) {
    Message.error('网络超时，请稍后再试！')
    return Promise.reject(err)
  }
  originalRequest.retry = true
  return instance.request(originalRequest)
}

function onNetWorkError(err: AxiosError) {
  Message.error('网络异常，请稍后再试！')
  return Promise.reject(err)
}

async function getNewToken(): Promise<KWResponse.Result<LoginResponse>> {
  const refreshToken = local.getStr(settings.refreshToken)
  return await instance.get('user/refresh/' + refreshToken, {})
}

export default instance
