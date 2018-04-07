Spring Boot Keycloak App Test
=================================================

Playing around with Keycloak and Spring Boot just to see how easy they work together. Code uses
[this blog post](https://developers.redhat.com/blog/?p=432287) as the starting point. 

As an extension to that tutorial I will be:
- Adding custom fields to user
- Using docker and/or docker compose to handle both Spring Boot app and Keycloak
- And if I can figure it out, proxying requests to the Keycloak server via the app. I'd prefer Keycloak not be visible
- to the user at all.

## Running Keycloak
To run Keycloak as a docker image just do:
```
docker run -d \
  --name keycloak \
  -e KEYCLOAK_USER=admin \
  -e KEYCLOAK_PASSWORD=admin \
  -p 9001:8080 \
  jboss/keycloak
```
^Thank you [Sandor Nemeth](https://github.com/sandor-nemeth) for that docker snippet.

### Keycloak Setup
```
docker cp scripts/keycloak-setup.sh keycloak {realm} {client}
docker exec keycloak /opt/jboss/keycloak-setup.sh
# Replace clientId with the actual client id outputted in the last step
docker exec keycloak /opt/jboss/keycloak/bin/kcadm.sh get -r {realm} clients/{clientId}/client-secret
```
