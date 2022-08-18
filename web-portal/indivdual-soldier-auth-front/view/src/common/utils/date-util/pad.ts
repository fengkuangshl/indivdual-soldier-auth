export function padLeft(source: string | number, targetLen: number, padChar: string): string {
  source = (typeof source === 'string' ? source : source.toString()) as string

  if (targetLen > source.length) {
    let diffLen = targetLen - source.length
    for (; diffLen > 0; diffLen--) {
      source = padChar + source
    }
  }

  return source
}

const pad = {
  padLeft
}
export default pad
