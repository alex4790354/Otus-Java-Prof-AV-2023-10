dependencies {
    implementation("ch.qos.logback:logback-classic")
    implementation ("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation ("com.google.code.findbugs:jsr305")
    implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")
}

/*
    implementation("org.hibernate:hibernate-core:6.4.8.Final")
    implementation("org.hibernate:hibernate-entitymanager:6.4.8.Final")

    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
*/
