#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Must provide realm name and client name"
    exit -1
fi

set -e
set -x

ADMIN_CMD="/opt/jboss/keycloak/bin/kcadm.sh"
REALM=$1
CLIENT=$2

# Login
$ADMIN_CMD config credentials \
  --server http://localhost:8080/auth \
  --realm master \
  --user admin \
  --password admin

# Create realm
$ADMIN_CMD create realms \
  -s realm=$REALM \
  -s enabled=true

# Create client
$ADMIN_CMD create clients \
  -r $REALM \
  -s clientId=$CLIENT \
  -s 'redirectUris=["http://localhost:8080/*"]' \
  -s clientAuthenticatorType=client-secret \
  -s directAccessGrantsEnabled=true

$ADMIN_CMD create roles \
  -r $REALM \
  -s name=user

$ADMIN_CMD create users -r $REALM -f - <<EOF
{
  "username": "testuser",
  "enabled": true,
  "credentials":
  [
    {
      "type": "password",
      "value": "password"
    }
  ]
}
EOF

$ADMIN_CMD add-roles \
  -r $REALM \
  --uusername testuser \
  --rolename user

