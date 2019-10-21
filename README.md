# Mock-api 
Api for test performances, resilience and show fonctionnality in demo (docker for example, with healthcheck)

## Informations
* sources : https://github.com/Tuxivinc/mock-api

## Fonctions
* Get request informations
 * body, headers and URL
* Simul healcheck
 * get http code
 * set http code return
* Sleep time response
* Size response
* Return Http status specify
* Shutdown application
* Get JVM args
* Get environment variables
* Get Hostname

## For more informations 
1/ Deploy container : docker run -d -p 8050:8050 --name mock-api tuxivinc/mock-api:1.2

2/ Call swagger for API infos : http://localhost:8050/mock-api/swagger-ui.html
