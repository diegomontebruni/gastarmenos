import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.flywaydb.flyway") version "10.14.0"
	id("io.gitlab.arturbosch.detekt") version "1.23.6"

	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.montebruni"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("io.github.microutils:kotlin-logging:2.0.11")

	// Database
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	runtimeOnly("org.postgresql:postgresql")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.8")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:1.19.3")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.2")
	}
}

// Temporary configuration while detek not support kotlin 1.9
configurations.all {
	resolutionStrategy.eachDependency {
		if (requested.group == "org.jetbrains.kotlin") {
			useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
		}
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

detekt {
	baseline = file("$projectDir/detekt/baseline.xml")
	config.setFrom("$projectDir/detekt/detekt.yml")
	buildUponDefaultConfig = true
	autoCorrect = true
}

tasks.detekt {
	reports {
		xml.required.set(false)
		html.required.set(false)
		txt.required.set(false)
		sarif.required.set(false)
	}

	include("**/*.kt", "**/*.kts")
	exclude("**/resources/**", "**/build/**")
}

tasks.detektBaseline {
	include("**/*.kt", "**/*.kts")
	exclude("**/resources/**", "**/build/**")
}

flyway {
	url = "jdbc:postgresql://localhost:5432/gastarmenos"
	user = "app_gastar_menos"
	password = "app_gastar_menos"
}
