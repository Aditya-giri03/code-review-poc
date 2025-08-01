import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Test {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Create context handlers for different endpoints
        server.createContext("/", new RootHandler());
        server.createContext("/api/data", new ApiHandler());

        // Set the executor (null means default executor)
        server.setExecutor(null);

        // Start the server
        server.start();
        System.out.println("Server started on port " + PORT);
        System.out.println("Available endpoints:");
        System.out.println("- GET/POST http://localhost:" + PORT + "/");
        System.out.println("- GET/POST http://localhost:" + PORT + "/api/data");
        System.out.println("Press Ctrl+C to stop the server");
    }

    // Handler for root endpoint "/"
    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response;

            if ("GET".equals(method)) {
                response = "Hello! This is a GET response from the root endpoint.";
            } else if ("POST".equals(method)) {
                // Read POST data
                String postData = readRequestBody(exchange);
                response = "Hello! This is a POST response. You sent: " + postData;
            } else {
                response = "Method " + method + " not supported";
                exchange.sendResponseHeaders(405, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            // Send response
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Handler for API endpoint "/api/data"
    static class ApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response;

            if ("GET".equals(method)) {
                // Return JSON-like response for GET
                response = "{\"message\": \"Data retrieved successfully\", \"method\": \"GET\", \"timestamp\": "
                        + System.currentTimeMillis() + "}";
            } else if ("POST".equals(method)) {
                // Read POST data and return it in response
                String postData = readRequestBody(exchange);
                response = "{\"message\": \"Data received successfully\", \"method\": \"POST\", \"receivedData\": \""
                        + postData + "\", \"timestamp\": " + System.currentTimeMillis() + "}";
            } else {
                response = "{\"error\": \"Method " + method + " not supported\"}";
                exchange.sendResponseHeaders(405, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            // Set content type for JSON response
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Helper method to read request body
    private static String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
`