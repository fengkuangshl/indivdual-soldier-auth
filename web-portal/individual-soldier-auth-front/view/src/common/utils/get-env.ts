export function getHttpDomain(): string {
  const baseURL = process.env.VUE_APP_HTTP_BASE_URL
  return processBaseURL(baseURL)
}

export function getWsDomain(): string {
  const baseURL = process.env.VUE_APP_WEBSOCKET_BASE_WS_URL
  return processBaseURL(baseURL)
}

function processBaseURL(baseURL: string): string {
  if (!baseURL.endsWith('/')) {
    baseURL += '/'
  }
  return baseURL
}
