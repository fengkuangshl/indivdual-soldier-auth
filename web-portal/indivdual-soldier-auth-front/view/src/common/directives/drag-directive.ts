// drag-directive.ts

import { DirectiveOptions } from 'vue'

const drag: DirectiveOptions = {
  bind(el: HTMLElement) {
    const odiv: HTMLElement = el // 获取当前元素
    odiv.onmousedown = (e: MouseEvent) => {
      // 算出鼠标相对元素的位置
      const disX = e.clientX - odiv.offsetLeft
      const disY = e.clientY - odiv.offsetTop
      // console.log('onmousedown:::::::::::::' + disX + '::::::::::::::' + disY)
      document.onmousemove = (e: MouseEvent) => {
        const windowHeight = document.documentElement.clientHeight
        const windowWidth = document.documentElement.clientWidth
        // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
        const left = e.clientX - disX
        const top = e.clientY - disY
        // console.log('befor:' + left + '::::' + top)
        // top = top < 0 ? 0 : top
        // top = top > windowHeight - odiv.offsetHeight ? windowHeight - odiv.offsetHeight : top
        // left = left < 0 ? 0 : left
        // left = left > windowWidth - odiv.offsetWidth ? windowWidth - odiv.offsetWidth : left
        // console.log('after:' + left + '::::' + top)
        // 绑定元素位置到positionX和positionY上面
        // this.positionX = top
        // this.positionY = left

        // 移动当前元素
        odiv.style.left = left + 'px'
        odiv.style.top = top + 'px'
      }
      document.onmouseup = () => {
        document.onmousemove = null
        document.onmouseup = null
      }
    }
  }
}

export default drag
