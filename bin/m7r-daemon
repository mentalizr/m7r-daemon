#!/bin/bash

__dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

file=$(cd ${__dir}/../build/libs && ls -1 m7r-daemon-*.jar | head -n 1)

java -cp "${__dir}/../build/libs/${file}:${__dir}/../build/dependencies/*" org.mentalizr.daemon.Daemon "$@" & > /dev/null 2>&1
