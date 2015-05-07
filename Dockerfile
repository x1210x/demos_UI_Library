FROM ubuntu:14.04
MAINTAINER x1210x <x1210x@gmail.com>

RUN apt-get update

# Install Java 8
RUN apt-get -y install oracle-java8-installer

# Install android sdk
RUN wget http://dl.google.com/android/android-sdk_r24.2-linux.tgz
RUN tar -xvzf android-sdk_r24.2-linux.tgz
RUN mv android-sdk-linux /usr/local/android-sdk

# Add android tools and platform tools to PATH
ENV ANDROID_HOME /usr/local/android-sdk
ENV PATH $PATH:$ANDROID_HOME/tools
ENV PATH $PATH:$ANDROID_HOME/platform-tools

# Export JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Install latest android (19 / 4.4.2) tools and system image.
RUN echo "y" | android update sdk --no-ui --force --filter platform-tools,android-22,build-tools-22.0.1,extra-android-support,extra-android-support,extra-android-m2repository

# build
RUN chmod +x gradlew
RUN ./gradlew clean assemble