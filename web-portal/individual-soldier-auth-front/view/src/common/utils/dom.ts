// 获取滚动的dom
export function getScrollContainer(el: Element, vertical: boolean): Window | Element | ParentNode {
  let parent: Element | ParentNode = el
  while (parent) {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    if ([window, document, document.documentElement].includes(parent as any)) {
      return window
    }
    if (isScroll(parent as Element, vertical)) {
      return parent
    }
    parent = (parent as Element).parentNode as ParentNode
  }
  return parent
}
export function isScroll(el: Element, vertical: boolean): RegExpMatchArray | null {
  const determinedDirection = vertical !== null || vertical !== undefined
  const overflow = determinedDirection ? (vertical ? getComputedStyle(el).overflowY : getComputedStyle(el).overflowX) : getComputedStyle(el).overflow
  return overflow.match(/(scroll|auto)/)
}
