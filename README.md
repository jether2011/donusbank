# Donus Bank - Challenge

### Pre-requirements

1. Docker service installed
2. Postman client to import collection and environments

### To start backend API application

1. Clone that GitHub project
2. Go to the cloned project folder
3. Execute `sh start_api.sh` (the jvm and environments variables is set into script file)
4. Use Postman collection to execute the operations

### Environment variables and JVM args

1. Environment variables
```text
DB_USER=donusbank
DB_PASSWORD=dOnUSB@nK
DB_DONUS_BANK=donusbank
```

2. JVM arguments
```text
-Dspring-boot.run.profiles=dev 
-Dspring-boot.run.jvmArguments="-Xmx256m -Xms128m"
```

### Future proposal

1. Add a cache using REDIS
2. Improve second-level cache
3. Improve others need tests to improve the coverage
4. To improve audit can we use Envers
5. Improve implementation for logs 
6. Create API documentation to expose the endpoints contracts using Swagger
7. Improve account operations historic