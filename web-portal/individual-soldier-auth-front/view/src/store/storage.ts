export default class StorageSupport {
  private storage: Storage

  /**
   *  Storage操作类
   * @param storage Storage的实现类
   */
  constructor(storage: Storage) {
    this.storage = storage
  }

  /**
   * 从storage中删除键为key的值
   * @param key 键
   */
  public clear(key: string): void {
    this.storage.removeItem(key)
  }

  /**
   * 将键值对保存到storage中
   * @param key 键
   * @param value 值
   */
  // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types, @typescript-eslint/no-explicit-any
  public save(key: string, value: any): void {
    if (value === undefined) {
      this.storage.removeItem(key)
    } else if (value === null) {
      this.storage.setItem(key, '')
    } else if (typeof value === 'string') {
      this.storage.setItem(key, value)
    } else if (typeof value === 'number') {
      this.storage.setItem(key, value.toString())
    } else if (typeof value === 'boolean') {
      this.storage.setItem(key, value.toString())
    } else {
      this.storage.setItem(key, JSON.stringify(value))
    }
  }

  /**
   * 根据键获取类型为any的值
   * @param key 键
   */
  // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types, @typescript-eslint/no-explicit-any
  public getAny(key: string): any {
    return this.storage.getItem(key)
  }

  /**
   * 根据键获取类型为string的值
   * @param key 键
   */
  // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types, @typescript-eslint/no-explicit-any
  public getStr(key: string): string {
    return this.getAny(key) || ''
  }

  /**
   * 根据键获取类型为Object的值
   * @param key 键
   */
  // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types, @typescript-eslint/no-explicit-any
  public getObj(key: string): any {
    const value = this.getAny(key)
    try {
      if (
        value && //
        value !== 'null' && //
        value !== 'undefined' && //
        value !== '[object Object]'
      ) {
        return JSON.parse(value || '')
      }
    } catch (e) {
      console.error('类型转换失败:' + value + ' to JSON!', e)
    }

    return undefined
  }

  /**
   * 遍历当前Storage下的所有数据
   * @param action 遍历器
   */
  public forEach(action: (k: string, v: string | null) => void): void {
    const length = this.storage.length

    for (let index = 0; index < length; index++) {
      const k = this.storage.key(index)

      if (k !== null && k !== undefined) {
        action(k, this.storage.getItem(k))
      }
    }
  }
}
