#!/bin/bash

CLASSPATH=.:./applicationContext.xml:./logback.xml:./application.properties

for i in ./lib/*.jar ; do
	CLASSPATH=$CLASSPATH:$i
done

export CLASSPATH

#echo $CLASSPATH

java com.xdja.syncThird.Main sync 10


