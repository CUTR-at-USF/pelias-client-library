# pelias-client-library [![Build Status](https://travis-ci.org/CUTR-at-USF/pelias-client-library.svg?branch=master)](https://travis-ci.org/CUTR-at-USF/pelias-client-library) [ ![Download](https://api.bintray.com/packages/cutr-at-usf/cutr-mvn-repo/pelias-client-library/images/download.svg) ](https://bintray.com/cutr-at-usf/cutr-mvn-repo/pelias-client-library/_latestVersion)
A Java library that makes it easy to call the [Mapzen Pelias Search API](https://mapzen.com/documentation/search/search/)

### Requirements

You'll need [JDK 7 or higher](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

### Including this library in your application

To add this library to your project using Maven, add the following to your `pom.xml` file:
~~~
<dependencies>
  <!-- Pelias Client Library -->
  <dependency>
      <groupId>edu.usf.cutr.pelias</groupId>    
      <artifactId>pelias-client-library</artifactId>    
      <version>1.0.0</version>
      <type>pom</type>
  </dependency>
</dependencies>
~~~

If you're using Gradle and Android Studio, here's what your `build.gradle` should look like:

~~~
...
repositories {
    jcenter()
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
    compile 'edu.usf.cutr.pelias:pelias-client-library:1.0.0'
}
~~~


### Usage

The below example shows how to call the Pelias Search API.

~~~
String apiKey = "YourKeyHere";
String text = "London";
SearchResponse response = new SearchRequest.Builder(apiKey, text).build().call();
System.out.println(response.toString());
~~~

## Compiling the code yourself

### Setting up your environment

This project was created in [IntelliJ](https://www.jetbrains.com/idea/).  You can also compile it from the command line using [Maven](https://maven.apache.org/).

### Getting the code

To get started with this project, use a Git client to clone this repository to your local computer.  Then, in IntelliJ import the project as a Maven project.

### Dependencies

Managed via Maven:

* [**Jackson JSON and XML Processor project**](http://wiki.fasterxml.com/JacksonHome) - For parsing JSON.
* [**GeoJson POJOs for Jackson**](https://github.com/opendatalab-de/geojson-jackson) - Plain-Old-Java-Objects (POJOs) used for Jackson data binding of GeoJSON elements to Java objects.

### Build the project

* IntelliJ - Clean and build the project
* Maven - `mvn install` 

### CUTR Release Process

**Snapshots**

We've set up a Maven repository to hold the snapshot artifacts from this project in a Github project - [cutr-mvn-repo](https://github.com/CUTR-at-USF/cutr-mvn-repo).

At CUTR, we should run the following at the command-line to create a new artifact:
~~~
mvn -DaltDeploymentRepository=cutr-snapshots::default::file:"/Git Projects/cutr-mvn-repo/snapshots" clean deploy
~~~

Then commit using Git and push new artifacts to Github.

If you want to include snapshot releases in your project, you'll need to add the following to the `pom.xml` of the project you want to use it in:

~~~
<!-- CUTR SNAPSHOTs/RELEASES -->
<repositories>
    <repository>
        <id>cutr-snapshots</id>
        <url>https://raw.githubusercontent.com/CUTR-at-USF/cutr-mvn-repo/master/snapshots</url>
    </repository>        
</repositories>
~~~

**Releases**

We're hosting releases on JCenter via [Bintray](https://bintray.com/).

These are the steps for publishing the `pelias-client-library` on `jcenter` repository.

###### 1 - Create an account on [Bintray](https://bintray.com/).
###### 2 - Setup your pom.xml

We need to specify the URL from which to distribute your project. 
```
    <distributionManagement>
        <repository>
            <id>jCenter</id>
            <url>https://api.bintray.com/maven/cutr-at-usf/cutr-mvn-repo/pelias-client-library/;publish=1</url>
        </repository>
    </distributionManagement>
```

###### 3 - Setup your setting.xml
We need to provide Bintray username and API Key to the Maven `settings.xml` file.  This may be under your Maven installation directory (e.g., `C:\Program Files\Apache Software Foundation\apache-maven-3.2.5\conf`).

```
<server>
  <id>jCenter</id>
  <username>yourjcenteraccount</username>
  <password>***youraccount-secret-api-key***</password>
</server>
```

To sign the application (required for distribution to Maven Central), you need to add the following block as well (after [importing the CUTR key](http://central.sonatype.org/pages/working-with-pgp-signatures.html)):

```
<profile>
    <id>ossrh</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
        <gpg.executable>gpg2</gpg.executable>
        <gpg.passphrase>the_pass_phrase</gpg.passphrase>
    </properties>
</profile>
```

###### 4 - Run maven deploy

Finally, we can run ```mvn deploy``` to complete publishing.
