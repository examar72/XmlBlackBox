Building all projects (You needs Maven and Ant)

To build all projects you can run this command (no tests run):

mvn clean install -DskipTests=true

To run with tests:

mvn clean install

To create the release zip file 

ant jarDistribution -Dft.version=<version>

This last command creates a zip file of template project 
with the last xmlblackbox library with the version specified




