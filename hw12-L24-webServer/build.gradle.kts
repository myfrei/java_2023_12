dependencies {
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    implementation("ch.qos.logback:logback-classic")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.flywaydb:flyway-core")

    implementation("org.postgresql:postgresql")

    implementation("org.ehcache:ehcache")
    implementation("org.eclipse.jetty:jetty-server")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-webapp")

    implementation("org.eclipse.jetty:jetty-io")
    implementation("org.eclipse.jetty:jetty-http")

    implementation("org.eclipse.jetty:jetty-util")
    implementation("org.eclipse.jetty:jetty-security")
    implementation("org.freemarker:freemarker")
}