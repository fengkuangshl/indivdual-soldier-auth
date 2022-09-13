import { VuexModule, Mutation, Action, getModule, Module } from 'vuex-module-decorators'
import store, { local } from '@/store'
import Socket from '@/common/web-socket/web-socket'
import settings from '@/settings'
import { getWsDomain } from '@/common/utils/get-env'
import WebSocketActionProcess from '@/common/web-socket/message-process/WebSocketActionProcess'

export interface ISocketState<T, RT> {
  webSocket: Socket<T, RT> | null
  message: RT | null
  msg: string
}

@Module({ dynamic: true, store, name: 'socket' })
class SocketStore<T, RT> extends VuexModule implements ISocketState<T, RT> {
  public webSocket: Socket<T, RT> | null = null
  public message: RT | null = null
  public msg = ''

  get getWebSocket() {
    return this.webSocket
  }

  get getMessage() {
    console.log(this.message, 'socket.message')
    return this.message
  }

  get getMsg() {
    return this.msg
  }

  @Mutation
  public CHANGE_SOCKET(webSocket: Socket<T, RT>): void {
    this.webSocket = webSocket
  }

  @Mutation
  public CHANGE_MESSAGE(message: RT): void {
    this.message = message
    console.log(this.message, 'RT')
  }

  @Mutation
  public CHANGE_MESSAGE_STR(message: string): void {
    this.msg = message
    console.log(this.msg, 'string')
  }

  @Action
  public initSocket(): void {
    let ws = getWsDomain()
    console.log('action:' + ws)
    ws = ws + local.getStr(settings.accessToken)
    console.log('full url:', ws)
    const wbSocket = new Socket<T, RT>({ url: ws })
    wbSocket.onmessage((data: RT) => {
      const str = JSON.stringify(data)
      WebSocketActionProcess.getInstance().processAction(str)
    })
    this.context.commit('CHANGE_SOCKET', wbSocket)
  }

  @Mutation
  public DESTROY_SOCKET(): void {
    const socket = this.webSocket as Socket<T, RT>
    socket.destroy()
  }

  @Action({ commit: 'DESTROY_SOCKET' })
  public destroySocket(): void {
    console.log('destroy socket..')
  }
}

export const SocketModule = getModule(SocketStore)
