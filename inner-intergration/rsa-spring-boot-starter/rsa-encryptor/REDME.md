#设备端的密码：Keywin4206
##总共生成7个文件
　１、public_key.der 和 private_key.p12 这对公钥私钥是给device用的
  2、rsa_public_key.pem 和 pkcs8_private_key.pem　是给JAVA用的。
  3、它们的源都来自一个私钥：private_key.pem ， 所以device端加密的数据，是可以被JAVA端解密的，反过来也一样。