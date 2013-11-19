/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class httpServer {

    private HttpServer server;

    public httpServer() throws IOException{
        server = HttpServer.create(new InetSocketAddress(52235), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
