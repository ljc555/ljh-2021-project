# odata-example
Access data using olingo odata. Tech stack : Odata, Spring boot, H2

Examples:-

curl http://localhost:8080/odata/$metadata
curl http://localhost:8080/odata/CarMakers
curl http://localhost:8080/odata/CarModels
curl http://localhost:8080/odata/CarMakers(1)
curl http://localhost:8080/odata/CarModels(1)
curl http://localhost:8080/odata/CarModels(1)/CarMakerDetails
curl http://localhost:8080/odata/CarMakers?$filter=startswith(Name,'B')
