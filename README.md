Effective Application Development with GemFire using Spring Data GemFire
========================================================================

This session focused on getting developers up-to-speed and productive with **Pivotal GemFire**
by using **Spring Data GemFire**. The content is appropriate for new and experienced GemFire users alike
and demonstrated the value of using both technologies together to develop high-throughput, low-latency
and scalable applications efficiently and effectively.

We briefly covered some of the core concepts in GemFire and then took a look at a few of the new features and tools
in **GemFire 8**. Next, we dove into _Spring Data GemFire's_ simple, yet powerful programming model and rich feature set
specific to _Spring Data GemFire_ for enhancing the developers experience when working with GemFire.

Finally, we reviewed some real-world case studies using actual customer use cases and problems so you can avoid
common pitfalls just by following a few recommended practices. By sessions end, developers new to GemFire were able to
gain an understanding for what GemFire is and does along with how to simplify their getting-started experience
by using _Spring Data GemFire_. More experienced GemFire users took away some additional insight and recommended
practices for being more productive and effective when working with GemFire.

The [Effective Application Development with GemFire using Spring Data GemFire](https://2014.event.springone2gx.com/schedule/sessions/effective_application_development_with_gemfire_and_spring_data_gemfire.html) session was presented
at [SpringOne2GX 2014](http://www.springone2gx.com/) in _Dallas, TX_ on _Wednesday, September 10_ at _4:30 pm CST_ by _John Blum_ and _Luke Shannon_.

# About

The *effective-spring-gemfire* project consists of 4 modules...

* _spring-gemfire-core_

This module contains source code and resources common to the other project modules.

* _gemfire-cachexml-example_

This module demonstrates the **GemFire 7.x** and **Spring Data GemFire 1.4.x** method of launching a GemFire Server
JVM process from _Gfsh_ bootstrapping a Spring `ApplicationContext` using a small snippet of GemFire `cache.xml`.
The example also illustrates how Spring can be used to auto-wire GemFire components using _Dependency Injection (DI)_
declared in the `cache.xml` file.

* _gemfire-springxml-example_

This module demonstrates the **new** and preferred *GemFire 8* method of using Spring to bootstrap a GemFire Server
in a Spring `ApplicationContext's` JVM process using _Gfsh's_ new `--spring-xml-location` option to the `start server`
command.

* _gemfire-mysql-example_

This module showcases a customer case study that uses *GemFire* together with a *MySQL* database in a global, JTA-based
transactional context.

Click the link of each module above to learn more.

# Runtime Requirements

1. Install [JDK 1.7](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. Set `JAVA_HOME` to the JDK 7 installation directory; include $JAVA_HOME/bin in your $PATH if necessary.
3. Install [GemFire 8](https://network.pivotal.io/products/pivotal-gemfire).
4. Set `GEMFIRE` to the *GemFire 8* installation directory; include $GEMFIRE/bin in your $PATH.

To build the project as well...

# Build Requirements

5. Install [Maven 3.2.x](http://maven.apache.org/download.cgi).
6. Install [Ant 1.9.x](http://ant.apache.org/bindownload.cgi).

# Build

To build the `effective-spring-gemfire` project run...

```
$ant clean-all
...

$mvn clean install
...

$ant build-all
...
```

# Conclusion

For those SpringOne attendees who attended our session, thank you!  We are open to and appreciate any honest feedback
on how we can improve and make this type of session even more valuable next time.  Feel free to reach out to us;
contact information is the POM file.

Thanks again.  See you next year!
