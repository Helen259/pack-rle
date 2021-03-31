plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.1.0"//плагин для создания запуск java файлов
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
sourceSets.getByName("main") {
    java.srcDir("src/main/java")
    java.srcDir("src/main/kotlin")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java")
    java.srcDir("src/test/kotlin")
}
dependencies {
    implementation(kotlin("stdlib"))
    //testCompile("junit", "junit", "4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("com.github.ajalt", "clikt", "2.1.0")//для консольных приложений
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>{
    kotlinOptions.jvmTarget="1.8"
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>{
    manifest{attributes["Main-class"]="packrle.PackrleKt"}
}
tasks.getByName<Test>("test"){
    useJUnitPlatform()
}