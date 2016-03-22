# sparkjava-restapi-crud

This is a sample REST API based on latest Java 8 and [SparkJava](http://sparkjava.com/) framework. It demonstrates several key functionalities such as implementing different HTTP request routes, setting and checking the content-types, handling the exceptions and returning appropriate responses. 

### Building and running
The project using Maven. So things should be easy and straight-forward.
> Command to generate project files for importing in Eclipse: mvn clean eclipse:eclipse
> Command to generate the jar file: mvn clean package

### The API
| Method | Request  | Description  |
| ------ | ---------- | -------- |
| POST | /login | Returns a JWS token in "result" field if the username and password is valid |
| GET | /extend | Returns a new the JWS token for the same username |
| GET | /customers | Returns a list of all customers |
| GET | /customers/{id} | Returns details of a particular customer, otherwise an error response is returned. |
| POST | /customers | Adds a new customer. A new ID is generated and is returned as response if operation is successful, otherwise an error response is returned. |
| POST | /customers/{id} | Updates the details of given customer. Updated details are returned as response if operation is successful, otherwise an error response is returned. |
| DELETE | /customers/{id} | Deletes/deactivates a particular customer |

A different implementation may make use of PUT HTTP method also. For eg, when POST is used for update, then PUT may be used for adding. 

### Security
The API implements [JWT](https://jwt.io/) token based security. As such a secure token needs to be sent as a URL parameter with all URLs except /login. See the example requests below in samples section. Each of these tokens may be reused infinitely but they have a life time of 8 hours for security reasons. So after 8 hours consumer must either login again or call /extend service to get a newer token. 

You may use admin/secret as demo username and password.

### Demo data
Since the intent of this sample code is to demonstrate REST API functionality using SparkJava, database has been mocked in DemoStorage class. So all the customer records are just stored and retrieved from a hashmap. The username and password credentials are also set just as constants. 

### Response codes
| HTTP Code | Description  |
| ------ | -------- |
| 200 | Operation successful |
| 400 | Invalid payload or content-type |
| 403 | Invalid credentials or authentication token |
| 404 | Entity with given id not found |
| 500 | Unexpected server error |

### Response Format
All responses have a standard format. They contain a code, message and result. The code 0 indicates successful response and in this case result contains JSON representation of the entity. In case of error, code is -1 and message contains the text description of what went wrong.
The "result" field contains the actual result which may be a list of customers, login JWT token or something else depending upon the service.

```sh
{
  "code": 0,
  "message": "",
  "result":<json content>
}
```
```sh
{
  "code": -1,
  "message": "Invalid content type: text/plain"
}
```

### Sample Requests/Responses

```sh
POST /login
content-type: application/json
{
  "username": "admin",
  "password": "secret"
}
```
```sh
200 OK
{
  "result": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwidGltZXN0YW1wIjoxNDU4NjUxMDI0NTAwfQ.Nsq66NVlrNxbuNChSRacM1VKsEeT_hp8OBbW5z_xjPk",
  "code": 0,
  "message": ""
}
```

```sh
GET /customers?token=<token>
```
```sh
200 OK
{
  "code": 0,
  "message": "",
  "result": [
    {
      "id": 1,
      "firstname": "Anna",
      "lastname": "Bedecs",
      "address": "123 1st Street",
      "city": "Seattle",
      "country": "USA",
      "business_phone": "(123)555-0100",
      "email": "anna.bedecs@gmail.com",
      "zip_postalcode": 99999
    },
    {
      "id": 2,
      "firstname": "Antonio",
      "lastname": "Gratacos Solsona",
      "address": "123 2nd Street",
      "city": "Boston",
      "country": "USA",
      "business_phone": "(123)555-0200",
      "email": "antonio.g.solsona@gmail.com",
      "zip_postalcode": 99998
    }
  ]
}
```

```sh
GET /customers/1?token=<token>
```
```sh
200 OK
{
  "code": 0,
  "message": "",
  "result": [
    {
      "id": 1,
      "firstname": "Anna",
      "lastname": "Bedecs",
      "address": "123 1st Street",
      "city": "Seattle",
      "country": "USA",
      "business_phone": "(123)555-0100",
      "email": "anna.bedecs@gmail.com",
      "zip_postalcode": 99999
    }
  ]
}
```

```sh
GET /customers/100?token=<token>
```
```sh
404 Not Found
{
  "code": -1,
  "message": "Customer with id 100 not found"
}
```

```sh
POST /customers?token=<token>
content-type: application/json
{
  "firstname": "aaa",
  "lastname": "bbbb",
  "address": "123 1st Street",
  "city": "Seattle",
  "country": "USA",
  "business_phone": "(123)555-0100",
  "email": "aaa.bbbb@gmail.com",
  "zip_postalcode": 99999
}
```
```sh
200 OK
{
  "code": 0,
  "message": "",
  "result": {
    "id": 3,
    "firstname": "aaa",
    "lastname": "bbb",
    "address": "123 1st Street",
    "city": "Seattle",
    "country": "USA",
    "business_phone": "(123)555-0100",
    "email": "aaa.bbbb@gmail.com",
    "zip_postalcode": 99999
  }
}
```
