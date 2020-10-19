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

## For more informations 
1/ Deploy container : docker run -d -p 8050:8050 --name mock-api tuxivinc/mock-api

2/ Call swagger for API infos : http://localhost:8050/mock-api/swagger-ui.html
