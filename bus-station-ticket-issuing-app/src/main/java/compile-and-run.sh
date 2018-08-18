#!/bin/bash


output_dir="classfiles"
output_file="bus-station-ticket"


clear  # очистка экрана


# Компиляция в .class

rm $output_dir/*.class
#javac -verbose -d "$output_dir" --source-path . Main.java
javac -d "$output_dir" --source-path . Main.java


# Запуск .class

#java --class-path "$output_dir" Main


# Упаковка в .jar

rm $output_file.jar
jar --create --file=$output_file.jar --main-class=Main -C $output_dir .
rm $output_dir/*.class
#jar --list --verbose --file=$output_file.jar  # просмотр содержимого .jar


# Запуск .jar

java -jar $output_file.jar

