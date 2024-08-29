# Bank-System-Project

*Banking system project* where I implement:
- **Maven** (SOFTWARE TOOL) 
- **Graphic Interfaces** (JSWING)
- **Database** (MYSQL)
- **Spring/SpringBoot** (FRAMEWORK) 
- **Unit Testing Mockito** (FRAMEWORK TESTING) 
- **Regular Expressions** (REGEX) 
- **APIS Implementation** (API_URL = "https://app.exchangerate-api.com")

## API Reference

## ----------CUSTOMER----------
### Get all customers

```http
  GET /api/customer
```
Retrieves a list of all customers.

#### Responses
- *200 OK*: Returns a list of CustomerDTO.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Get customer by id

```http
  GET /api/customer/${id}
```
Retrieves the details of a specific customer by their ID.

| Parameter | Type   | Description                                |
| :-------- |:-------|:-------------------------------------------|
| `id`      | `Long` | **Required**. ID of the customer to fetch. |

#### Responses
- *200 OK*: Returns a CustomerDTO with the customer's details.
- *404 NOT FOUND*: If no customer is found with the specified ID.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Get bank accounts

```http
  GET /api/customer/${id}/bank-account
```
Retrieves all bank accounts associated with a specific customer.

| Parameter | Type   | Description                                                    |
| :-------- |:-------|:---------------------------------------------------------------|
| `id`      | `Long` | **Required**. ID of the customer to fetch their bank accounts. |

#### Responses
- *200 OK*: Returns a CustomerWithBankAccountDTO with the customer's bank accounts.
- *404 NOT FOUND*: If no customer is found with the specified ID.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Create customer

```http
  POST /api/customer
```
Creates a new customer.

| Parameter | Type                  | Description                                 |
|:----------|:----------------------|:--------------------------------------------|
| `jsonMap` | `Map<String, Object>` | **Required**. Customer data in JSON format. |

#### Responses
- *201 CREATED*: Returns a ResponseDTO with the status and success message.
- *400 BAD REQUEST*: If the jsonMap violates the DTO constraint.
- *409 CONFLICT*: If the value of a unique field already exists in the database.
- *422 UNPROCESSABLE ENTITY*: If there are invalid keys in the jsonMap.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Update customer

```http
  PUT /api/customer/${id}
```
Updates the details of an existing customer.

| Parameter   | Type                  | Description                                           |
|:------------|:----------------------|:------------------------------------------------------|
| `id`        | `Long`                | **Required**. ID of the customer to update.           |
| `jsonMap`   | `Map<String, Object>` | **Required**. Customer data to update in JSON format. |

#### Responses
- *200 OK*: Returns a CustomerDTO with the updated customer details.
- *404 NOT FOUND*: If no customer is found with the specified ID.
- *409 CONFLICT*: If the value of a unique field already exists in the database.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the update.

### Delete customer

```http
  DELETE /api/customer/${id}
```
Deletes an existing customer.

| Parameter   | Type                  | Description                                 |
|:------------|:----------------------|:--------------------------------------------|
| `id`        | `Long`                | **Required**. ID of the customer to delete. |

#### Responses
- *204 NO CONTENT*: If the customer was successfully deleted.
- *404 NOT FOUND*: If no customer is found with the specified ID.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the deletion.

## ----------BANK ACCOUNT----------
### Get all bank accounts

```http
  GET /api/bank-account
```
Fetches a list of all bank accounts.

#### Responses
- *200 OK*: Returns a list of BankAccountDTO.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Get customer by id

```http
  GET /api/bank-account/${id}
```
Retrieves the details of a specific bank account by its ID.

| Parameter | Type   | Description                                    |
| :-------- |:-------|:-----------------------------------------------|
| `id`      | `Long` | **Required**. ID of the bank account to fetch. |

#### Responses
- *200 OK*: Returns a BankAccountDTO with the bank account's details.
- *404 NOT FOUND*: If no bank account is found with the specified ID.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Get bank accounts by bank entity

```http
  GET /api/bank-account/entity/${urlDescription}
```
Retrieves a list of bank accounts associated with a specified bank entity.

| Parameter        | Type     | Description                                                    |
|:-----------------|:---------|:---------------------------------------------------------------|
| `urlDescription` | `String` | **Required**. The URL-friendly description of the bank entity. |

#### Responses
- *200 OK*: Returns a list of BankAccountDTO for the specified bank entity.
- *404 NOT FOUND*: If no bank account is found for the specified entity.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Get movements by bank account

```http
  GET /api/bank-account/${id}/movements
```
Retrieves the movements associated with a specific bank account by its ID.

| Parameter | Type   | Description                                                    |
|:----------|:-------|:---------------------------------------------------------------|
| `id`      | `Long` | **Required**. ID of the bank account to fetch their movements. |

#### Responses
- *200 OK*: Returns a BankAccountMovementsDTO containing the movements of the specified bank account.
- *404 NOT FOUND*: If no bank account is found for the specified entity.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.

### Create bank account

```http
  POST /api/bank-account
```
Creates a new bank account.

