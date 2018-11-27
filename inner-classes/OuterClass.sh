#!/bin/bash
#clear  # очистка экрана

# Компиляция в .class
rm OuterClass*.class

#javac -verbose -sourcepath . Outer.java
javac -sourcepath . OuterClass.java

# Запуск .class
java -cp . OuterClass

