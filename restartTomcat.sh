#!/bin/sh
#
# Startup Script. for Tomcat
#
# chkconfig: 345 87 13
# description: Tomcat Daemon

# Source function library.
. /etc/rc.d/init.d/functions

# Source networking configuration.
. /etc/sysconfig/network

# Check that networking is up.
[ "$NETWORKING" = "no" ] && exit 0
export JAVA_HOME="/usr/java/latest"
#export JAVA_HOME="/usr/local/java/jdk1.8.0"
export CATALINA_HOME="/var/tomcat/tomcat-7"
export DUSER="root"
export WEB_BASE=$(cd "$(dirname $0)";pwd)
export PROG=$(basename $WEB_BASE)
export CATALINA_BASE="$WEB_BASE/tomcat"
export CATALINA_PID="$CATALINA_BASE/tomcat.pid"
export CATALINA_TMPDIR="$CATALINA_BASE/tmp"
export CATALINA_OUT="$CATALINA_BASE/logs/catalina.out"
export LOCKFILE="$CATALINA_BASE/tomcat.lock"
#export JAVA_OPTS="-Xmx1024m -Xms256m -XX:PermSize=128m -XX:MaxNewSize=256m -XX:MaxPermSize=256m -Xss1m"
export JAVA_OPTS="-Xms4096m -Xmx4096m -Xmn512m -XX:PermSize=256m -XX:MaxPermSize=256m -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8  -XX:+DisableExplicitGC"

#rm -rf $CATALINA_BASE/webapps/*
#rm -rf $CATALINA_BASE/work/*

start() {
        [ -x $CATALINA_HOME/bin/catalina.sh ] || exit 5
        echo -e -e "\e[1;35mStarting $PROG: \e[0;30m"
        daemon --user $DUSER $CATALINA_HOME/bin/catalina.sh jpda start
        retval=$?
        echo
        [ $retval -eq 0 ] && touch $LOCKFILE
        return $retval
}

stop() {
        [ -x $CATALINA_HOME/bin/shutdown.sh ] || exit 5
        #$CATALINA_HOME/bin/shutdown.sh
        ps axuf|grep "$WEB_BASE"|grep -v grep |grep java|awk '{print "kill -9 " $2}'|sh
        echo -e "\e[1;35mStopping $PROG: \t\t\t\t\t[ ok ] \e[0;30m"
        #killproc -p $CATALINA_PID
        su -l $DUSER -s /bin/bash -c  "rm -rf $CATALINA_BASE/{work,webapps}/*"
        retval=$?
        [ $retval -eq 0 ] && rm -f $LOCKFILE
        return $retval
}

restart() {
        stop
        start
}

case "$1" in
        start)
                start
                ;;
        stop)
                stop
                ;;
        restart)
		 restart
                ;;
        *)
                restart
                #echo $"Usage: $0 {start|stop|restart}"
                #exit 2
esac
exit 0
