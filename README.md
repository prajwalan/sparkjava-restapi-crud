# sparkjava-restapi-crud

This is a sample REST API based on latest Java 8 and SparkJava framework. It demonstrates several key functionalities such as implementing GET and POST request routes, setting and checking the content-types, handling the exceptions and returning appropriate responses. 

### Implemented REST API
| Method | Request  | Description  |
| ------ | ---------- | -------- |
| GET | /customers | Returns a list of all customers |
| GET | /customers/{id} | Returns details of a particular customer, otherwise an error response is returned. |
| POST | /customers | Adds a new customer. A new ID is generated and is returned as response if operation is successful, otherwise an error response is returned. |
| POST | /customers/{id} | Updates the details of given customer. Updated details are returned as response if operation is successful, otherwise an error response is returned. |

