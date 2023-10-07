# TODO

* [x] Add Logger
* [ ] Add a License to all source files
* [ ] Metrics export to Prometheus
* [ ] Metrics support to Graphite
* [ ] Makefiles to build the project or to build the defined jobs
* [ ] Rest API to get metrics, and to change the configuration
* [ ] Add a configuration file
* [x] Add job class loader to load jobs from a jar file (or a directory), at runtime
* [ ] Rest API to disable/enable a job
* [ ] Rest API to add a job at runtime
* [ ] Rest API to remove a job at runtime
* [ ] Add a job to one-time job to run after a specified interval, and then remove itself
* [ ] Add Postgres support to allow job definitions that use Postgres as a data source
* [ ] Add shell script support to allow job definitions run shell scripts or commands
* [ ] Ability to exist when there is no job to run
* [ ] Testing support
  * [ ] Unit tests
    * [ ] Job class loader
    * [ ] Job scheduler
  * [ ] Integration tests
  * [ ] Functional tests
  * [ ] Performance tests
* [ ] Add a way to define dependencies between jobs
* [ ] Add a way to define a job to run at a specific time, once
* [ ] Add a way to define a job to run at a specific interval
* [ ] Add a way to dynamically change job class at runtime
* [ ] Identity provider with generation numbers to give tasks unique ids
