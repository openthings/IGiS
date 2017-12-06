#!/bin/sh

if git diff build.sbt | wc -l | grep -v 0 > /dev/null; then exit 1; fi

wget http://central.maven.org/maven2/com/typesafe/sbt/sbt-launcher/0.13.6/sbt-launcher-0.13.6.jar -O .sbt-launcher.jar

echo '.disablePlugins(SbtLess)' >> build.sbt
java -jar .sbt-launcher.jar npmUpdate
git checkout -- build.sbt
