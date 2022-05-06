@echo off

rem you must first build the application with mvn package

for %%f in (target\*.jar) do java -Xmx32M -Xms32M -server -jar %%f