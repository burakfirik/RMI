RMI
===

-Remote Method Invocation Client/Server app in Java

README Client
ASSUMPTION
==========
It is assumed that JAVA & TOMCAT Server is installed and configured on OS.

EXECUTABLE FILES
================
Under directory ..\Executables\RMIClient.war

SOURCE FILES
============
Under Directory ..\SourceCode\RMIWebClient

STEPS TO DEPLOY & RUN
=====================
1) Goto home directory where tomcat installed
2) Goto "webapps" folder and paste RMIClient.war/Generated build from given source
3) Start Tomcat
4) Open browser and check at http://localhost:8080/RMIClient
5) UserName: pramod,  Password: password




README Server
NOTE
====
When you'll setup RMIServer at other public IP, you need to configure same IP under RMIClient\WEB-INF\web.xml


ASSUMPTION
==========
It is assumed that JAVA is configured on OS.
Firewall should should have public port entry which mapped to RMI SERVICES port 1099 for external client from other networks.


EXECUTABLE FILES
================
Under directory ..\Executables\RMIServer

SOURCE FILES
============
Under Directory ..\SourceCode\RMIServer

CONTENTS
========
app.properties - application configuration data[ database credentials, server public ip etc...]
start_server.sh - script to run the server
log4j.properties - log4j configuration settings
rmi.policy - security policy file for RMI Server

START SERVER
============
.../RMIServer$ ./start_server.sh
