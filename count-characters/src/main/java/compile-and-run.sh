#!/bin/bash

clear  # очистка экрана




# Компиляция в .class

rm *.class
#javac -verbose -sourcepath . CharacterCounter.java
javac -sourcepath . CharacterCounter.java




# Запуск .class

#java -cp . CharacterCounter




# Упаковка в .jar

jar cfe characterCounter.jar CharacterCounter *.class
#rm *.class
jar tvf characterCounter.jar  # просмотр содержимого .jar




# Запуск .jar

java -jar characterCounter.jar
