Bootstrapping GemFire with a Spring ApplicationContext using Gfsh
=================================================================

This example demonstrates the preferred way to start a GemFire Server given this approach affords a
Spring application developer the ability to fully configure their GemFire Server with Spring configuration
meta-data, which is far superior to GemFire's native `cache.xml` format.

Not only is Spring configuration meta-data more robust, it immediately enables a Spring application developer
with the full power of **Spring**, giving the application developer a consistent and powerful programming model
along with the ability to integrate with any of the other Spring Platform ecosystem technologies (e.g. *Spring Boot*,
*Spring Security*, *Spring Data*, *Spring Integration*, *Spring Batch*, *Spring XD*, *Reactor* and so on).

This example is far simpler than the previous example illustrated in the `gemfire-cachexml-example` project module.

# Runtime Requirements

To run the example, you will need...

1. Install [JDK 1.7](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. Set `JAVA_HOME` to the JDK 7 installation directory; include $JAVA_HOME/bin in your $PATH if necessary.
3. Install [GemFire 8](https://network.pivotal.io/products/pivotal-gemfire).
4. Set `GEMFIRE` to the *GemFire 8* installation directory; include $GEMFIRE/bin in your $PATH.
5. Launch `gfsh` and follow the recorded session below...

# Session

I illustrate the steps to execute this example below.

In addition, I have also recorded my _Gfsh_ session in the `gemfire-springxml-example.gfsh` shell script, also contained
in this project module.

In order to run the _Gfsh_ shell script, you will need to replace the token `EFFECTIVE_SPRING_GEMFIRE` with absolute
pathname in which you "cloned" the _effective-spring-gemfire_ project to you local machine.  Execute the _Gfsh_
shell script like so...

```
gfsh>run --file=./gemfire-springxml-example/gemfire-springxml-example.gfsh
```

The session played out like this...

```
$ gfsh
    _________________________     __
   / _____/ ______/ ______/ /____/ /
  / /  __/ /___  /_____  / _____  /
 / /__/ / ____/  _____/ / /    / /
/______/_/      /______/_/    /_/    v8.0.0

Monitor and Manage GemFire

gfsh>start server --name=X --disable-default-server --classpath=/Users/jblum/vmdev/springone2gx-2014/lib/gemfire-springxml-example-dependencies.jar --spring-xml-location=sample/spring/config/spring-gemfire-context-example.xml
Starting a GemFire Server in /Users/jblum/vmdev/springone2gx-2014/X...
...............................
Server in /Users/jblum/vmdev/springone2gx-2014/X on 10.237.177.9 as X is currently online.
Process ID: 70506
Uptime: 16 seconds
GemFire Version: 8.0.0
Java Version: 1.6.0_65
Log File: /Users/jblum/vmdev/springone2gx-2014/X/X.log
JVM Arguments: -Xserver -Dgemfire.use-cluster-configuration=true -XX:OnOutOfMemoryError="kill -9 %p" -Dgemfire.launcher.registerSignalHandlers=true -Djava.awt.headless=true -Dsun.rmi.dgc.server.gcInterval=9223372036854775806
Class-Path: /Users/jblum/vmdev/springone2gx-2014/lib/gemfire-springxml-example-dependencies.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/server-dependencies.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-aop-3.2.9.RELEASE.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-beans-3.2.9.RELEASE.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-context-3.2.9.RELEASE.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-context-support-3.2.9.RELEASE.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-expression-3.2.9.RELEASE.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-tx-3.2.9.RELEASE.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/spring-data-gemfire-1.4.0.RELEASE.jar

gfsh>connect --locator=localhost[11235]
Connecting to Locator at [host=localhost, port=11235] ..
Connecting to Manager at [host=10.237.177.9, port=1199] ..
Successfully connected to: [host=10.237.177.9, port=1199]

gfsh>list members
Name | Id
---- | -------------------------------
X    | 10.237.177.9(X:70506)<v0>:40682

gfsh>describe member --name=X
Name        : X
Id          : 10.237.177.9(X:70506)<v0>:40682
Host        : 10.237.177.9
Regions     : Example
PID         : 70506
Groups      :
Used Heap   : 18M
Max Heap    : 123M
Working Dir : /Users/jblum/vmdev/springone2gx-2014/X
Log file    : /Users/jblum/vmdev/springone2gx-2014/X/X.log
Locators    : localhost[11235]

gfsh>list regions
List of regions
---------------
Example

gfsh>describe region --name=/Example
..............................................................................
Name            : Example
Data Policy     : partition
Hosting Members : X

Non-Default Attributes Shared By Hosting Members

  Type    |          Name          | Value
--------- | ---------------------- | ----------------------------------------------
Region    | cache-loader           | sample.gemfire.cache.NamedNumbersCacheLoader
          | cloning-enabled        | true
          | cache-listeners        | sample.gemfire.cache.util.LoggingCacheListener
          | size                   | 0
Partition | startup-recovery-delay | -1

gfsh>get --region=/Example --key=one
Result      : true
Key Class   : java.lang.String
Key         : one
Value Class : java.lang.Integer
Value       : 1

gfsh>get --region=/Example --key=two
Result      : true
Key Class   : java.lang.String
Key         : two
Value Class : java.lang.Integer
Value       : 2

gfsh>get --region=/Example --key=four
Result      : true
Key Class   : java.lang.String
Key         : four
Value Class : java.lang.Integer
Value       : 4

gfsh>get --region=/Example --key=eight
Result      : true
Key Class   : java.lang.String
Key         : eight
Value Class : java.lang.Integer
Value       : 8

gfsh>get --region=/Example --key=twelve
Result      : false
Key Class   : java.lang.String
Key         : twelve
Value Class : java.lang.String
Value       : <NULL>

gfsh>describe region --name=/Example
..............................................................................
Name            : Example
Data Policy     : partition
Hosting Members : X

Non-Default Attributes Shared By Hosting Members

  Type    |          Name          | Value
--------- | ---------------------- | ----------------------------------------------
Region    | cache-loader           | sample.gemfire.cache.NamedNumbersCacheLoader
          | cloning-enabled        | true
          | cache-listeners        | sample.gemfire.cache.util.LoggingCacheListener
          | size                   | 4
Partition | startup-recovery-delay | -1

gfsh>list functions
Member | Function
------ | ----------
X      | echo
X      | regionSize

gfsh>execute function --id=echo --arguments=Hello
Execution summary

        Member ID/Name          | Function Execution Result
------------------------------- | -------------------------
10.237.177.9(X:70506)<v0>:40682 | You said 'Hello'!

gfsh>execute function --id=regionSize --arguments=/Example
Execution summary

        Member ID/Name          | Function Execution Result
------------------------------- | -------------------------
10.237.177.9(X:70506)<v0>:40682 | 4

gfsh>list members
Name | Id
---- | -------------------------------
X    | 10.237.177.9(X:70506)<v0>:40682

gfsh>stop server --name=X
Stopping Cache Server running in /Users/jblum/vmdev/springone2gx-2014/X on 10.237.177.9 as X...
Process ID: 70506
Log File: /Users/jblum/vmdev/springone2gx-2014/X/X.log
..
No longer connected to 10.237.177.9[1199].

```

The `--spring-xml-location` command-line option of the _Gfsh_ `start server` command automatically looks for
a Spring configuration file on the CLASSPATH by default.  However, a user is permitted to use any of Spring's
Resource prefix qualifiers (e.g. `file:`) to specify the location of their Spring config.

The advantage of this approach is that you are not required to provide a small, minimal snippet of GemFire `cache.xml`
to bootstrap the GemFire process.  In this case, _Gfsh_ starts a Spring `ApplicationContext`-based JVM process that
bootstraps GemFire configured with Spring, and specifically with *Spring Data Gemfire's* XML namespace based config.

Finally, this approach also simplifies the CLASSPATH and the Spring JARs that are required to launch
the Spring configured GemFire Server.

Whereas the previous approach described in the `gemfire-cachexml-example` module requires you to include all the
necessary core Spring JAR files in addition to your application JAR file(s), this approach includes a minimal set
of Spring JARs on the CLASSPATH by default when using the `--spring-xml-location` command-line option.

Note, however, you still need to include your application classes (e.g. `NamedNumbersCacheLoader`) on the CLASSPATH.

# Dependency JAR Files

You may have noticed that these examples used a Manifest-only JAR file to specify an application's dependencies.

A Manifest-only JAR file is a JAR file that  only contains a META-INF/MANIFEST.MF file with
a `Class-Path` Manifest Header indicating the JAR files necessary to run the application.

These Manifest-only, dependency-based JAR files are the...

`gemfire-cachexml-example-dependencies.jar`
`gemfire-springxml-example-dependencies.jar`

... files respectively.  If you crack one open, e.g. ...

```
$jar -xvf lib/gemfire-cachexml-example-dependencies.jar META-INF/MANIFEST.MF

$less META-INF/MANIFEST.MF
```

... and compare their contents, namely the `Class-Path` Manifest header, you will notice the difference.

The `Class-Path` Manifest header references JAR files in the `~/effective-spring-gemfire/lib` relative to
the Manifest-only, dependency-based JAR files (i.e. the `./lib` directory).
