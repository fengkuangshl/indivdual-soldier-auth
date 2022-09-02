function getUinx(): number {
  const date = new Date() // 获取一个时间对象
  return date.getTime() // 获取时间对象的时间戳
}

// 获取今天0点0分0秒的时间戳
function getTodayUnix(): number {
  const date = new Date()
  date.setHours(0)
  date.setMinutes(0)
  date.setSeconds(0)
  date.setMilliseconds(0)
  return date.getTime()
}

// 获取今年1月1日0时0分0秒的时间戳
function getYearUnix(): number {
  const date = new Date()
  date.setMonth(0)
  date.setDate(1)
  date.setHours(0)
  date.setMinutes(0)
  date.setSeconds(0)
  date.setMilliseconds(0)
  return date.getTime()
}

// 获取标准时间年月日
function getLastDate(time: number | string | Date): string {
  const date = new Date(time)
  const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
  const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  console.log(date.getFullYear())
  return date.getFullYear() + '-' + month + '-' + day
}

// 转换时间
function getFormatTime(date: string | number | Date): string {
  date = date instanceof Date ? date : new Date(date)
  const timestamp = date.getTime()
  const now = getUinx() // 现在的时间戳
  const today = getTodayUnix() // 今天0点时间戳
  const year = getYearUnix() // 今年0点时间戳
  const timer = (now - timestamp) / 1000 // 转换成秒级事件戳
  let tip = ''
  if (timer <= 0) {
    tip = '刚刚'
  } else if (Math.floor(timer / 60) <= 0) {
    tip = '刚刚'
  } else if (timer < 3600) {
    tip = Math.floor(timer / 60) + '分钟前'
  } else if (timer >= 3600 && timestamp - today >= 0) {
    tip = Math.floor(timer / 3600) + '小时前'
  } else if (timer / 86400 < 31) {
    tip = Math.ceil(timer / 86400) + '天前'
  } else {
    tip = getLastDate(timestamp)
  }
  return tip
}
const Time = {
  getUinx,
  getTodayUnix,
  getYearUnix,
  getLastDate,
  getFormatTime
}
export default Time
