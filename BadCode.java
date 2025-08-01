import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

// Bad class name - not descriptive
public class BadCode {
    // Bad: public static variables, magic numbers, poor naming
    public static int p = 8080;
    public static String[] responses = new String[100];
    public static int counter = 0;
    public static List<String> logs = new ArrayList<>();

    // Bad: throws generic Exception instead of specific ones
    public static void main(String[] args) throws Exception {
        // Bad: creating server without proper error handling
        HttpServer s = HttpServer.create(new InetSocketAddress(p), 0);

        // Bad: inline anonymous classes instead of proper separation
        s.createContext("/", new HttpHandler() {
            public void handle(HttpExchange e) throws IOException {
                processRequest(e);
            }
        });

        s.createContext("/data", new HttpHandler() {
            public void handle(HttpExchange e) throws IOException {
                handleData(e);
            }
        });

        s.setExecutor(null);
        s.start();

        // Bad: no logging framework, just sysout
        System.out.println("Server running on " + p);
    }

    // Bad: method doing too many things, poor naming, no access modifiers
    static void processRequest(HttpExchange exchange) throws IOException {
        // Bad: variable names are not descriptive
        String m = exchange.getRequestMethod();
        String resp = "";

        // Bad: using loops instead of Stream API for simple operations
        List<String> validMethods = Arrays.asList("GET", "POST", "PUT", "DELETE");
        boolean isValid = false;
        for (int i = 0; i < validMethods.size(); i++) {
            if (validMethods.get(i).equals(m)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            resp = "Bad method";
            sendResponse(exchange, resp, 405);
            return;
        }

        // Bad: nested if-else instead of switch or strategy pattern
        if (m.equals("GET")) {
            resp = "GET response";
            // Bad: direct string concatenation in loop
            for (int i = 0; i < 5; i++) {
                resp = resp + " extra" + i;
            }
        } else if (m.equals("POST")) {
            // Bad: reading body without proper validation
            String body = readBody(exchange);
            resp = "POST: " + body;

            // Bad: storing in static array without bounds checking
            if (counter < responses.length) {
                responses[counter] = resp;
                counter++;
            }
        } else if (m.equals("PUT")) {
            resp = "PUT not implemented properly";
        } else {
            resp = "DELETE danger zone";
        }

        // Bad: logging to static list without synchronization
        logs.add(new Date() + ": " + m + " request processed");

        sendResponse(exchange, resp, 200);
    }

    // Bad: method name doesn't follow convention, poor error handling
    static void handleData(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        // Bad: using StringBuilder unnecessarily for simple concatenation
        StringBuilder sb = new StringBuilder();

        // Bad: hardcoded values, magic strings
        String[] dataItems = { "item1", "item2", "item3", "item4", "item5" };

        // Bad: inefficient loop instead of using Collections or Streams
        if (method.equals("GET")) {
            sb.append("{\"data\":[");
            for (int i = 0; i < dataItems.length; i++) {
                sb.append("\"" + dataItems[i] + "\"");
                if (i < dataItems.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("]}");
            response = sb.toString();
        } else {
            // Bad: poor JSON handling
            response = "{\"error\":\"only GET allowed\"}";
        }

        // Bad: not setting proper content type headers
        sendResponse(exchange, response, 200);
    }

    // Bad: method name abbreviation, no validation
    static String readBody(HttpExchange e) throws IOException {
        InputStream is = e.getRequestBody();
        // Bad: reading entire stream without size limits
        byte[] bytes = is.readAllBytes();
        String result = new String(bytes);

        // Bad: unnecessary processing
        if (result.length() > 0) {
            // Bad: modifying input without clear purpose
            result = result.trim().toLowerCase();
        }

        return result;
    }

    // Bad: method doing multiple responsibilities
    static void sendResponse(HttpExchange exchange, String resp, int code) throws IOException {
        // Bad: no input validation
        byte[] responseBytes = resp.getBytes();

        // Bad: not properly handling different status codes
        exchange.sendResponseHeaders(code, responseBytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        // Bad: not using try-with-resources
        os.close();

        // Bad: side effect in utility method
        System.out.println("Response sent: " + resp.substring(0, Math.min(20, resp.length())));
    }

    // Bad: unused method with poor implementation
    public static void cleanup() {
        // Bad: manual cleanup instead of proper resource management
        for (int i = 0; i < responses.length; i++) {
            responses[i] = null;
        }
        logs.clear();
        counter = 0;
    }
}
