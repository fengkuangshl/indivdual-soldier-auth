package com.key.win.rsa.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptModel implements IEncryptor {

    //时间戳
    private long timestamp;
    //实际的业务请求数据转化成的Json字符串再进行加密得到的密文
    private String data;
    //生成规则算法伪代码是SHA-256(data=xxx&timestamp=11111)，防篡改
    private String sign;
}
