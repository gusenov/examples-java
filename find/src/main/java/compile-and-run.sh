#!/bin/bash

clear  # очистка экрана




# Компиляция в .class

rm *.class
#javac -verbose -sourcepath . FileSearchCli.java
javac -sourcepath . FileSearchCli.java




# Запуск .class

#java -cp . FileSearchCli




# Упаковка в .jar

jar cfe find.jar FileSearchCli *.class
#rm *.class
#jar tvf find.jar  # просмотр содержимого .jar




# Запуск .jar

java -jar find.jar -r -d /home/abbas/Projects/Public/java README.md
