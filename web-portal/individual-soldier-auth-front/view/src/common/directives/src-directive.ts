// url-directive.ts

import settings from '@/settings'
import { DirectiveOptions } from 'vue'
import { DirectiveBinding } from 'vue/types/options'

const src: DirectiveOptions = {
  bind(el: HTMLElement, binding: DirectiveBinding) {
    // console.log(el)
    if (el instanceof HTMLImageElement) {
      if (binding.value) {
        el.src = binding.value
      } else {
        el.src = settings.defaultAvatar
      }
      el.onerror = function() {
        el.src = settings.defaultAvatar
        el.onerror = null
      }
    }
  }
}

export default src
