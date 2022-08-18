import drag from './drag-directive'
import src from './src-directive'
import time from './time-directive'
import { DirectiveOptions } from 'vue'

const directives: { [key: string]: DirectiveOptions } = {
  drag: drag,
  src: src,
  time: time
}
export default directives
