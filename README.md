# Mock-api 
Api for Mock, performances testing, resilience and show functionalities in demo (docker for example, with healthcheck)

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
    * What: Get information of call (GET/PUT/POST/DELETE/OPTIONS/HEAD/PATCH)
      * Hostname container
      * HostIp
      * Url received
      * Uri received
      * Quesry String
      * List headers
      * Body content
    * How: `curl -X GET "http://localhost:8050/mock-api/sniffer/headers"`
    * Used: Demo, Debug
* F8-Error code
    * What: Choose Http status of response
    * How: 
      * return http status 404 : `curl -X GET "http://localhost:8050/mock-api/simulation/error/404"`
      * return http status 500 : `curl -X GET "http://localhost:8050/mock-api/simulation/error/500"`
    * Used: Gatling Resilience, Demo
* F9-Response time
    * What: Choose response time (value in path)
    * How: Response in 5s : `curl -X GET "http://localhost:8050/mock-api/simulation/timeout/5000"`
    * Used: Gatling Resilience
* F10-Response time in memory
    * What: Choose response time (value store in memory of server, by default: 0ms)
    * How: 
      * Set response time to 10s: `curl -X POST "http://localhost:8050/mock-api/simulation/timeout-memory/10000"` 
      * Get response in 10s: `curl -X GET "http://localhost:8050/mock-api/simulation/timeout-memory"` 
    * Used: Gatling Resilience
* F11-Response size
    * What: Get file with specify size on request
    * How: 
      * Get File 200Ko: `curl -X GET "http://localhost:8050/mock-api/simulation/response-size/200" --output /tmp/file200`
      * Get File 2Mo: `curl -X GET "http://localhost:8050/mock-api/simulation/response-size/2000" --output /tmp/file2000`
    * Used: Gatling Resilience, Demo, Test
* F12-Session not shared
    * What: Store session (by token key) in server without share scales instances
    * How: 
      * Run a secondary server (replace `-p 8050:8050` by `-p 8060:8050`)
      * Call first server with token "test", return timestamp of created session: `curl -X GET "http://localhost:8050/mock-api/session/share/test"` 
      * Call secondary server with token "test", return same timestamp of first server: `curl -X GET "http://localhost:8060/mock-api/session/share/test"` 
    * Used: Demo serverless constraints Docker
* F13-Shared Session
    * What: Share session (on multicast) between all servers
    * How: 
      * Run a secondary server (replace `-p 8050:8050` by `-p 8060:8050`)
      * Call first server with token "test", return timestamp of created session: `curl -X GET "http://localhost:8050/mock-api/session/notshare/test"` 
      * Call fisrt server with token "test", return same timestamp
      * Call secondary server with token "test", return timestamp of created session, but different of first server: `curl -X GET "http://localhost:8060/mock-api/session/notshare/test"` 
      * Call secondary server with token "test", return same timestamp
    * Used: Demo serverless constraints Docker 
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