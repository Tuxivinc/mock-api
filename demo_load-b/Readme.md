# Démo: 

* Run two containers
 * `docker run --rm -d -p 8050:8050 --name mock-api -h mockapi1 tuxivinc/mock-api:1.3`
 * `docker run --rm -d -p 8060:8050 --name mock-api2 -h mockapi2 tuxivinc/mock-api:1.3`
* Test 
 * http://localhost:8050/mock-api/getinfos/hostname
 * http://localhost:8060/mock-api/getinfos/hostname

