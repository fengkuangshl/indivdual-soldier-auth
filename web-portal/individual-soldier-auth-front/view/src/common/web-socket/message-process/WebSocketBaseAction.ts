import { WebSocketMessage } from './WebSocketMessage'
import { Notification } from 'element-ui'
import Bus, { deviceStatusNotification } from '../../event-hub/event-hub'
import { DeviceStatus } from '@/views/index/business/device/interface/device-auth'

export interface IAction {
  processMessage(): void
  getFullJson(): string
}

export abstract class WebSocketBaseAction implements IAction {
  webSocketMessage!: WebSocketMessage

  abstract processMessage(): void

  getFullJson(): string {
    return JSON.stringify(this.webSocketMessage)
  }
}

export class WebSocketAction extends WebSocketBaseAction {
  processMessage(): void {
    console.log(this.getFullJson())
  }
}

export class MessageNotifyAction extends WebSocketBaseAction {
  processMessage(): void {
    Notification.success(this.webSocketMessage.message)
  }
}

export class DeviceOnLineNotifyAction extends MessageNotifyAction {
  processMessage(): void {
    // 设备[" + uniqueCode + "]上线
    this.notificationByStatus('上线', true)
  }

  notificationByStatus(subTile: string, stauts: boolean): void {
    const msg = this.webSocketMessage.message
    const josn: DeviceStatus = JSON.parse(msg)
    josn.status = stauts
    Bus.$emit(deviceStatusNotification, josn)
    Notification.success({ title: '设备' + subTile, dangerouslyUseHTMLString: true, message: this.getHtmlBody(subTile) })
  }

  getHtmlBody(subTile: string): string {
    const msg = this.webSocketMessage.message
    const josn = JSON.parse(msg)
    const htmlBody = '设备[<a href="#/device-auth?uniqueCode=' + josn.uniqueCode + '">' + josn.uniqueCode + '</a>]' + subTile
    return htmlBody
  }
}

export class DeviceOffLineNotifyAction extends DeviceOnLineNotifyAction {
  processMessage(): void {
    super.notificationByStatus('下线', false)
  }
}
