rootProject.name = "api-doc-conf"

//services-doc
include("api-doc-ui")
project(":api-doc-ui").projectDir = file("services-doc/api-doc-ui")

include(":api-doc-service")
project(":api-doc-service").projectDir = file("services-doc/api-doc-service")

//services-java
include("info-service")
project(":info-service").projectDir = file("services-java/info-service")

include("pet-service")
project(":pet-service").projectDir = file("services-java/pet-service")

include("user-service")
project(":user-service").projectDir = file("services-java/user-service")

//swagger-doc
include("swagger-simple-service")
project(":swagger-simple-service").projectDir = file("swagger-doc/swagger-simple-service")

include("swagger-multiple-services")
project(":swagger-multiple-services").projectDir = file("swagger-doc/swagger-multiple-services")

