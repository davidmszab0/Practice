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
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"David","gender":"Male","movieGenres":[{"id":null,"name":"Comedy","users":null},{"id":null,"name":"Action","users":null}], "musicGenres":[{"id":null,"name":"Jazz","users":null}, {"id":null,"name":"Blues","users":null}]}' 'http://localhost:8080/user/1'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"peter@szabo.com","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Peter","gender":"Male","movieGenres":[{"id":null,"name":"Action","users":null}], "musicGenres":[{"id":null,"name":"Blues","users":null}, {"id":null,"name":"Opera","users":null}]}' 'http://localhost:8080/user/2'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"john@szabo.com","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"John","gender":"Male","movieGenres":[{"id":null,"name":"Bromance","users":null}], "musicGenres":[{"id":null,"name":"Rock","users":null}]}' 'http://localhost:8080/user/3'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"jacob","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Jacob","gender":"Male","movieGenres":[{"id":null,"name":"Adventure","users":null}, {"id":null,"name":"Drama","users":null}], "musicGenres":[{"id":null,"name":"Gospel","users":null}]}' 'http://localhost:8080/user/4'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"e","password":"e"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Missy","gender":"Female","movieGenres":[{"id":null,"name":"Bibliography","users":null}], "musicGenres":[{"id":null,"name":"Pop","users":null}]}' 'http://localhost:8080/user/5'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"Erica","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Erica","gender":"Female","movieGenres":[{"id":null,"name":"Romantic","users":null}, {"id":null,"name":"Drama","users":null}], "musicGenres":[{"id":null,"name":"Gospel","users":null}, {"id":null,"name":"Country","users":null}]}' 'http://localhost:8080/user/6'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"sarah","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Sarah","gender":"Female","movieGenres":[{"id":null,"name":"Historical","users":null}, {"id":null,"name":"Western","users":null}], "musicGenres":[{"id":null,"name":"Electronic","users":null}]}' 'http://localhost:8080/user/7'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"carl","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Carl","gender":"Male","movieGenres":[{"id":null,"name":"War","users":null}, {"id":null,"name":"Drama","users":null}], "musicGenres":[{"id":null,"name":"Latin","users":null}]}' 'http://localhost:8080/user/8'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"jacob4","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Jacob","gender":"Male","movieGenres":[{"id":null,"name":"Adventure","users":null}, {"id":null,"name":"Drama","users":null}], "musicGenres":[{"id":null,"name":"Latin","users":null}]}' 'http://localhost:8080/user/9'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"oscar","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Oscar","gender":"Male","movieGenres":[{"id":null,"name":"War","users":null}, {"id":null,"name":"Adventure","users":null}], "musicGenres":[{"id":null,"name":"Indie","users":null}]}' 'http://localhost:8080/user/10'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"henok","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Henok","gender":"Male","movieGenres":[{"id":null,"name":"Adventure","users":null}, {"id":null,"name":"Comedy","users":null}], "musicGenres":[{"id":null,"name":"Rock","users":null}]}' 'http://localhost:8080/user/11'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"tamru","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Tamru","gender":"Male","movieGenres":[{"id":null,"name":"Adventure","users":null}, {"id":null,"name":"Drama","users":null}], "musicGenres":[{"id":null,"name":"Gospel","users":null}]}' 'http://localhost:8080/user/12'

curl --verbose --header "Content-Type: application/json" --request POST --data '{"email":"kevin","password":"empty"}' 'http://localhost:8080/account'
curl --verbose --header "Content-Type: application/json" --request PUT \
--data '{"name":"Kevin","gender":"Male","movieGenres":[{"id":null,"name":"Adventure","users":null}, {"id":null,"name":"Drama","users":null}], "musicGenres":[{"id":null,"name":"Gospel","users":null}]}' 'http://localhost:8080/user/13'

#eval "$curl_header --request GET 'http://localhost:8080/account/all'"
#eval "$curl_header --request POST --data '{\"email\":\"david4@szabo.com\",\"password\":\"empty\"}' 'http://localhost:8080/account'"













