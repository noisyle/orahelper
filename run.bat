@echo off
set JAVA_HOME=D:\java\jdk1.7.0_67
set path=%JAVA_HOME%\bin;%path%
set classpath=.;%JAVA_HOME%\lib
java -jar bin/run.jar
pause
