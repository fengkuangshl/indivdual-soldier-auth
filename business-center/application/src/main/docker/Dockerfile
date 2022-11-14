FROM centos:centos7.1.1503

#拷贝jdk
ADD jdk-8u261-linux-x64.rpm jdk-8u261-linux-x64.rpm
RUN rpm -ivh jdk-8u261-linux-x64.rpm
RUN rm jdk-8u261-linux-x64.rpm
#设置环境变量
ENV JAVA_HOME=/usr/java/jdk1.8.0_261-amd64
ENV JRE_HOME=$JAVA_HOME/jre
ENV CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib:$CLASSPATH
ENV PATH=/sbin:$JAVA_HOME/bin:$PATH
#查看环境变量
RUN java -version

ENV LC_ALL=zh_CN.utf8
ENV LANG=zh_CN.utf8
ENV LANGUAGE=zh_CN.utf8
RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8


#RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
VOLUME /tmp
ADD individual-soldier-auth-application.jar app.jar
#RUN apk --no-cache add tzdata  && \
#    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
#    echo "Asia/Shanghai" > /etc/timezone

#设置时间
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
RUN  date +'%x %X.%N'

RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]