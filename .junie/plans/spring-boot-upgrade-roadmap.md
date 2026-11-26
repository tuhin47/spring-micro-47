---
sessionId: session-261126-114441-d5cg
---

# Requirements

### Goal
Provide a safe roadmap for upgrading the Maven reactor to Spring Boot `4.1.x` on Java `25`, with a compatible Spring Cloud release train and validation across all services.

### Scope
- Align Spring Boot, Spring Cloud, Java compiler/toolchain, CI, container, and development-image versions.
- Keep service boundaries and application behavior unchanged except for compatibility fixes required by the upgrade.
- Treat Spring Boot `4.1.x` and Java `25` as the selected targets; select exact compatible patch releases during the baseline stage.

# Technical Design

### Current Implementation
- The root `pom.xml` parents the Maven reactor and currently pins Spring Boot parent/property `3.4.1`, Java/compiler levels `22`, and Spring Cloud BOM `2024.0.0`.
- `apigateway`, `configserver`, and `service-registry` depend on Spring Cloud Gateway, Config, and Eureka. Their POMs explicitly set the Spring Boot Maven plugin from `${spring-boot.version}`; other modules inherit versions and include Axon, JPA/MySQL, MongoDB, Redis, tracing, and related integrations.
- Java 22 is pinned in `.github/workflows/build.yml`, `.circleci/config.yml`, `Build.Dockerfile`, `Dockerfile`, `tuhin47.dev.Dockerfile`, `docker-compose-prod.yml`, and the root Maven compiler properties. `dev.Dockerfile` and `runConfigurations/tuhin47_javadev.run.xml` reference the custom `tuhin47/javadev:22` image.

### Proposed Roadmap
1. Establish a clean baseline, verify the Boot 4.1.x / Java 25 support requirements, and select exact patches. Spring Cloud's compatibility guidance maps Boot 4.1.x to the `2025.1.x` train starting at `2025.1.2`; confirm the current supported train/patch before pinning it. Record pre-existing build failures.
2. Align the root Maven platform: Boot parent/property, Spring Cloud BOM, and Java/compiler properties. Reconcile plugin and direct Boot-version declarations in the module POMs, then update CI, container, Compose, and custom development-image references to Java 25 where supported.
3. Migrate compatibility surfaces in order: Gateway, Config Server, and Eureka first, then business services and shared modules. Address verified incompatibilities, especially major-version framework changes and Axon, persistence/Flyway, tracing, legacy JAXB/JWT, and Springdoc integrations.
4. Validate targeted modules and the full Maven reactor, then verify Java 25 container builds and a Compose smoke test for startup, discovery/configuration, gateway routing, persistence, and the order/payment workflow.

### Key Decisions
- Use Spring Boot `4.1.x` and Java `25` as the target pairing specified by the user; choose and document exact patch versions before changing the platform.
- Pair Boot with the compatible Spring Cloud `2025.1.x` train, confirming the currently supported patch (the official mapping begins with `2025.1.2` for Boot 4.1.x) rather than retaining the current `2024.0.0` BOM.
- Preserve module boundaries and make compatibility changes in the modules that own them; do not carry old dependency versions forward without verifying Boot 4 compatibility.

# Testing

### Validation Approach
- Capture existing failures with the baseline Maven build before version changes.
- After alignment, run focused tests for `apigateway`, `configserver`, and `service-registry`, then affected application and shared modules.
- Run `mvn -B clean install --file pom.xml`; build the project images with Java 25-compatible builder/runtime images.
- Smoke-test `docker-compose-prod.yml` for readiness, Eureka registration, Config Server access, gateway routes, persistence, and the order/payment workflow.

### Regression Checks
- Distinguish pre-existing failures from upgrade regressions and confirm existing controller/service behavior remains intact.
- Confirm Java 25 is used consistently in Maven, GitHub Actions, CircleCI, production/development Docker workflows, Compose build images, and any custom development image.

# Delivery Steps

### ✓ Step 1: Baseline and confirm exact compatible releases
The baseline is recorded and exact Spring Boot 4.1.x, Spring Cloud, and Java 25 versions are confirmed before implementation.
- Record current versions and direct dependency constraints from the root `pom.xml` and module POMs; run the existing reactor build and note failures.
- Verify the official Boot 4.1.x Java 25 requirements and select the intended Boot patch.
- Confirm the currently supported Spring Cloud `2025.1.x` patch compatible with Boot 4.1.x (the mapping starts at `2025.1.2`) and identify third-party dependencies that need migration.

### ✓ Step 2: Align Maven versions and Java 25 build/runtime environments
The Maven reactor and all supported build/deployment paths use the selected Boot/Cloud versions and Java 25.
- Update the parent, `${spring-boot.version}`, Spring Cloud BOM, and Java/compiler properties in root `pom.xml`.
- Reconcile plugin and explicit Boot-version declarations, including `apigateway/pom.xml`, `configserver/pom.xml`, and `service-registry/pom.xml`.
- Update Java pins in `.github/workflows/build.yml`, `.circleci/config.yml`, `Build.Dockerfile`, `Dockerfile`, `tuhin47.dev.Dockerfile`, and `docker-compose-prod.yml`.
- Verify Java 25 availability for `dev.Dockerfile` and `runConfigurations/tuhin47_javadev.run.xml`; update their custom image reference only if a compatible image is available or built.

### ✓ Step 3: Migrate infrastructure and service dependencies
Infrastructure modules and then the application/shared modules compile and pass focused tests on the new platform.
- Migrate Gateway, Config Server, and Eureka integrations in `apigateway`, `configserver`, and `service-registry` first.
- Resolve demonstrated incompatibilities across `authservice`, `productservice`, `orderservice`, `paymentservice`, `chat-service`, `common`, `saga`, and `aws-reactive`.
- Verify or update Axon, database/JPA and Flyway, tracing, legacy JWT/JAXB, and Springdoc integrations; add or adjust focused tests with each behavior/API change.

### ✓ Step 4: Verify the reactor and deployment workflow
The Java 25 / Spring Boot 4.1.x stack passes CI-equivalent validation and a production Compose smoke test.
- Run focused module tests followed by `mvn -B clean install --file pom.xml`.
- Build the Maven builder and runtime images and confirm the produced artifacts run on Java 25.
- Smoke-test `docker-compose-prod.yml` for startup/readiness, discovery/configuration, gateway routing, persistence, and the order/payment workflow.