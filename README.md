# pelias-client-library [![Build Status](https://travis-ci.org/CUTR-at-USF/pelias-client-library.svg?branch=master)](https://travis-ci.org/CUTR-at-USF/pelias-client-library)
A Java library that makes it easy to call the [Mapzen Pelias Search API](https://mapzen.com/documentation/search/search/)

### Requirements

You'll need [JDK 7 or higher](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

### Including this library in your application

To add this library to your project using Maven, add the following to your `pom.xml` file:
~~~
<dependencies>
  <!-- Pelias Client Library -->
  <dependency>
      <groupId>edu.cutr.pelias</groupId>    
      <artifactId>pelias-client-library</artifactId>    
      <version>1.0.0-SNAPSHOT</version>
  </dependency>
</dependencies>

<!-- CUTR SNAPSHOTs/RELEASES -->
<repositories>
    <repository>
        <id>cutr-snapshots</id>
        <url>https://raw.githubusercontent.com/CUTR-at-USF/cutr-mvn-repo/master/snapshots</url>
    </repository>        
    <repository>
        <id>cutr-releases</id>
        <url>https://raw.githubusercontent.com/CUTR-at-USF/cutr-mvn-repo/master/releases</url>
    </repository>  
</repositories>
~~~

If you're using Gradle and Android Studio, here's what your `build.gradle` should look like:

~~~
...
repositories {
    mavenCentral()
    maven {
        // CUTR SNAPSHOTs
        url "https://github.com/CUTR-at-USF/cutr-mvn-repo/raw/master/snapshots"
    }
    maven {
        // CUTR Releases
        url "https://github.com/CUTR-at-USF/cutr-mvn-repo/raw/master/releases"
    }
}

android {
    ...

    // http://stackoverflow.com/questions/20673625/gradle-0-7-0-duplicate-files-during-packaging-of-apk
    packagingOptions {
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/services/com.fasterxml.jackson.core.JsonFactory'
        exclude 'META-INF/services/com.fasterxml.jackson.core.ObjectCodec'
    }
...

dependencies {
    ...
    // Pelias Client library
    compile 'edu.usf.cutr.pelias:pelias-client-library:1.0.0-SNAPSHOT'
}
~~~


### Usage

The below example shows how to call the Pelias Search API.

~~~
String apiKey = "YourKeyHere";
String text = "London";
PeliasResponse response = new PeliasRequest.Builder(apiKey, text).build().call();
System.out.println(response.toString());
~~~

## Compiling the code yourself

### Setting up your environment

This project was created in [IntelliJ](https://www.jetbrains.com/idea/).  You can also compile it from the command line using [Maven](https://maven.apache.org/).

### Getting the code

To get started with this project, use a Git client to clone this repository to your local computer.  Then, in IntelliJ import the project as a Maven project.

### Dependencies

* [**Jackson JSON and XML Processor project**](http://wiki.fasterxml.com/JacksonHome) - For parsing JSON.
* [**GeoJson POJOs for Jackson**](https://github.com/opendatalab-de/geojson-jackson) - Plain-Old-Java-Objects (POJOs) used for Jackson data binding of GeoJSON elements to Java objects.

### Build the project

* IntelliJ - Clean and build the project
* Maven - `mvn install` 

### CUTR Release Process

We've set up a Maven repository to hold the artifacts from this project in a Github project - [cutr-mvn-repo](https://github.com/CUTR-at-USF/cutr-mvn-repo).

At CUTR, we should run the following at the command-line to create a new artifact:
~~~
mvn -DaltDeploymentRepository=cutr-snapshots::default::file:"/Git Projects/cutr-mvn-repo/snapshots" clean deploy
~~~

Then commit using Git and push new artifacts to Github.

Note that the "snapshots" in the command-line should be replaced with "releases" for release versions.
