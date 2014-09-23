GemFire and MySQL Global, JTA-based Transaction Example
=======================================================

This example demonstrates GemFire along with a _MySQL_ database in a global, JTA-backed transactional context.

# Background

There are several ways GemFire and an external RDBMS (e.g. Oracle, MySQL) can interact together and coordinate 
in order to keep both in sync with each other in a consistent and atomic fashion.

One way is to perform a synchronous, ["write-through"]() to a backend data store using a GemFire [CacheWriter](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/run_a_cache_transaction_with_external_db.html).
A synchronous "write-through" will work reliably in the presence of a [Cache Transaction](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/working_with_transactions.html#concept_ysx_nf1_wk)
as well as in a [JTA context](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/cache_plugins_with_jta.html),
with Global Transactions potentially involving multiple data sources.

Another way is to perform a ["write-behind"](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/events/implementing_write_behind_event_handler.html)
that updates the backend data store asynchronously using a GemFire `AsyncEventListener` and `AsyncEventQueue`.

However, sometimes a developer needs flexibility when configuring the application's transaction management strategy.
By using Spring's powerful and robust transaction management infrastructure with the `PlatformTransactionManager`
abstraction, it enables developers to "plugin" an appropriate transaction management strategy (e.g. 'JtaTransactionManager`)
at runtime based on context (e.g. Web Application Server environment).

# GemFire and Global, JTA-based Transactions

GemFire can [play along in a JTA transactional context](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/JTA_transactions_with_GemFire.html#concept_cp1_zx1_wk)
when an external JTA `TransactionManager` is in use.

GemFire even ships with it's own [JTA TransactionManager](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/JTA_transactions_with_GemFire.html#concept_8567sdkbigige)
implementation.  However, it is highly recommended that users rely on a more "robust" JTA implementation for production
purposes.

For one, GemFire's JTA `TransactionManager` is not a complete JTA implementation.

Second of all, GemFire must be used when configuring the external, JTA-aware and XA capable `DataSource`
using GemFire's [JNDI settings](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/configuring_db_connections_using_JNDI.html)
in order to coordinate all transactional resources with the Global JTA Transaction initiated by GemFire's JTA `TransactionManager`
rather appropriately and correctly coordinating transactional resources through the use of a `TransactionSynchronizationRegistry`.

This limitation alone prevents a Spring application developer from configuring an external `DataSource` using Spring config
and then registering the external `DataSource` with GemFire's JTA `TransactionManager`, as desired.

Furthermore, GemFire only allows 1 external transactional resource to be coordinated with GemFire's JTA `TransactionManager`
using the JNDI settings, thus requiring a developer to resort to external means anyway, such as when a JMS Message Broker
is thrown into the mix.

Although, perhaps in simple test cases, GemFire's JTA `TransactionManager` can be convenient.  Still, using an open source
JTA implementation like [JBossTS](http://narayana.jboss.org/), [Atomikos](http://www.atomikos.com/Main/TransactionsEssentials),
[Bitronix](http://docs.codehaus.org/display/BTM/Home) or even [JOTM](http://jotm.objectweb.org/) is highly recommended.

Ideally, an application developer will use the same transaction management strategy throughout development, across all
application environments in order to ensure consistent behavior and proper functioning in a transactional context.

# Quick Note About GemFire Cache Transactions

Note, _Spring Data GemFire_ provides an [implementation of Spring's PlatformTransactionManager](http://docs.spring.io/spring-data-gemfire/docs/current/reference/html/#apis:tx-mgmt)
for use in GemFire's [Cache Transactions](http://gemfire.docs.pivotal.io/latest/userguide/index.html#developing/transactions/cache_transactions.html).

The _Cache_ transaction management strategy can be enabled with the following snippet of Spring config...

```
<gfe:transaction-manager id="transactionManager" copy-on-read="true" />
```

WARNING... GemFire's _Cache_ Transaction Manager **cannot** be used in a JTA transactional context to manage external
transactional resources in addition to the _Cache Regions_ with a Global Transaction.

To see an example of GemFire's _Cache_ Transaction Manager in practice, see the...

[Spring Boot Sample Data GemFire example](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-data-gemfire).


The _Spring Boot_ example is very similar in function to this example.

# Setup

You must use a JTA-compliant external `DataSource` that provides an XA-capable JDBC Driver.  For this example, I have
setup an external MySQL database (`gemfire`) using the following DDL...

```
CREATE DATABASE gemfire;

CREATE TABLE IF NOT EXISTS gemfire.gemstones (
  id NUMERIC(10) PRIMARY KEY,
  stone_name VARCHAR(100) NOT NULL UNIQUE
);
```

Once the MySQL Server is started and database and schema are created, then just run
the `sample.app.SpringGemFireGlobalTransactionTest` test suite class.

The test attempts to save approved as well as "illegal" Gemstones with the GemstoneService.
