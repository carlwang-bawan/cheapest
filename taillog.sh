#!/bin/sh
WEB_BASE=$(cd "$(dirname $0)";pwd)
tail -f ${WEB_BASE}/tomcat/logs/catalina.out
