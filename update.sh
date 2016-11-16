#!/bin/sh

export WEB_BASE=$(pwd | xargs basename)

cd source/$WEB_BASE;

##step-1:更新source；
git pull --rebase

##step-2:mvn打包；
mvn clean install -Denv=beta;

#step-3:替换包，并重启tomcat
cp -f target/$WEB_BASE.war /home/default/$WEB_BASE/webroot/;
cd /home/default/$WEB_BASE;
./restartTomcat.sh;
./taillog.sh
