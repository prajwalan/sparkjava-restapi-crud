# sparkjava-restapi-crud

This is a sample REST API based on latest Java 8 and SparkJava framework. It demonstrates several key functionalities such as implementing GET and POST request routes, setting and checking the content-types, handling the exceptions and returning appropriate responses. 

### Implemented REST API
| Method | Request  | Description  |
| ------ | ---------- | -------- |
| GET | /customers | Returns a list of all customers |
| GET | /customers/{id} | Returns details of a particular customer, otherwise an error response is returned. |
| POST | /customers | Adds a new customer. A new ID is generated and is returned as response if operation is successful, otherwise an error response is returned. |
| POST | /customers/{id} | Updates the details of given customer. Updated details are returned as response if operation is successful, otherwise an error response is returned. |

### Response codes
| HTTP Code | Description  |
| ------ | -------- |
| 200 | Operation successful |
| 404 | Entity with given id not found |
| 400 | Invalid payload or content-type |
| 500 | Unexpected server error |

### Sample Requests/Responses

```sh
GET /customers
```
```sh
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
