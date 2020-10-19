# Mock-api 
Api for test performances, resilience and show functionalities in demo (docker for example, with healthcheck)

## Informations
* sources : https://github.com/Tuxivinc/mock-api

## Fonctions
* Get request information
  * body, headers and URL
* Simul healthcheck
  * get http code
  * set http code return
* Sleep time response
  * Storage ni memory or pass in request
* Size response
* Return Http status specify
* Shutdown application with error code
* Get JVM args
* Get environment variables
* Get Hostname
* Latency on startup
* Demo session sharing (for load balancing example)
  * Store session local
  * Multicast session

## For testing
1/ Deploy container : docker run -d -p 8050:8050 --name mock-api tuxivinc/mock-api

2/ Call swagger for API infos : http://localhost:8050/mock-api/swagger-ui.html

## More information
### Functions list
* F1-Latency of startup:
    * What: add time before service starting Up
    * Apply: add `-e STARTING_LATENCY_MS=[XXXX]` on command line docker run (XXXX is a time in ms) 
    * Used: Gatling resilence testing, Docker restart policy
* F2-JVM information
    * What: Get Jvm information
    * Apply: `curl -X GET "http://localhost:8050/mock-api/getinfos/jvm"`
    * Used: Debug
* F3-Environment variables
    * What: Get Environment variables
    * Apply: `curl -X GET "http://localhost:8050/mock-api/getinfos/varenv"`
    * Used: Debug
* F4-Hostname
    * What: Get Hostname of container
    * Apply: `curl -X GET "http://localhost:8050/mock-api/getinfos/hostname"`
    * Used: Debug
* F5-Shutdown
    * What: Kill JVM with specify exit Code
    * Apply: 
      * Run container with --restart on-failure
      * Call `curl -X POST "http://localhost:8050/mock-api/shutdown/12"` -> The container restart
      * Call `curl -X POST "http://localhost:8050/mock-api/shutdown/0"` -> The container don't restart
    * Used: Docker Restart policy, Gatling Resilence test

 