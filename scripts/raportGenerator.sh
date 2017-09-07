#!/bin/bash

# Preparing demo

echo "Preparing demo!!"

cd
git clone https://github.com/Ice94/robot.git
cd robot
mvn clean install
mvn site
mvn site:stage
cd target
cd staging

echo "Update Hibernate Properties."
xdg-open index.html &
