export interface DataLogSearchRequest {
  searchContent: string
  fkId: string
}

export interface DataLog {
  content: string
  fkId: string
}

export interface DataLogDetail extends DataLog, Model.BaseField {}
