# TODO

* [x] Add Logger
* [ ] Add a License to all source files
* [x] Metrics export to Prometheus
* [ ] Unload/Unschedule and schedule a task
* [ ] Replace a task implementation
* [ ] Can we sign a jar to prove its authenticity? Project hard-codes a private key; Also can 
* [ ] Scheduling policy to allow tasks to be executed 
    * [ ] Once only
    * [ ] Defined by a cron
    * [ ] Chained to the outcome of a different task
* [ ] Job-specific configuration should be in a resource file. Resource file can be reloaded
* [ ] Implement a mechanism to dynamically adjust the thread pool size based on the number and type of jobs in the queue.                                                                                 
* [ ] Design for Tasks that have external dependencies
* [ ] Control lifecycle of tasks
* [ ] Metrics support to Graphite
* [ ] Makefiles to build the project or to build the defined jobs
* [ ] Rest API to get metrics, and to change the configuration
* [ ] Add a configuration file
* [ ] Add task metadata that stores 
    * Task identifier
    * Last task completion times
    * Task status counts
* [x] Add job class loader to load jobs from a jar file (or a directory), at runtime
* [ ] Rest API to disable/enable a job
* [ ] Rest API to add a job at runtime
* [ ] Rest API to remove a job at runtime
* [ ] Add a job to one-time job to run after a specified interval, and then remove itself
* [ ] Add Postgres support to allow job definitions that use Postgres as a data source
* [ ] Add shell script support to allow job definitions run shell scripts or commands
* [ ] Ability to exist when there is no job to run
* [ ] Testing
  * [ ] Test a task that fails randomly - sometimes succeeding and sometimes failing
  * [ ] Test a task 
* [ ] Add a way to define dependencies between jobs, or chaining tasks, using the output of one in another
* [ ] Defined OK/ERROR return code from tasks
* [ ] Add a way to define a job to run at a specific time, once
* [ ] Add a way to define a job to run at a specific interval
* [ ] Add a way to dynamically change job class at runtime
* [ ] Identity provider with generation numbers to give tasks unique ids
* [ ] Check scheduler thread pool and why there are more threads created than the pool size
