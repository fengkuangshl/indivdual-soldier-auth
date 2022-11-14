FROM nginx:1.19.2
# 将dist文件中的内容复制到 /usr/share/nginx/html/ 这个目录下面
COPY dist/  /usr/share/nginx/html/auth_front
COPY nginx.conf /etc/nginx/nginx.conf
RUN sh -c 'chmod -R 777 /usr/share/nginx/html/auth_front'

