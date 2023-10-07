# SaareKaam
Java based job scheduler 

## Introduction
`SaareKaam` (`Saare`=All and `Kaam`=work in Hindi), is Job scheduler written in Java. It is a simple job scheduler
which can be used to schedule jobs to run at a specific time. The jobs are provided to the scheduler as a jar file.

### Features
1. Job can be scheduled to run at a specific time
2. Jobs are provided to the scheduler as a jar file
3. Jobs are defined as a class implementing the Job interface, which has a single method execute which is called by the scheduler
4. The job scheduler will impose a hard limit on the time a job can run for. If a job is not complete within the time 
limit, it will be terminated
5. The framework will provide a way to define dependencies between jobs. A job can be dependent on one or more jobs
6. The framework will export metrics about the jobs that are running and have completed. The metrics will include the 
time taken by the job to complete, the status of the job (success/failure), etc.
7. The framework will treat all the jobs at the same priority level. It will not provide a way to prioritize one job over another

## Running the scheduler
### Prerequisites
1. Java 17
2. Maven 3.8.2

### Building the scheduler and running it
The current repo has a coupled of test tasks. The scheduler can be run using the following command to load those test
tasks:
```bash
mvn package && java -cp target/sarekaam-1.0-SNAPSHOT.jar com.gnmathur.saarekaam.Main
```

_Note_: For convenience, both the core scheduler and the test task classes are in the same jar file. In production, the 
tasks will be provided as a separate jar file.

