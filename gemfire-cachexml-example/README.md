Bootstrapping a Spring ApplicationContext in a GemFire Server
=============================================================

This example demonstrates how to launch a GemFire Server from _Gfsh_ configured to bootstrap a Spring
`ApplicationContext` inside the Server's JVM process during initialization.

The approach presented by this example was inspired by a Pivotal customer using *GemFire 7.x* combined with
*Spring Data GemFire 1.3*.

# Background

It is a rather simple matter to launch a Spring-based `ApplicationContext` JVM process bootstrapping GemFire.  This is
actually the preferred way to configure and launch a GemFire Server peer member node in the cluster.  It is as simple
as using a simple Java main class...

```
package example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringGemFireServerLauncher {

  public static void main(final String[] args) {
    new ClassPathXmlApplicationContext(args[0]);
  }
}
```

The user need just pass a Spring `ApplicationContext` XML configuration meta-data file to the `SpringGemFireServerLauncher` class
at runtime...

```
$java -server -cp "<user-application-classpath>" example.SpringGemFireServerLauncher /path/to/spring/application/context.xml
```

Of course, an even better way to bootstrap a GemFire Server in a Spring `ApplicationContext` JVM process
is to use **Spring Boot**, of course...

```
@Configuration
@ImportResource("/path/to/spring/application/context.xml")
@EnableAutoConfiguration
@EnableGemfireFunctions
@EnableGemfireRepositories
@EnableTransactionManagement
public class SampleDataGemFireApplication {

  public static void main(String[] args) {
    SpringApplication.run(SampleDataGemFireApplication.class, args);
  }
}
```

The `/path/to/spring/application/context.xml` file in these to code samples is a *Spring Data GemFire* XML namespace (XSD)
file containing configuration meta-data and Spring beans to configure GemFire.

# Problem

However, for certain customers, this was not allowed.  Their IT operations team required the actual application
deployment to rely on GemFire's provided tool set to manage and monitor GemFire processes.  This meant using _Gfsh_
to configure and launch (i.e. "manage") GemFire Servers.

