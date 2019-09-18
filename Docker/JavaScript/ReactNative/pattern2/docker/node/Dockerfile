FROM "node:10-alpine"

RUN apk update && apk add bash openjdk8

RUN wget https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip

RUN mkdir -p /usr/android/sdk

RUN unzip sdk-tools-linux-4333796.zip -d /usr/android/sdk

RUN rm -rf sdk-tools-linux-4333796.zip

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV ANDROID_HOME /usr/android
ENV ANDROID_SDK_ROOT $ANDROID_HOME/sdk
ENV PATH $PATH:$ANDROID_SDK_ROOT/tools/bin
ENV PATH $PATH:$ANDROID_SDK_ROOT/emulator
ENV PATH $PATH:$ANDROID_SDK_ROOT/platform-tools

WORKDIR /usr/src/app/

RUN yarn global add expo-cli