// time-directive.ts

import { DirectiveOptions } from 'vue'
import Time from '@/common/utils/date-util/Time'
import { DirectiveBinding } from 'vue/types/options'

const time: DirectiveOptions = {
  bind(el: HTMLElement, binding: DirectiveBinding) {
    el.innerHTML = Time.getFormatTime(binding.value)
  }
}

export default time