| Parameter | Type                  | Description                                     |
|:----------|:----------------------|:------------------------------------------------|
| `jsonMap` | `Map<String, Object>` | **Required**. Bank account data in JSON format. |

#### Responses
- *201 CREATED*: Returns a ResponseDTO with the status and success message.
- *400 BAD REQUEST*: If the jsonMap violates the DTO constraint.
- *404 NOT FOUND*: If the customer NID specified in the JSON is not found.
- *409 CONFLICT*: If the value of a unique field already exists in the database.
- *422 UNPROCESSABLE_ENTITY*: If there are invalid keys in the jsonMap.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the creation.

### Update bank account

```http
  PUT /api/bank-account/${id}
```
Updates the details of an existing bank account.

| Parameter   | Type                  | Description                                               |
|:------------|:----------------------|:----------------------------------------------------------|
| `id`        | `Long`                | **Required**. ID of the bank account to update.           |
| `jsonMap`   | `Map<String, Object>` | **Required**. Bank account data to update in JSON format. |

#### Responses
- *200 OK*: Returns a BankAccountDTO with the updated details of the bank account.
- *404 NOT FOUND*: If no bank account is found with the specified ID.
- *409 CONFLICT*: If the value of a unique field already exists in the database.
- *422 UNPROCESSABLE_ENTITY*: If there are invalid keys in the jsonMap.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the update.

### Delete bank account

```http
  DELETE api/bank-account/${id}
```
Deletes an existing bank account.

| Parameter | Type   | Description                                     |
|:----------|:-------|:------------------------------------------------|
| `id`      | `Long` | **Required**. ID of the bank account to delete. |

#### Responses
- *204 NO CONTENT*: If the bank account was successfully deleted.
- *404 NOT FOUND*: If no bank account is found with the specified ID.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the deletion.

## ----------MOVEMENT----------
### Get movement by id

```http
  GET /api/movement/${id}
```
Retrieves the details of a specific movement by its ID.

| Parameter | Type   | Description                                |
|:----------|:-------|:-------------------------------------------|
| `id`      | `Long` | **Required**. ID of the movement to fetch. |

#### Responses
- *200 OK*: Returns a MovementDTO with the details of the movement.
- *404 NOT FOUND*: If no movement is found with the specified ID.
- *500 INTERNAL SERVER ERROR*: If a server error occurs.


### Make withdraw
Creates a new movement

```http
  POST /api/movement/withdraw
```

| Parameter | Type                  | Description                                   |
|:----------|:----------------------|:----------------------------------------------|
| `jsonMap` | `Map<String, Object>` | **Required**. Withdrawal data in JSON format. |

#### Responses
- *201 CREATED*: Returns a ResponseDTO indicating the withdrawal was successful.
- *400 BAD REQUEST*: If the jsonMap violates the DTO constraint.
- *402 PAYMENT_REQUIRED*: If bank account no have sufficient funds.
- *422 UNPROCESSABLE_ENTITY*: If there are invalid keys in the jsonMap.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the creation.

### Make transfer
Creates a new movement.

```http
  POST /api/movement/transfer
```

| Parameter | Type                  | Description                                 |
|:----------|:----------------------|:--------------------------------------------|
| `jsonMap` | `Map<String, Object>` | **Required**. Transfer data in JSON format. |

#### Responses
- *201 CREATED*: Returns a ResponseDTO indicating the transfer was successful.
- *400 BAD REQUEST*: If the jsonMap violates the DTO constraint.
- *400 BAD REQUEST*: If the source and destination accounts are the same.
- *400 BAD REQUEST*: If the currency in the jsonMap does not match the currency of the source account.
- *400 BAD REQUEST*: If there is a currency mismatch between the source and destination accounts.
- *402 PAYMENT_REQUIRED*: If source bank account no have sufficient funds.
- *422 UNPROCESSABLE_ENTITY*: If there are invalid keys in the jsonMap.
- *423 LOCKED*: If the source account is inactive.
- *423 LOCKED*: If the destination account is inactive.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the creation. 

### Make invest
Creates a new movement.

```http
  POST /api/movement/invest
```

| Parameter | Type                  | Description                                   |
|:----------|:----------------------|:----------------------------------------------|
| `jsonMap` | `Map<String, Object>` | **Required**. Investment data in JSON format. |

#### Responses
- *201 CREATED*: Returns a ResponseDTO indicating the transfer was successful.
- *400 BAD REQUEST*: If the jsonMap violates the DTO constraint.
- *400 BAD REQUEST*: If an invalid fixed term value was entered.
- *402 PAYMENT_REQUIRED*: If source bank account no have sufficient funds.
- *422 UNPROCESSABLE_ENTITY*: If there are invalid keys in the jsonMap.
- *500 INTERNAL SERVER ERROR*: If a server error occurs during the creation.
 
       _____________________________
      |                             |
      | All rights reserved.        |
      |                             |
      |Author: Matias Nicolas Dupont|
      |                             |
      |File: 21142                  |
      |_____________________________|