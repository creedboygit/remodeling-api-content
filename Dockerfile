FROM 691128264298.dkr.ecr.ap-northeast-2.amazonaws.com/remodeling/amazoncorretto:17-alpine as jdk-deps
COPY build/libs/*.jar app.jar
RUN unzip /app.jar -d temp &&  \
    jdeps  \
      --print-module-deps \
      --ignore-missing-deps \
      --recursive \
      --multi-release 17 \
      --class-path="./temp/BOOT-INF/lib/*" \
      --module-path="./temp/BOOT-INF/lib/*" \
      /app.jar > /modules.txt

FROM 691128264298.dkr.ecr.ap-northeast-2.amazonaws.com/remodeling/amazoncorretto:17-alpine as jdk
COPY --from=jdk-deps /modules.txt /modules.txt
RUN apk add --no-cache binutils && \
    jlink \
     --verbose \
     --add-modules "$(cat /modules.txt),jdk.crypto.ec,jdk.crypto.cryptoki" \
     --strip-debug \
     --no-man-pages \
     --no-header-files \
     --compress=2 \
     --output /jre

FROM 691128264298.dkr.ecr.ap-northeast-2.amazonaws.com/remodeling/alpine:latest
RUN apk add curl binutils fontconfig libuuid ttf-dejavu --no-cache
RUN ln -s /usr/lib/libfontconfig.so.1 /usr/lib/libfontconfig.so && \
    ln -s /lib/libuuid.so.1 /usr/lib/libuuid.so.1 && \
    ln -s /lib/libc.musl-x86_64.so.1 /usr/lib/libc.musl-x86_64.so.1
ENV LD_LIBRARY_PATH /usr/lib
ENV LANG=C.UTF-8 LC_ALL=en_US.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH="$JAVA_HOME/bin:${PATH}"
COPY --from=jdk /jre $JAVA_HOME
COPY build/libs/*.jar app.jar
ARG SPRING_PROFILES_ACTIVE=local
ENV JAVA_OPTS=" -XX:+UseParallelGC -XX:MaxRAMPercentage=75 -Duser.timezone=Asia/Seoul -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom ${JAVA_OPTS} -jar /app.jar

#FROM 691128264298.dkr.ecr.ap-northeast-2.amazonaws.com/remodeling/amazoncorretto:17-al2-jdk
##RUN apk add curl --no-cache
#COPY build/libs/*.jar app.jar
#ARG SPRING_PROFILES_ACTIVE=local
#ENV JAVA_OPTS=" -XX:+UseG1GC -Duser.timezone=Asia/Seoul -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"
#ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /app.jar"]
