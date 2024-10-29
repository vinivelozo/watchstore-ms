#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

#array to hold all our test data ids
allTestSalesIds=()
allTestInventoryIds=()
allTestStoreIds=()
allTestEmployeesIds=()


function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

##CREATE SOME CUSTOMER TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
#
body=\
'{
  "referenceNumber": "11"
}'
    recreateSaleAggregate 1 "$body" "e5913a79-5151-5151-9ffd-06578e7a4321" "11"

    #create some inventory test
    body=\
    '{
      "type":"Men5"
    }'
        recreateInventoryAggregate 1 "$body" "e5913a79-5151-5151-9ffd-06578e7a4321"

    #CREATE SOME STORE TEST DATA - POST REQUEST
    body=\
    '{
      "email": "store1@outlook.com",
      "street":"Boul. Des Promenades",
      "city":"St-Bruno",
      "province":"QC",
      "country":"Canada",
      "postalCode":"J5R8X1",

    }'

    recreateStoreAggregate 1 "$body"

    #CREATE SOME EMPLOYEE DATA - POST REQUEST
    body=\
    '{
      "first_name": "Vinicius",
      "email" : "vini@gmail.com",
      "salary" : 60000.0,
      "job_title" : "manager",
      "street" : "rue emerdol",
      "city" : "longueuil",
      "province" : "Quebec",
      "country" : "Canada"
    }'



    recreateEmployeeAggregate 1 "$body" "e5913a79-9b1e-4516-9ffd-06578e7af201"
#

} #end of setupTestdata


#USING CUSTOMER TEST DATA - EXECUTE POST REQUEST
function recreateEmployeeAggregate() {
    local testId=$1
    local aggregate=$2

    #create the employee and record the generated emmployeeId
    employeeId=$(curl -X POST http://$HOST:$PORT/api/v1/employees -H "Content-Type:
    application/json" --data "$aggregate" | jq '.employeeId')
    allTestEmployeesIds[$testId]=$employeeId
    echo "Added Employee with employeeId: ${allTestEmployeesIds[$testId]}"
}


#USING STORE TEST DATA - EXECUTE POST REQUEST
function recreateStoreAggregate(){
  local testId=$1
  local aggregate=$2

  storeId=$(curl -X POST http://$HOST:$PORT/api/v1/stores -H "Content-Type:
               application/json" --data "$aggregate" | jq '.storeId')
               allTestStoreIds[$testId]=$storeId
               echo "Added Store Aggregate with storeId: ${allTestStoreIds[$testId]}"
}


#don't start testing until all the microservices are up and running
function waitForService() {
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
waitForService curl -X DELETE http://$HOST:$PORT/api/v1/stores/e5913a79-9b1e-4516-9ffd-06578e736257

setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all employees works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/employees -s"
assertEqual 10 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted employees works
echo -e "\nTest 2: Verify that a normal get by id of earlier posted employee works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/employees/${allTestEmployeesIds[1]} '${body}' -s"
assertEqual ${allTestEmployeesIds[1]} $(echo $RESPONSE | jq .employeeId)
assertEqual "\"Vinicius\"" $(echo $RESPONSE | jq ".first_name")
#
#
## Verify that an update of an earlier posted employee works - put at api-gateway has no response body
#echo -e "\nTest 3: Verify that an update of an earlier posted employee works"
#body=\
#'{
#  "firstName":"Christine",
#  "lastName":"Gerard",
#  "emailAddress":"christine@gmail.com",
#  "streetAddress": "99 Main Street",
#  "city":"Montreal",
#  "province": "Quebec",
#  "country": "Canada",
#  "postalCode": "H3A 1A1",
#  "phoneNumbers": [
#    {
#      "type": "HOME",
#      "number": "514-555-5555"
#    },
#    {
#      "type": "WORK",
#      "number": "514-555-5556"
#    }
#  ]
#}'
#assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted employee works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/employees/${allTestEmployeesIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get customer request with a non existing employeeId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/employees/e5913a79-9b1e-4516-9ffd-06578e7af201 -s"
#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get employee request with an invalid employeeId"
assertCurl 422 "curl http://$HOST:$PORT/api/v1/employee/e5913a79-9b1e-4516-9ffd-06578e7af000 -s"


if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi