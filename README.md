vaadin-mockapp
==============

Template for a full-blown Vaadin application that only requires a Servlet 3.0 container to run (no other JEE dependencies).


Project Structure
=================

The project consists of the following three modules:

- parent project: common metadata and configuration
- mockapp-client: widgetset, custom client side code and dependencies to widget add-ons
- mockapp-server: main application module, development time
- mockapp: module that produces a production mode WAR for deployment - no Java code

The production mode module recompiles the widgetset (obfuscated, not draft), activates production mode for Vaadin with a context parameter in web.xml and contains a precompiled theme. The server module WAR contains an unobfuscated widgetset, and is meant to be used at development time only.

Workflow
========

To compile the entire project, run "mvn install" in the parent project.

Other basic workflow steps:

- getting started
- compiling the whole project
  - run "mvn install" in parent project
- developing the application
  - edit code in the server module
  - run "mvn jetty:run" in server module
  - open http://localhost:8080/
- developing the theme
  - run the application as above
  - edit the theme in the server module
  - if using precompiled theme, run "mvn vaadin:compile-theme" in the server module
  - reload the application page
- client side changes or add-ons
  - edit code/POM in client module
  - run "mvn install" in client module
- debugging client side code
  - run "mvn vaadin:run-codeserver" in client module
  - activate Super Dev Mode in the debug window of the application
- creating a production mode war
  - run "mvn package" in the production mode module

