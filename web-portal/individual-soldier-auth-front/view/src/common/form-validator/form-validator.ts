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
  },

  requiredRule: (message: string, trigger: string | Array<string> = 'blur'): KWRule.Rule => {
    return { required: true, message: message, trigger: trigger }
  },

  numberRule: (message: string, trigger: string | Array<string> = 'blur'): KWRule.NumberRule => {
    return {
      type: 'number',
      transform: (value: string) => Number.parseFloat(value),
      message: message,
      trigger: trigger
    }
  },

  dateRule: (message: string, trigger: string | Array<string> = 'blur'): KWRule.DateRule => {
    return { type: 'date', message: message, trigger: trigger }
  },

  arrayRule: (message: string, trigger: string | Array<string> = 'blur'): KWRule.ArrayRule => {
    return { type: 'array', message: message, trigger: trigger }
  },

  mixinRul: (min: number, max: number, message: string, trigger: string | Array<string> = 'blur'): KWRule.MixinRule => {
    return { min: min, max: max, message: message, trigger: trigger }
  }
}

export default FormValidatorRule
