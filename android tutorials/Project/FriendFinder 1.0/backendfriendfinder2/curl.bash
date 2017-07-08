#!/bin/bash
: ${protocol=http}
: ${port=8080}
: ${host=localhost}
: ${logging=0}

# to execute this file type: "bash curl.bash"

function endPoint() {
    local service
    service=$1
    endpoint="$protocol://$host:$port$service"
}

curl_cmd="curl --show-error --insecure --verbose"
curl_header="$curl_cmd --header \"Content-Type: application/json\" --header \"Accept: application/json\""


curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"david@szabo.com","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"name":"David","gender":"male"}' 'http://localhost:8080/user/1'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"david1@szabo.com","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"name":"David1","gender":"male"}' 'http://localhost:8080/user/2'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"david2@szabo.com","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"name":"David3","gender":"male"}' 'http://localhost:8080/user/3'


#eval "$curl_header --request GET 'http://localhost:8080/account/all'"
#eval "$curl_header --request POST --data '{\"email\":\"david4@szabo.com\",\"password\":\"empty\"}' 'http://localhost:8080/account'"













