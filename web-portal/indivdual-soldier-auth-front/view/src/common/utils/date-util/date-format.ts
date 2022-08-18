import { padLeft } from './pad'

export enum DateFormatType {
  ISO,
  Date,
  Time,
  DateTime
}

function padLeft02Twice(source: number) {
  return padLeft(source, 2, '0')
}

export default function dateFormat(date: string | number | Date, type: DateFormatType = DateFormatType.DateTime): string {
  date = date instanceof Date ? date : new Date(date)

  switch (type) {
    case DateFormatType.Date:
      return `${date.getFullYear()}-${padLeft02Twice(1 + date.getMonth())}-${padLeft02Twice(date.getDate())}`
    case DateFormatType.Time:
      return `${padLeft02Twice(date.getHours())}:${padLeft02Twice(date.getMinutes())}:${padLeft02Twice(date.getSeconds())}`
    case DateFormatType.DateTime:
      return `${date.getFullYear()}-${padLeft02Twice(1 + date.getMonth())}-${padLeft02Twice(date.getDate())} ${padLeft02Twice(date.getHours())}:${padLeft02Twice(date.getMinutes())}:${padLeft02Twice(date.getSeconds())}`
    case DateFormatType.ISO:
    default:
      return date.toISOString()
  }
}
