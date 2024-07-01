# Weather Application

This is a Spring Boot application that fetches weather data from the [OpenWeatherMap API](https://openweathermap.org/api) and provides endpoints to access this data. It also includes a health check endpoint.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Testing](#testing)
- [Future Improvements](#future-improvements)

## Installation

### Prerequisites

- Java 17 or later
- [Maven](https://formulae.brew.sh/formula/maven)
- An [OpenWeatherMap](https://openweathermap.org/) account and a working API key

### Steps

1. **Clone the repository:**

    ```bash
    git clone https://github.com/PunitDh/weather-app-spring.git
    cd weather-app-spring
    ```

2. **Build the project:**

    ```bash
    mvn clean install
    ```

3. **Set environment variable:**

    You will need to get a working API key from [OpenWeatherMap](https://openweathermap.org/).
    Before running the application, run this command in the terminal.
    ```shell
    export OPEN_WEATHER_MAP_API_KEY=Your_API_KEY_Here
    ```

4. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

## Usage

Once the application is running, you can access the API at `http://localhost:8080`.

## API Endpoints

### Healthcheck

- **URL:** `/healthcheck`
- **Method:** `GET`
- **Response:**
      200 OK: Returns a simple message indicating the health of the application.

#### Example Request

```bash
curl -X GET 'http://localhost:8080/healthcheck'
```

#### Example Response
```json
{
  "status": "UP"
}
```

### Get Weather Data

- **URL:** `/weather`
- **Method:** `GET`
- **Query Parameters:**
    - `location` (string): The name of the location to get weather data for.
- **Response:**
    - 200 OK: Returns the weather data for the specified location.
    - 400 Bad Request: If the location is not provided or invalid.
    - 500 Internal Server Error: If an error occurs while fetching the data.

#### Example Request

```bash
curl -X GET 'http://localhost:8080/weather?location=Melbourne'
```

#### Example Response
```json
{
  "lat": -37.814,
  "lon": 144.96332,
  "timezone": "Australia/Melbourne",
  "timezone_offset": 36000,
  "current": {
    "temp": 285.47,
    "feels_like": 284.61,
    "pressure": 1016,
    "humidity": 71,
    "dew_point": 280.37,
    "uvi": 0.74,
    "clouds": 100,
    "visibility": 10000,
    "wind_speed": 2.24,
    "wind_deg": 4,
    "weather": [
      {
        "id": 804,
        "main": "Clouds",
        "description": "overcast clouds",
        "icon": "04d"
      }
    ]
  }
}
```

## Configuration
The application can be configured using the `application.yml` file. Below is an example configuration.

```yml
openweathermap:
  base-url: https://api.openweathermap.org
  endpoints:
    geolocation: /geo/1.0/direct
    weather: /data/3.0/onecall
  api-key: ${OPEN_WEATHER_MAP_API_KEY}
```

Before running the application, make sure to set the `OPEN_WEATHER_MAP_API_KEY` as an environment variable. This
can be achieved by running this command in the terminal window:

```shell
export OPEN_WEATHER_MAP_API_KEY=Your_API_KEY_Here
```

## Testing

### Testing Using Postman

To test the application using [Postman](https://www.postman.com/), follow the steps.
1. Download the [postman collection](./Weather_API.postman_collection.json) from the repository
2. Import the collection into Postman by clicking **Edit** > **Import**
3. (Optional) Set the `api_url` collection variable to the local host URL the application is running on (default `localhost:8080`)
4. Test each endpoint

### Unit Tests
To run the unit tests, use the following command:

```bash
mvn test
```

### End-to-End Tests
To run the end-to-end tests, use the following command:

```bash
mvn verify
```

## Future Improvements

- **Caching**: Implement caching to reduce the number of API calls to the OpenWeatherMap service and improve response times.
- **Authentication**: Add user authentication and authorization to secure the API endpoints.
- **Rate Limiting**: Implement rate limiting to prevent abuse and overuse of the API.
- **Improved Error Handling**: Enhance error handling to provide more informative error messages and better handle edge cases.
- **Expand Endpoints**: Add more endpoints to provide additional weather-related data, such as historical weather data, weather alerts, etc.
- **Unit and Integration Tests**: Increase test coverage with additional unit and integration tests.