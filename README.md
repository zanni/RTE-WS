RTE-WS
=====

RTE-WS is java/j2ee project intended to grab, store and distribute [rte eco2mix] [1] data
[1]:http://www.rte-france.com/fr/developpement-durable/eco2mix

The project is based on Spring roo framework, different modules are implemented using Maven multi-module project.

rte-framework
==

Service oriented framework which map data into MongoDB database. Data retreiving services are based onto MapReduce jobs in order to gain performance with time based aggregation and field selection. It also define classical CRUD service to easily retreive and inject data.

rte-grabber
==

Generic command line data grabber. Usage: 
```javascript
{rte-ws_dir}/bin/grabber {start} {end}
```
whith start/end temporal bounds of data to grab and inject, following format yyyy-MM-dd

rte-daily-grabber
==

Command-line grabber intended to grab today's data. If executed every quarters, data are grab in real-time. Usage:
```javascript
{rte-ws_dir}/bin/grabber {start} {end}
```

Both grabbers needs definition of following environnement variables (obviously related to your database connection):

- $MONGO_DBNAME
- $MONGO_USERNAME
- $MONGO_PASS
- $MONGO_HOST
- $MONGO_PORT


rte-app
==

Spring based j2ee web project which provide JSON/JSONP data mapping with time based aggregation and field selection (considering application is deployed on localhost:8080 ) :

```javascript
//JSON url
http://localhost:8080/mixenergys/{agg}/{fields}/between/{start}/{end}

//JSONP url
http://localhost:8080/mixenergys/{agg}/{fields}/between/jsonp/{start}/{end}
```
 
 - {agg} : Desired time aggregation period (quarter | hour | day | week | month | year)
 - {fields} : Desired fields separated with coma (see RteFieldAggregateEnum rte-framework enum)
 - {start}/{end} : temporal bounds, following format yyyy-MM-dd

Spring Roo + heroku = magic
=====

Project is easily deployable to [Heroku] [2] plateform: a jetty runnable server is embbeded with few maven configuration and backend storage is provided by MongoHQ addon. Monitoring / Logging / IO tests can be provided by following addon : New Relic / Loggy mole / loader.io tests.
[2]:[https://www.heroku.com/]

At the end, their was not as much data as I imagine in rte eco2mix, but good point is all application just cost you the price of a dyno, for a 100 concurrent connections ready application.


