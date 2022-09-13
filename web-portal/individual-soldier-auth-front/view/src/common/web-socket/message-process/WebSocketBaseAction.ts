import { WebSocketMessage } from './WebSocketMessage'
import { Notification } from 'element-ui'

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
    Notification.success({ title: '设备上线', dangerouslyUseHTMLString: true, message: this.webSocketMessage.message })
  }
}

export class DeviceOffLineNotifyAction extends MessageNotifyAction {
  processMessage(): void {
    Notification.success({ title: '设备下线', dangerouslyUseHTMLString: true, message: this.webSocketMessage.message })
  }
}
