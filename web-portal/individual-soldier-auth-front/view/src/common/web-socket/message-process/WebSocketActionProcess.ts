import { DeviceOffLineNotifyAction, DeviceOnLineNotifyAction, IAction, MessageNotifyAction, WebSocketAction, WebSocketBaseAction } from './WebSocketBaseAction'

class WebSocketActionProcess {
  private static _instance: WebSocketActionProcess | null = null
  private static _items: { [key: string]: IAction } = {}
  private WebSocketActionProcess() {
    console.log('private constructor')
  }

  private static initAction() {
    WebSocketActionProcess.getInstance().set('webSocketAction', new WebSocketAction())
    WebSocketActionProcess.getInstance().set('messageNotifyAction', new MessageNotifyAction())
    WebSocketActionProcess.getInstance().set('deviceOnLineNotifyAction', new DeviceOnLineNotifyAction())
    WebSocketActionProcess.getInstance().set('deviceOffLineNotifyAction', new DeviceOffLineNotifyAction())
  }

  // 获得实例对象
  public static getInstance(): WebSocketActionProcess {
    if (!this._instance) {
      this._instance = new WebSocketActionProcess()
      this.initAction()
    }
    return this._instance
  }

  set(key: string, value: IAction): void {
    WebSocketActionProcess._items[key] = value
    console.log(`set cache with key: '${key}', value: '${value}'`)
  }

  get(key: string): IAction {
    const value = WebSocketActionProcess._items[key]
    console.log(`get cache value: '${value}' with key: '${key}'`)
    return value
  }

  processAction(jsonStr: string): void {
    const josn = JSON.parse(jsonStr)
    const action = this.get(josn.action)
    const webSocketBaseAction = action as WebSocketBaseAction
    webSocketBaseAction.webSocketMessage = josn
    action.processMessage()
  }
}
export default WebSocketActionProcess
