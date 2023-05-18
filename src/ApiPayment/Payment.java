package ApiPayment;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

public class Payment {
    public void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(3456), 0);
        System.out.println("Server started at port " + server.getAddress());
        server.createContext("/api/payment", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                System.out.println("Receiving a payment request");
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                try {
                    // Parse the request body as JSON
                    JSONObject requestBody = new JSONObject(body);

                    // Extract the user_id, flight_id, and method parameters from the JSON
                    String userId = requestBody.getString("user_id");
                    String flightId = requestBody.getString("flight_id");
                    String method = requestBody.getString("method");

                    // Process the received parameters
                    System.out.println("User ID: " + userId);
                    System.out.println("Flight ID: " + flightId);
                    System.out.println("Payment Method: " + method);

                    // Return a response if needed
                    String responseText = "Payment received successfully";
                    exchange.sendResponseHeaders(200, responseText.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(responseText.getBytes());
                    output.flush();
                } catch (Exception e) {
                    exchange.sendResponseHeaders(400, -1); // 400 Bad Request
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();

    }
}
