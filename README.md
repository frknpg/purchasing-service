# E-Ticaret Satın Alma

This web service controls to invoices and theirs limits. The new invoice information to be entered into the accounting system will be based on all APPROVED invoices that the procurement specialist has entered so far.
is NOT APPROVED if it increases the total above the specified limit, or APPROVED if it does not exceed the limit.

## Run the project

To run project, run the shell executable file `./run.sh`. Application running on `8080` default port.

In order to test project run the `mvn test` command in the project root directory.

## Endpoints

* `POST /api/v1/invoices` for add a new invoice.

* `GET /api/v1/invoices` for get all invoices.

### Examples

* Transaction Information: "John,Doe,john@doe.com,200,TR0001" Credit Limit : 200
  Result : The transaction of John Doe is approved

* Transaction Information: "Jane,Doe,jane@doe.com,201,TR0001" Credit Limit : 200
  Result : The transaction of John Doe is rejected

```
1) Transaction Information: "Jane,Doe,jane@doe.com,199,TR0001"
2) Transaction Information: "Jane,Doe,jane@doe.com,2,TR0002" 

Credit Limit : 200
   
Result : The second transaction of Jane Doe is rejected
```

## CURL Examples

* Authentication

```shell
accessToken=$(curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{"email": "john@doe.com","password": "demopass"}' \
 | jq -r '.accessToken')
```

* Add a new invoice

```shell
curl --location 'http://localhost:8080/api/v1/invoices' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer '$accessToken \
--data-raw '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@doe.com",
    "amount": 45,
    "productName": "TR0001",
    "billNo": 2
}'
```

* Get invoice list

```shell
curl --location 'http://localhost:8080/api/v1/invoices' \
--header 'Authorization: Bearer '$accessToken
```

## Development Requirements

Project requires the following to run:

* Java 21
* Apache Maven 3.9.0+