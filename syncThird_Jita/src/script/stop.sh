#!/bin/bash

ps -ef | grep 'com.xdja.syncThird.Main'|grep -v 'color' |awk '{print $2}'|xargs kill


