export interface CustomerInfoBase {
  // 客户号
  sequence: string
  // 公司名称
  companyName: string
  // 项目号
  projectNo: string
  // 项目号
  projectName: string
  //  授权码
  authDeviceCode: string
  // 客户设备授权到期日
  expireDeviceDate: Date | null | string | number
  // 授权设备数
  authDeviceNum: number
  // 是否校验日期
  isVerify: boolean | string
  // 负责人姓名
  leadName: string
  // 负责人手机
  leadMobile: string
}

export interface SearchRequestBase {
  // 已授权数量
  authorizedQuantity: number
  // 查询条件的开始时间
  startDate: string
  // 查询条件的结束时间
  endDate: string
  // 查询条件的开始数量
  startNum: string
  // 查询条件的结束数量
  endNum: string
}
