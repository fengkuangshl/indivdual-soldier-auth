const FormValidatorRule = {
  checkEmail: (rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void => {
    const regEmail = /^\w+@\w+(\.\w+)+$/ // 验证邮箱的正则表达式
    if (regEmail.test(value)) {
      return cb() // 合法邮箱
    }
    // 返回一个错误提示
    cb(new Error('请输入合法的邮箱'))
  },
  // 验证手机的规则
  checkMobeli: (rule: KWRule.ValidatorRule, value: string, cb: KWRule.CallbackFunction): void => {
    const regMobile = /^1[34578]\d{9}$/
    if (regMobile.test(value)) {
      return cb()
    }
    // 返回一个错误提示
    cb(new Error('请输入合法的手机号码'))
  }
}

export default FormValidatorRule
