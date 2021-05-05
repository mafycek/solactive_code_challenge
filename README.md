# Solactive Code Challenge

##Introduction
The project demonstrate a simple Spring Boot application that process ticks for various instruments and process results in the memory of computer. On the other hand, it can also show statistics for either each instrument or collective statistics for all instruments.

### Opened end-points
The application expose three endpoints
* (Post) ticks
* (Get) statistics/{id}
* (Get) statistics

The first end-point allows to consume ticks obtained from external source. Later ones provide appropriate statistics for an instrument or all instruments.

### Compilation
The application uses Gradle as a build tool to build and run.

To execute the server on local machine on port 8080, please, use:
`./gradlew bootRun`

To run tests of the application, please, use
`./gradlew test`

I prepared a Python test utility that sends number of ticks to the interface and requests statistics.

### Assumptions of the solution
During execution of the interface I assume only single threaded /statistics end-points.

### Possible improvements
* Implementation of the Bessel correction to the volatility calculation
* Use shared locks instead standard locks to improve performance of the solution
* Calculation of the average of time exponential decay can be calculated using arbitrary precision numbers to prevent under flow or overflow of standard Double type when calculated arguments *ARG* of exponential functions |*ARG*| > 700
* Improvement of the calculation statistics to update only exposed values
* Complex testing of the calculation of statistics
* Encapsulate solution in a Docker image
* Python test utility needs reasonable virtual environment

### Conclusions
The code challenge is really challenge that proves and tests ability to learn new technologies hand in hand with exploiting actual knowledge.  