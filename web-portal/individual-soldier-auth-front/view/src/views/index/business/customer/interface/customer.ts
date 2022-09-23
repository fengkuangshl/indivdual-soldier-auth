import { CustomerInfoBase, SeachRequestBase } from '../../base/interface/customer-info-base'

export interface CustomerInfoForm extends CustomerInfoBase {
  // 公司地址
  companyAddress: string
  // 公司电话
  companyPhone: string
  // 负责人座机
  leadPhone: string
}

export interface CustomerInfoSeachRequest extends CustomerInfoForm, SeachRequestBase {}

export interface CustomerInfoDetail extends CustomerInfoForm, Model.BaseField {}
