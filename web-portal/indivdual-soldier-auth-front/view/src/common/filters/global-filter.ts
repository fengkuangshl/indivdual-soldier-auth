// 首先在文件中引入Vue
import Vue from 'vue'
import dateFormat, { DateFormatType } from '@/common/utils/date-util/date-format'
// 以下为全局过滤器的定义（可以定义多个）
Vue.filter('dateFormat', function(date: string | number | Date): string {
  return dateFormat(date, DateFormatType.Date)
})

Vue.filter('dateTimeFormat', function(dateTime: string | number | Date): string {
  return dateFormat(dateTime, DateFormatType.DateTime)
})
