<template>
  <div class="kw-text" ref="textRef" :style="textStyle" @mouseenter="mouseenter" @mouseleave="mouseleave">
    {{ value }}
  </div>
</template>

<script lang="ts">
import { getScrollContainer } from '@/common/utils/dom'
import { Component, Prop, Ref, Vue, Watch } from 'vue-property-decorator'
// 显示文字组件，可以设置最多显示几行，超过后会隐藏并且鼠标hover显示全部信息（需要给组件设置宽度）
@Component
export default class KWText extends Vue {
  isShowHover = false
  textStyle = {}
  div: HTMLElement | null = null

  @Prop({ default: '', type: String })
  value!: string

  @Prop({ default: 0, type: [Number, String] })
  row!: number | string

  @Ref('textRef')
  readonly textRef!: HTMLElement

  // get text(): HTMLElement {
  //   const el = this.$refs.text as HTMLElement
  //   return el
  // }

  // eslint-disable-next-line no-undef
  get scrollWrap(): Window | Element | ParentNode {
    return getScrollContainer(this.div as HTMLElement, true)
  }

  @Watch('row')
  onRow(): void {
    this.init()
  }

  @Watch('value')
  onValue(): void {
    this.isShowHover = false
    this.textStyle = {
      cursor: 'text'
    }
    this.$nextTick(() => {
      this.getStyle((this.row as number) - 0)
    })
  }

  mounted(): void {
    this.init()
  }

  init(): void {
    this.div = document.querySelector('.kw-text__hover')
    if (!this.div && (this.row as number)) {
      this.div = document.createElement('div')
      this.div.className = 'kw-text__hover'
      this.div.style.top = '0'
      this.div.style.left = '0'
      document.body.append(this.div)
    }
    if (this.div && (this.row as number)) {
      this.getStyle(this.row as number)
    }
  }

  getStyle(val: number): void {
    const lineHeight = Number.parseInt(getComputedStyle(this.textRef).lineHeight.replace('px', '')) || 20
    const height = Number.parseInt(getComputedStyle(this.textRef).height.replace('px', ''))
    if (height > lineHeight * val) {
      this.isShowHover = true
      this.textStyle = {
        height: `${lineHeight * val}px`,
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        display: '-webkit-box',
        webkitLineClamp: val,
        webkitBoxOrient: 'vertical',
        cursor: 'pointer'
      }
    } else {
      this.isShowHover = false
      this.textStyle = {
        cursor: 'text'
      }
    }
  }

  mouseenter(e: MouseEvent): void {
    if (!this.isShowHover) {
      return
    }
    const d = this.div as HTMLElement
    d.innerHTML = this.value
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const box = (e.target as any).getBoundingClientRect()
    const divRect = d.getBoundingClientRect()
    const windowHeight = innerHeight || document.documentElement.clientHeight
    let top = ''
    let left = ''
    left = box.left + (box.width - divRect.width) / 2 + 'px'
    if (box.bottom + divRect.height - windowHeight > 10) {
      top = box.top - divRect.height + 'px'
    } else {
      top = box.top + box.height + 'px'
    }
    d.style.top = top
    d.style.left = left
    d.classList.add('active')
    this.scrollWrap.addEventListener('scroll', this.scrollFun)
  }

  mouseleave(e: MouseEvent): void {
    if (!this.isShowHover) {
      return
    }
    if (e.relatedTarget !== this.div) {
      this.scrollFun()
    }
    const d = this.div as HTMLElement
    d.onmouseleave = () => {
      this.scrollFun()
    }
  }

  // 最近的dom滚动时,关闭弹窗
  scrollFun(): void {
    const d = this.div as HTMLElement
    d.style.top = '0px'
    d.style.left = '0px'
    d.classList.remove('active')
    this.scrollWrap.removeEventListener('scroll', this.scrollFun)
  }
}
</script>
<style lang="less">
.kw-text {
  position: relative;
}
.kw-text__hover {
  position: absolute;
  background-color: #ffffff;
  color: #666666;
  font-size: 14px;
  line-height: 1.4;
  padding: 6px 10px;
  border-radius: 5px;
  border: 1px solid #eee;
  max-width: 400px;
  transition: opacity 0.3s linear;
  z-index: -1;
  opacity: 0;

  &.active {
    z-index: 99999;
    opacity: 1;
    word-wrap: break-word;
    word-break: break-all;
  }
}
</style>
