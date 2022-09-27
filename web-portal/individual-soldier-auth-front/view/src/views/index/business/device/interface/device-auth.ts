import { CustomerInfoBase, SearchRequestBase } from '../../base/interface/customer-info-base'

export interface DeviceAuthForm extends CustomerInfoBase {
  // andriod APILevel
  aPILevel: string
  // 屏幕对角线的像素值/对角线的尺寸
  densityDpi: string
  // 高
  heightPixels: string
  // 宽
  widthPixels: string
  // androidId
  androidId: string
  // 主板名称
  board: string
  // 厂商名称
  brand: string
  // 时间
  buildTime: string
  // 硬件识别码
  fingerPrint: string
  // 硬件名称
  hardware: string
  // Mac地址
  macAddress: string
  // 无线电固件版本号
  radio: string
  // serialNumber
  serialNumber: string
  // 软件版本
  softwareVersion: string
  // 授权码
  authCode: string
  // code
  verifyCode: string
  // 唯一码
  uniqueCode: string
  // 设备是否在线
  isOnLine: boolean
}

export interface DeviceAuthSearchRequest extends DeviceAuthForm, SearchRequestBase {}

export interface DeviceAuthDetail extends DeviceAuthForm, Model.BaseField {}

export interface DeviceStatus {
  uniqueCode: string
  status: boolean
}
