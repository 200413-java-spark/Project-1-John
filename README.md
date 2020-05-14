# Spark Stocks 1.0.1

***Spark Stocks Is An ETL Batch Processor For Historic Ticker Data Served As Spring A Microservice.
The Goal Of Spark Stocks Is To Identify Clusters Of Potential Support And Resistance Within The Dataset.***

## Build
### Java
`
java - jar project-1-john-1.0.1-SNAPSHOT.war
`
### DockerFile
```
FROM postgres
ENV POSTGRES_USER mydb
ENV POSTGRES_PASSWORD mydb
EXPOSE 5432
```

(You'll Also Need To Change The IP Of The Server In The Application.properties File.)

### Docker
```
docker build -t mydb -f ./Dockerfile
docker run --name mydb -d --rm -p 5432:5432 mydb
```

## Usage
Upload A File At:

`
http://localhost:8080/
`

## Design
### Architecture
**Business Logic Layer:**
- Java
- Apache Spark
- JUnit
- Spring Boot
- Spring MVC

**Data Access Layer:**
- Docker
- PostgreSQL
- AWS - EC2
- Spring JPA
- Spring Data

**Presentation Layer:**
- D3
- Bootstrap 4
- JavaScript
- CSS
- HTML


### Main Algorithm

- The User Uploads A CSV To A Form Styled With Bootstrap 4.
- The CSV Is Written To Disk On The Server.
- Spark Reads The File Into Memory, Caches It, Filters The Header, Filters The Unnecessary Columns, Rounds Off All The ‘close’ Values Within The Dataset, And Finally Combines The Values Into Key-value Pairs.
- The Key-value Pairs Are Persisted To A Remote PostgreSQL Docker Instance On AWS.
- The User Is Redirected To A Bar Chart Using D3 That Consumes A JSON API. (The API Reflects The Stored Values In PostgreSQL.)
