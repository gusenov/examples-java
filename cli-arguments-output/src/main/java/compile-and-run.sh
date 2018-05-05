#!/bin/bash

clear  # очистка экрана




# Компиляция в .class

rm *.class
#javac -verbose -sourcepath . MyProgram.java
javac -sourcepath . MyProgram.java




# Запуск .class

#java -cp . MyProgram arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9




# Упаковка в .jar

jar cfe my-program.jar MyProgram *.class
#rm *.class
#jar tvf my-program.jar  # просмотр содержимого .jar




# Запуск .jar

java -jar my-program.jar arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9
