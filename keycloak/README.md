# Keycloak

## Run
* Create Docker Network: 
  `docker network create mock-api`
* run command for keycloak: 
  ```
  docker run --rm -it --name keycloak-demo \
    -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin \
    --network mock-api --network-alias keycloak.localhost \
    -v "$(pwd)"/keycloak/export.json:/tmp/demo-import.json \
    quay.io/keycloak/keycloak:11.0.3 -b 0.0.0.0 -Dkeycloak.migration.action=import \
    -Dkeycloak.migration.file=/tmp/demo-import.json -Dkeycloak.profile.feature.upload_scripts=enabled \
    -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.strategy=OVERWRITE_EXISTING
  ```
* Re-run mock with this command:
  ```
  docker run --rm -d -p 8050:8050 \
    --network mock-api -e KEYCLOAK_AUTH_SERVER_URL=http://keycloak.localhost:8080/auth \
    --name mock-api tuxivinc/mock-api
  ```

## Informations
* admin user: admin/admin
* user1: user1/user1
  * group user
* user2: user2/user2
  * group admin
* user3: user3/user3
  * group admin+user
* clientId & Secret: demo-app / 6f1fba02-4ba5-4ccc-be89-227f2a2e0b15
  
## Urls:
* Keycloack: http://localhost:8080/
* Tests rights:
  * unsecured: http://localhost:8050/mock-api/auth/unsecured
  * user: http://localhost:8050/mock-api/auth/user
  * admin: http://localhost:8050/mock-api/auth/admin
  * user and admin: http://localhost:8050/mock-api/auth/all-user
  * get token: TODO
  
## Tests
* KEYCLOAK_BEARER_ONLY=false -> Redirect to keycloak login page
  * example:
    * Call: http://localhost:8050/mock-api/auth/user
    * user/password: user1 / user1
    * redirect auto response api (show response information)
* KEYCLOAK_BEARER_ONLY=true -> No redirect (simulate front or another component get toker)
* Swagger auth TODO

AN: Pass this information when run mock container with arg `-e KEYCLOAK_BEARER_ONLY=xxxx`

## Command helper
* export :
  * docker exec -it keyclaok-demo sh
  * cd /opt/jboss/keycloak
  * bin/standalone.sh -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=/tmp/export.json -Djboss.socket.binding.port-offset=100
  * ctrl+c where "Admin console listening on http://127.0.0.1:10090"
  * exit
  * docker cp keycloak-demo:/tmp/export.json keycloak/.

  

