@echo off
set CLASSPATH=conf;lib\cassandra-driver-core-2.0.6.jar;lib\metrics-core-3.0.2.jar;lib\slf4j-log4j12-1.7.7.jar;lib\guava-16.0.1.jar;lib\netty-3.9.0.Final.jar;lib\test-cassandra-injector-1.0-SNAPSHOT.jar;lib\lib;lib\sigar-1.6.4.jar;lib\log4j-1.2.17.jar;lib\slf4j-api-1.7.7.jar
set JAVA_LIBRARY_PATH=lib
java -cp %CLASSPATH% -Djava.library.path=%JAVA_LIBRARY_PATH% com.zenika.test.cassandra.injector.Main