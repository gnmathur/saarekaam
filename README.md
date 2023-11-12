# SaareKaam
Java based task scheduler 

## Introduction
`SaareKaam` (`Saare`=All and `Kaam`=work in Hindi), is task scheduler written in Java. It accepts the tasks to be 
scheduled in the form of a jar file. The aim of the project is to provide a simple, easy to use, and reliable task
scheduler. It _does not_ support a distributed mode. It is ideal for a use case where a user wants to schedule a bunch 
of tasks to run on a single machine. Tasks can range from simple to complex. For example, a task can be to download a
file from the internet, or running a Postgres query, or calling an API etc. Since tasks can run concurrently, and there
is no hard limit on the number of tasks that can be scheduled, the application does not support priority based
scheduling.

### Features
1. Different task scheduling options including one time, recurring, and cron based
2. Tasks are provided to the scheduler as a jar file at startup
3. Identifying long-running tasks and terminating them after a configurable timeout

### Implementation details
#### Task
The following task types are supported:
##### Cancellable task
A cancellable task is a task that can be cancelled by the scheduler. The task is expected to implement the
`CancellableTask` interface. The interface has a single method `cancel()`. The task is expected to implement the
`cancel()` method to gracefully terminate the task. The task is expected to check for the `isCancelled()` method
periodically and terminate itself if the method returns `true`. The `isCancelled()` method is provided by the
scheduler. The scheduler will call the `cancel()` method if the task is running for longer than the configured timeout.

##### Non-cancellable task
A non-cancellable task is a task that cannot be cancelled by the scheduler. The task is expected to implement the
`NonCancellableTask` interface. The interface has a single method `run()`.

Its highly recommended that the tasks implement the `CancellableTask` interface. The scheduler will terminate the
tasks that are running for longer than the configured timeout. The cancellable type offers the task a
handy API to check if the task has been cancelled and terminate itself gracefully. This allows it to be a _better
citizen_ in the scheduler ecosystem.

Note that the scheduler will try to terminate the UnCancellable tasks as well if they are running for longer than the
configured timeout. However, since the task does not implement the `CancellableTask` interface, the task
will receive no notification from the scheduler. The task will be terminated abruptly in case it was sleeping or
waiting for some other task to complete. This can lead to data corruption or other undesirable side effects. Hence, it
is highly recommended that the tasks implement the `CancellableTask` interface and check for the `isCancelled()`
method periodically.

## Running the scheduler
### Prerequisites
1. Java 17
2. Maven 3.8.2

### Building the scheduler and running it
The current repository has a coupled of test tasks. The scheduler can be run using the following command to load those test
tasks:
```bash
mvn package && java -Xmx512M -DlogLevel=INFO -javaagent:ext/lib/jmx_prometheus_javaagent-0.20.0.jar=8899:ext/lib/prometheus_jmx_config.yml -jar target/saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar target/saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

You can also build and run it as a Docker container
```bash
docker build -t <name>:<version> . && docker run -it --rm -p 8899:8899 <name>:<version
```

_Note_: For convenience, both the core scheduler and the test task classes are in the same jar file. In production, the 
tasks will be provided as a separate jar file.