As of *GemFire 7*, there was not an immediate way to launch a GemFire Server configured with Spring, at least not
until *Spring Data GemFire 1.4*, where `[SpringContextBootstrappingInitializer]`(http://docs.spring.io/spring-data-gemfire/docs/current/api/org/springframework/data/gemfire/support/SpringContextBootstrappingInitializer.html) was introduced.

For more detailed information on using the `SpringContextBootstrappingInitializer`, see the _Spring Data GemFire Reference Guide_...

<a href="http://docs.spring.io/spring-data-gemfire/docs/current/reference/html/#gemfire-bootstrap">Bootstrapping a Spring ApplicationContext in GemFire</a>

Essentially a user only need create a small, minimal snippet of GemFire's `cache.xml` to bootstrap the
Spring `ApplicationContext` in the GemFire Server's JVM process during initialization on start...

```
<?xml version="1.0"?>
<!DOCTYPE cache PUBLIC  "-//GemStone Systems, Inc.//GemFire Declarative Caching 7.0//EN"
  "http://www.gemstone.com/dtd/cache7_0.dtd">
<cache>
  <initializer>
    <class-name>org.springframework.data.gemfire.support.SpringContextBootstrappingInitializer</class-name>
    <parameter name="contextConfigLocations">
      <string>
        classpath:sample/spring/config/spring-gemfire-context-example.xml,
      </string>
    </parameter>
  </initializer>
</cache>
```

Then, from _Gfsh_, the user would configure and launch the GemFire Server like so...

```
gfsh>start server --name=X --log-level=config --disable-default-server --classpath=/path/to/application/classes.jar --cache-xml-location=sample/gemfire/config/cache.xml
```

This enabled IT to administer the (mostly) Spring-configured GemFire Servers using _Gfsh_ and the rest of the
GemFire tool suite.

For more information using GemFire's "_Initializers_" to perform post Cache creation and application initialization
activities, see [GemFire's User Guide](http://gemfire.docs.pivotal.io/latest/userguide/index.html#reference/topics/cache_xml.html#initializer).

# Solution (Example)

The example is very similar to the code snippets above, except the example goes a step further to demonstrate
that GemFire `cache.xml` declared components (e.g. CacheLoaders, CacheListeners) can be configured (auto-wired using DI)
by Spring once the Spring `ApplicationContext` gets initialized by GemFire.

This approach maybe common to existing GemFire users that have a large investment in GemFire's proprietary and native
`cache.xml` format and wish to ease the migration and burden of moving to a more robust configuration approach with
Spring using *Spring Data GemFire*.

# Runtime Requirements

To run the example, you will need...

1. Install JDK 1.6 or 1.7 (preferably 1.7).
2. Set JAVA_HOME to the JDK installation; add $JAVA_HOME/bin to your $PATH.
3. Install GemFire 8.
4. Set $GEMFIRE and $GEMFIRE_HOME to GemFire installed location; add $GEMFIRE_HOME/bin to your $PATH.
5. Launch `gfsh` and follow the recorded session below...

# Session

I illustrate the steps to execute this example below.

In addition, I have also recorded my _Gfsh_ session in the `gemfire-cachexml-example.gfsh` shell script, also contained
in this project module.

In order to run the _Gfsh_ shell script, you will need to replace the token `EFFECTIVE_SPRING_GEMFIRE` with absolute
pathname in which you "cloned" the _effective-spring-gemfire_ project....

```
gfsh>run --file=./gemfire-cachexml-example/gemfire-cachexml-example.gfsh
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

gfsh>start server --name=X --log-level=config --disable-default-server --classpath=/Users/jblum/vmdev/springone2gx-2014/lib/gemfire-cachexml-example-dependencies.jar --cache-xml-file=./gemfire-cachexml-example/target/classes/sample/gemfire/config/cache.xml --J=-Dgemfire.jmx-manager=true --J=-Dgemfire.jmx-manager-start=true --J=-Dgemfire.jmx-manager-port=1199 --J=-Dgemfire.start-locator=localhost[11235] --J=-Dgemfire.locators=localhost[11235]
Starting a GemFire Server in /Users/jblum/vmdev/springone2gx-2014/X...
..........................................
Server in /Users/jblum/vmdev/springone2gx-2014/X on 10.237.177.9 as X is currently online.
Process ID: 53848
Uptime: 22 seconds
GemFire Version: 8.0.0
Java Version: 1.6.0_65
Log File: /Users/jblum/vmdev/springone2gx-2014/X/X.log
JVM Arguments: -Xserver -Dgemfire.log-level=config -Dgemfire.cache-xml-file=/Users/jblum/vmdev/springone2gx-2014/gemfire-cachexml-example/target/classes/sample/gemfire/config/cache.xml -Dgemfire.use-cluster-configuration=true -Dgemfire.jmx-manager=true -Dgemfire.jmx-manager-start=true -Dgemfire.jmx-manager-port=1199 -Dgemfire.start-locator=localhost[11235] -Dgemfire.locators=localhost[11235] -XX:OnOutOfMemoryError="kill -9 %p" -Dgemfire.launcher.registerSignalHandlers=true -Djava.awt.headless=true -Dsun.rmi.dgc.server.gcInterval=9223372036854775806
Class-Path: /Users/jblum/vmdev/springone2gx-2014/lib/gemfire-cachexml-example-dependencies.jar:/Users/jblum/vmdev/workspaces/gemfire-cedar-workspace/Gemfire/build-artifacts/mac/product/lib/server-dependencies.jar

gfsh>connect --locator=localhost[11235]
Connecting to Locator at [host=localhost, port=11235] ..
Connecting to Manager at [host=10.237.177.9, port=1199] ..
Successfully connected to: [host=10.237.177.9, port=1199]

gfsh>list members
Name | Id
---- | -------------------------------
X    | 10.237.177.9(X:53848)<v0>:50681

gfsh>describe member --name=X
Name        : X
Id          : 10.237.177.9(X:53848)<v0>:50681
Host        : 10.237.177.9
Regions     : Example
PID         : 53848
Groups      :
Used Heap   : 29M
Max Heap    : 123M
Working Dir : /Users/jblum/vmdev/springone2gx-2014/X
Log file    : /Users/jblum/vmdev/springone2gx-2014/X/X.log
Locators    : localhost[11235]

gfsh>list regions
List of regions
---------------
Example

gfsh>describe region --name=/Example
.............................................................................
Name            : Example
Data Policy     : partition
Hosting Members : X

Non-Default Attributes Shared By Hosting Members

 Type  |       Name       | Value
------ | ---------------- | ----------------------------------------------
Region | initial-capacity | 101
       | cache-loader     | sample.gemfire.cache.NamedNumbersCacheLoader
       | cache-listeners  | sample.gemfire.cache.util.LoggingCacheListener
       | load-factor      | 0.85
       | size             | 0


```

Notice the `/Example` _Region_ size is **0**.  Now we will use the configured GemFire `CacheLoader`
(_sample.gemfire.cache.NamedNumbersCacheLoader_) to load the `/Example` _Region_ with some values.

The `NamedNumbersCacheLoader` must extend *Spring Data GemFire's* [LazyWiringDeclarableSupport](http://docs.spring.io/spring-data-gemfire/docs/current/api/org/springframework/data/gemfire/LazyWiringDeclarableSupport.html)
class to allow Spring to auto-wire any bean dependencies (e.g. `NamedNumbers` Map bean) the GemFire declared
component may require for correct functioning.

You could imagine that instead of the 'CacheLoader' taking a _Map_ bean dependency, that it might take a Spring
configured _DataSource_ instead to load _Region_ values from a RDBMS.

```
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
.............................................................................
Name            : Example
Data Policy     : partition
Hosting Members : X

Non-Default Attributes Shared By Hosting Members

 Type  |       Name       | Value
------ | ---------------- | ----------------------------------------------
Region | initial-capacity | 101
       | cache-loader     | sample.gemfire.cache.NamedNumbersCacheLoader
       | cache-listeners  | sample.gemfire.cache.util.LoggingCacheListener
       | load-factor      | 0.85
       | size             | 4


```

Now the `/Example` _Region_ size is **4** since we loaded "one", "two", "four" and "eight".  Note, the `NamedNumbersCacheLoader`
does not have a value for "twelve", hence the _Region_ size of **4**.

Onto Function execution...

These GemFire _Functions_ are *Spring Data GemFire* Function annotated POJO methods in the
`sample.spring.function.RegionFunctions` class.

```
gfsh>list functions
Member | Function
------ | ----------
X      | echo
X      | regionSize

gfsh>execute function --id=echo --arguments=Hello
Execution summary

        Member ID/Name          | Function Execution Result
------------------------------- | -------------------------
10.237.177.9(X:63342)<v0>:31579 | You said 'Hello'!

gfsh>execute function --id=regionSize --arguments=/Example
Execution summary

        Member ID/Name          | Function Execution Result
------------------------------- | -------------------------
10.237.177.9(X:63342)<v0>:31579 | 4

```

Finally, we stop the GemFire Server...

```
gfsh>stop server --name=X
Stopping Cache Server running in /Users/jblum/vmdev/springone2gx-2014/X on 10.237.177.9 as X...
Process ID: 63342
Log File: /Users/jblum/vmdev/springone2gx-2014/X/X.log
...
No longer connected to 10.237.177.9[1199].
```

# Summary

Using GemFire too bootstrap the Spring `ApplicationContext` is not desirable.  First of all, GemFire is not
an "application server".

Furthermore, you loose the ability to fully configure the GemFire _Cache_ instance using Spring config when
GemFire bootstraps Spring.  The main reason for this is, a GemFire _Cache_ is a "Singleton" and can only ever be
created once during the lifecycle of the JVM process.  Therefore, *Spring Data GemFire's* `CacheFactoryBean`
must perform a "lookup" for first in order to see if the _Cache_ instance already exists, and since it does,
SDG's `<gfe:cache>` element tag in the XML namespace is effectively limited in its configuration options and ability
to configure the Cache instance.

Remember, the _Cache_ instance already exists because it is constructed, configured (with the `cache.xml`) and
initialized before any `Declarable` objects (e.g. SDG's `SpringContextBootstrappingInitializer`) declared
in the `<initializer>` block of the `cache.xml` file.

See *Spring Data GemFire's Reference Guide* on the [matter](http://docs.spring.io/spring-data-gemfire/docs/current/reference/html/#gemfire-bootstrap)
for more details.

# Next Up...

We explore using GemFire 8's new `--spring-xml-location` command-line option to the `start server` _Gfsh_ command
to enable Spring to bootstrap GemFire with the ability to fully configure the _Cache_ in the `gemfire-springxml-example`
module.
