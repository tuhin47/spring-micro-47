[![CircleCI](https://dl.circleci.com/status-badge/img/circleci/MgvxEwdpnJ3irCvVZbs4M2/6mxMGr251EXuYKkuQNrzHn/tree/main.svg?style=svg&circle-token=bdaab008db5675ca388793362feaa7f5b9c83c3e)](https://app.circleci.com/pipelines/circleci/MgvxEwdpnJ3irCvVZbs4M2/6mxMGr251EXuYKkuQNrzHn) [![Github CI](https://github.com/tuhin47/spring-micro-47/workflows/Project%20Build/badge.svg)](https://github.com/tuhin47/spring-micro-47)

# Project Summary

This is a microservice-based Spring Boot project aimed at providing a comprehensive solution for managing distributed platform.
The project incorporates the following components:

- **API Gateway**: Serves as a central entry point for accessing the microservices within the system.
- **Eureka**: A service registry and discovery server that enables communication between microservices.
- **Config Service**: A service to manage configuration.
- **Authentication Service**: Handles user authentication and authorization.
- **Product Service**: Manages product-related functionalities, such as retrieval, creation, and updating.
- **Payment Service**: Handles payment processing and integration with third-party payment gateways.
- **Order Service**: Manages the lifecycle of customer orders.
- **MySQL Database**: The relational database used for storing persistent data.
- **Chat Service**: Provides real-time messaging capabilities for users.
- **Angular Service**: A front-end service built using the Angular framework.
- **MongoDB**: A NoSQL database used for storing unstructured data, such as chat messages.
- **Redis**: An in-memory data store used for caching.
- **Zipkin**: A distributed tracing system for monitoring and troubleshooting microservices.
- **Elasticsearch**: A search engine used for indexing and searching data.
- **Logstash**: A data pipeline tool used for collecting, processing, and forwarding logs and metrics.
- **Kibana**: A visualization and exploration tool used for analyzing and visualizing log data.

The project includes YAML files for both production and development environments, allowing easy configuration and deployment in different settings.

In addition, the project follows the Gitflow workflow for managing continuous integration (CI) and versioning. It generates automated GitHub pages for each version.

## Getting Started

For Production follow [this](./command.md)

