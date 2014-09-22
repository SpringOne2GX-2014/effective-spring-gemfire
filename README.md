Effective Application Development with GemFire using Spring Data GemFire
========================================================================

This session focused on getting developers up-to-speed and productive with Pivotal GemFire and Spring Data GemFire.
The content is appropriate for new and experienced GemFire users alike and will demonstrate the value of using both
technologies together to develop low-latency, high-throughput and scalable applications quickly and effectively.
We briefly covered some of the core concepts in GemFire and then took a look at a few of the new features and tools
in GemFire 8. Next, we dove into Spring Data GemFire's simple, yet powerful programming model and rich feature set
specific to Spring Data GemFire for enhancing the developers experience when working with GemFire.

Finally, we reviewed some real-world use cases using actual customer problems so you can avoid common pitfalls
just by following a few recommended practices. By sessions end, developers new to GemFire were able to gain an
understanding for what the technology is and does along with how to simplify their getting-started experience
by using Spring Data GemFire. More experienced GemFire users took away some additional insight and recommended
practices for being more productive and effective when working with GemFire.

The [Effective Application Development with GemFire using Spring Data GemFire](https://2014.event.springone2gx.com/schedule/sessions/effective_application_development_with_gemfire_and_spring_data_gemfire.html) presentation was presented
at SpringOne2GX 2014 in Dallas, TX on Wednesday, September 10 at 4:30 pm CST by John Blum and Luke Shannon.

# About

The *effective-spring-gemfire* project consists of 4 modules.

* _spring-gemfire-core_

This module contains source code and resources common to the other project modules.

* _gemfire-cachexml-example_

This module demonstrates the **GemFire 7.x** and **Spring Data GemFire 1.4.x** method of launching a GemFire Server
JVM process from _Gfsh_ bootstrapping a Spring ApplicationContext using a small snippet of GemFire `cache.xml`.
The example also illustrates how Spring can be used to wire GemFire components declared in the `cache.xml` file.

* _gemfire-springxml-example_

This module demonstrates the **new**, preferred *GemFire 8* method of using Spring to bootstrap a GemFire Server
in the Spring ApplicationContext's JVM process using _Gfsh's_ new `--spring-xml-location` option to the `start server`
command.

* _gemfire-mysql-example_

This module showcases a customer case study that uses *GemFire* together with a *MySQL* database in a global (JTA-based)
transactional context.

Click the link of each module to learn more.

# Runtime Requirements

1. Install JDK 1.6 or 1.7 (preferably 1.7)
2. Set JAVA_HOME to the JDK installation; include $JAVA_HOME/bin in your $PATH if necessary.
3. Install [GemFire 8](https://network.pivotal.io/products/pivotal-gemfire).
4. Set $GEMFIRE to the *GemFire 8* installation directory; include $GEMFIRE/bin in your $PATH.

To build as well...

# Build Requirements

5. Install Maven 3.2.x. (to build the project
6. Install Ant 1.9.x.

# Build

To build the `effective-spring-gemfire` project run...

1. ant clean-all
2. mvn clean install
3. ant build-all

# Conclusion

For those who attended our session, thank you.  We are open to and appreciate any honest feedback on how we can
improve and make this type of session even more valuable next time.  Feel free to reach out to us; contact information
is the POM file.  Thanks again.  See you next year!
