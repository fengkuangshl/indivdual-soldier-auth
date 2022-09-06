openssl genrsa -out private_key.pem 1024

openssl req -new -key private_key.pem -out rsaCertReq.csr

openssl x509 -req -days 36500 -in rsaCertReq.csr -signkey private_key.pem -out rsaCert.crt

openssl x509 -outform der -in rsaCert.crt -out public_key.der　　　　　　　　　　　　　　　// Create public_key.der For device

openssl pkcs12 -export -out private_key.p12 -inkey private_key.pem -in rsaCert.crt　　// Create private_key.p12 For device. 这一步，请记住你输入的密码，IOS代码里会用到

openssl rsa -in private_key.pem -out rsa_public_key.pem -pubout　　　　　　　　　　　　　// Create rsa_public_key.pem For Java
　
openssl pkcs8 -topk8 -in private_key.pem -out pkcs8_private_key.pem -nocrypt　　　　　// Create pkcs8_private_key.pem For Java