[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

<a href="https://muratcanyeldan.dev" target="_blank">
    <img src="https://img.shields.io/badge/My_Website-muratcanyeldan.dev-2ea44f?style=for-the-badge&logo=firefox&logoColor=white" alt="mywebsite logo"  />
</a>

[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/muratcanyeldan/)
&nbsp;
[![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/muratcanyeldan)
&nbsp;

# My Related Articles on Medium

[![Medium](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white) How to Create a Slack Bot with Spring Boot](https://muratcanyeldan.com/how-to-create-a-slack-bot-with-spring-boot-bc7ed3243e9c)


# Redis Cache Manager Slack Bot

A Spring Boot application that provides a Slack bot interface for managing Redis cache directly from Slack channels. The bot allows users to perform operations like GET, SET, DELETE, and LIST ALL Redis keys through simple Slack commands.

## Features

- **Command Processing**: Process Slack commands to interact with Redis
- **Redis Operations**: Perform key-value operations on Redis cache
- **Scheduled Polling**: Periodically check for new commands in Slack
- **Containerized Deployment**: Docker and Docker Compose setup for easy deployment
- **Configurable Settings**: Environment variable configuration for different deployments

## Technology Stack

- **Java 21**
- **Spring Boot**
- **Slack API Client**
- **Redis**
- **Docker & Docker Compose**
- **Maven**

## Prerequisites

- Java 21 or higher
- Maven
- Docker and Docker Compose (for containerized deployment)
- Redis instance (or use the provided Docker Compose setup)
- Slack workspace with bot token

## Installation and Deployment

### Docker Deployment

1. Build and run using Docker Compose:
   ```
   docker compose up -d
   ```

2. View logs:
   ```
   docker compose logs -f app
   ```

3. Stop the application:
   ```
   docker compose down
   ```

## Usage

The bot recognizes the following commands in the configured Slack channel:

### Redis Operations

- **GET key**
  - Retrieves the value stored for the specified key
  - Example: `GET user_123`

- **SET key value**
  - Stores a value with the specified key (permanent storage)
  - Example: `SET user_123 "John Doe"`

- **SET key value ttl**
  - Stores a value with the specified key and time-to-live in seconds
  - Example: `SET temp_key "temporary value" 3600` (expires in 1 hour)

- **DELETE key**
  - Removes the specified key from the cache
  - Example: `DELETE user_123`

- **LIST ALL**
  - Shows all currently stored keys in the cache
  - Example: `LIST ALL`

### Utility Commands

- **HELP**
  - Displays the help message with available commands
  - Example: `HELP`

- **PING**
  - Checks if the bot is active
  - Example: `PING`

## Architecture

The application follows a strategy pattern for handling different commands:

- **SlackService**: Handles communication with the Slack API
- **SlackCommand**: Routes commands to appropriate strategy implementations
- **Command Strategies**: Implement specific Redis operations (GET, SET, DELETE, LIST ALL)
- **Scheduled Job**: Polls the Slack channel for new messages every 5 seconds

## Troubleshooting

- **Bot not responding**: Ensure the bot token and channel ID are correct
- **Redis connection issues**: Check Redis host and port configuration
- **Command not recognized**: Make sure you're using the exact command format
