#设备端的密码：Keywin4206
##总共生成7个文件
1、public_key.der 和 private_key.p12 这对公钥私钥是给device用的<br>
2、rsa_public_key.pem 和 pkcs8_private_key.pem　是给JAVA用的<br>
3、它们的源都来自一个私钥：private_key.pem ， 所以device端加密的数据，是可以被JAVA端解密的，反过来也一样<br>
 ##参考文章
 https://blog.51cto.com/u_13538361/3285243
 https://www.cnblogs.com/makemelike/articles/3802518.html