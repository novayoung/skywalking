Changes by Version
==================
Release Notes.

8.3.0
------------------
#### Project
* Test: ElasticSearch version 7.0.0 and 7.9.3 as storage are E2E tested. 
* Test: Bump up testcontainers version to work around the Docker bug on MacOS. 

#### Java Agent
* Support propagate the sending timestamp in MQ plugins to calculate the transfer latency in the async MQ scenarios.
* Support auto-tag with the fixed values propagated in the correlation context.
* Make HttpClient 3.x, 4.x, and HttpAsyncClient 3.x plugins to support collecting HTTP parameters.
* Make the Feign plugin to support Java 14
* Make the okhttp3 plugin to support Java 14
* Polish tracing context related codes.
* Add the plugin for async-http-client 2.x
* Fix NPE in the nutz plugin.
* Provide Apache Commons DBCP 2.x plugin.
* Add the plugin for mssql-jtds 1.x.
* Add the plugin for mssql-jdbc 6.x -> 9.x.
* Fix the default ignore mechanism isn't accurate enough bug.

#### OAP-Backend
* Add the `@SuperDataset` annotation for BrowserErrorLog.
* Add the thread pool to the Kafka fetcher to increase the performance.
* Add `contain` and `not contain` OPS in OAL.
* Add Envoy ALS analyzer based on metadata exchange.
* Support keeping collecting the slowly segments in the sampling mechanism.
* Support choose files to active the meter analyzer.
* Support nested class definition in the Service, ServiceInstance, Endpoint, ServiceRelation, and ServiceInstanceRelation sources.
* Support `sideCar.internalErrorCode` in the Service, ServiceInstance, Endpoint, ServiceRelation, and ServiceInstanceRelation sources.
* Improve Kubernetes service registry for ALS analysis.
* Add health checker for cluster management
* Support the service auto grouping.
* Support query service list by the group name.
* Improve the queryable tags generation. Remove the duplicated tags to reduce the storage payload.
* Fix the threads of the Kafka fetcher exit if some unexpected exceptions happen.
* Fix the excessive timeout period set by the kubernetes-client.
* Fix deadlock problem when using elasticsearch-client-7.0.0.
* Fix storage-jdbc isExists not set dbname.
* Fix `searchService` bug in the InfluxDB storage implementation.
* Fix CVE in the alarm module, when activating the dynamic configuration feature.
* Fix CVE in the endpoint grouping, when activating the dynamic configuration feature.
* Fix CVE in the uninstrumented gateways configs, when activating the dynamic configuration feature.
* Fix CVE in the Apdex threshold configs, when activating the dynamic configuration feature.
* Make the codes and doc consistent in sharding server and core server.
* Fix that chunked string is incorrect while the tag contains colon.
* Fix the incorrect dynamic configuration key bug of `endpoint-name-grouping`.
* Remove unused min date timebucket in jdbc deletehistory logical

#### UI
* Fix incorrect label in radial chart in topology.
* Replace node-sass with dart-sass.

#### Documentation
* Add VNode FAQ doc.
* Add logic endpoint section in the agent setup doc.
* Adjust configuration names and system environment names of the sharing server module

All issues and pull requests are [here](https://github.com/apache/skywalking/milestone/62?closed=1)

------------------
Find change logs of all versions [here](changes).
