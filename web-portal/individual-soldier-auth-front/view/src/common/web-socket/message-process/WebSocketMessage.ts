export interface IWebSocketBaseMessage {
  token: string
  fromUserName: string
  message: string
  action: string
}

export interface WebSocketMessage extends IWebSocketBaseMessage {
  toUserName: string
}

export interface WebSocketGroupMessage extends IWebSocketBaseMessage {
  toUserNames: Array<string>
}

export interface WebSocketGroupIdMessage extends IWebSocketBaseMessage {
  groupId: string
}
