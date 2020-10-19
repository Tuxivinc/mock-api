# Mock-api 
Api for test performances, resilience and show functionalities in demo (docker for example, with healthcheck)

## Informations
* sources : https://github.com/Tuxivinc/mock-api

## Fonctions
* Get request information
  * body, headers and URL
* Simulation healthcheck
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
* Mock Json response

## For testing
1/ Deploy container : docker run -d -p 8050:8050 --name mock-api tuxivinc/mock-api

2/ Call swagger for API infos : http://localhost:8050/mock-api/swagger-ui.html

## More information
### Functions list
* F1-Latency of startup:
    * What: add time before service starting Up
    * How: add `-e STARTING_LATENCY_MS=[XXXX]` on command line docker run (XXXX is a time in ms) 
    * Used: Gatling resilience testing, Docker restart policy
* F2-JVM information
    * What: Get Jvm information
    * How: `curl -X GET "http://localhost:8050/mock-api/getinfos/jvm"`
    * Used: Debug
* F3-Environment variables
    * What: Get Environment variables
    * How: `curl -X GET "http://localhost:8050/mock-api/getinfos/varenv"`
    * Used: Debug
* F4-Hostname
    * What: Get Hostname of container
    * How: `curl -X GET "http://localhost:8050/mock-api/getinfos/hostname"`
    * Used: Debug
* F5-Shutdown
    * What: Kill JVM with specify exit Code
    * How: 
      * Run container with --restart on-failure
      * Call `curl -X POST "http://localhost:8050/mock-api/shutdown/12"` -> The container restart
      * Call `curl -X POST "http://localhost:8050/mock-api/shutdown/0"` -> The container don't restart
    * Used: Docker Restart policy, Gatling Resilience test
* F6-HealthCheck
    * What: Set return code of Docker HealthCheck
    * How: 
      * Call `curl -X GET "http://localhost:8050/mock-api/healthcheck"` -> Return healthcheck code (by default 200)
      * Call `curl -X POST "http://localhost:8050/mock-api/healthcheck/setcode/500"` -> Set return code at 500
    * Used: Docker Restart policy, Docker HealthCheck, Gatling Resilience, Traefik expose service
* F7-Sniffer
* F8-Error code
* F9-Response time
* F10-Response time in memory
* F11-Response size
* F12-Session not shared
* F13-Shared Session
* F14-Mock Json Response
    * What: Return Json file store in `/mock-response`
    * How: 
      * Create directory `/xxx/mock`
      * In this directory, create json file, example:
        * test:
            ```
            {
              "username":"John",
              "id":564
            }
            ```
        * test2:
            ```
            {
            "username":"Robert",
            "id":789
            }
            ```
      * Run container with -v /xxx/mock:/mock-response
      * Call `curl -X GET "http://localhost:8050/mock-api/mock/test"` -> Return 200 with test file content
      * Call `curl -X GET "http://localhost:8050/mock-api/mock/test2"` -> Return 200 with test2 file content
      * Call `curl -X GET "http://localhost:8050/mock-api/mock/test3"` -> Return 404, no file found
    * Used: Mock Json call, Gatling Resilience test