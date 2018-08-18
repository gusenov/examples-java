set jdk_dir=C:\Program Files\Java\jdk-10.0.1\bin
set output_file=bus-station-ticket-issuing-app

del /s /q /f *.class
del /s /q /f *.jar

"%jdk_dir%/javac" -encoding utf8 --source-path . Main.java
"%jdk_dir%/jar" --create --file=%output_file%.jar --main-class=Main .
"%jdk_dir%/java" -jar %output_file%.jar

pause
