package br.com.reciclamais.desktop;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

final class ReciclaMaisApiClient {

    private static final String BASE_URL = "http://127.0.0.1:8080";
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();
    private final ObjectMapper json = new ObjectMapper();

    JsonNode get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + path))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        return send(request);
    }

    JsonNode post(String path, Object body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + path))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.writeValueAsString(body)))
                .build();
        return send(request);
    }

    private JsonNode send(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode body = response.body().isBlank() ? json.createObjectNode() : json.readTree(response.body());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            String message = body.path("erro").asText("A solicitação falhou (HTTP " + response.statusCode() + ").");
            throw new ApiException(message);
        }
        return body;
    }

    static final class ApiException extends IOException {
        ApiException(String message) {
            super(message);
        }
    }
}
