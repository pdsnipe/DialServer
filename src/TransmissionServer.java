
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;

import java.util.HashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import nl.stil4m.transmission.rpc.RpcException;

import java.net.URLDecoder;



public class TransmissionServer {

    private HttpServer server;

    private static BasicRpcService rpcService = new BasicRpcService();

    public void sendTestPost() throws Exception,RpcException {

        String url = "http://localhost:52235/transmission/torrent";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", "Mozilla/5.0");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("add","magnet:?xt=urn:btih:727665E0FE70263CD0B715758C2E8DB9A78554EC&dn=white+house+down+2013+720p+brrip+x264+yify&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337"));
        urlParameters.add(new BasicNameValuePair("add", "magnet:?xt=urn:btih:9df7c1554742bfeaa226faea683541958bbc44cf&dn=Bitcoin+Exposed%2C+Today%27s+Complete+Guide+to+Tomorrow%27s+Currency&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80&tr=udp%3A%2F%2Ftracker.istole.it%3A6969&tr=udp%3A%2F%2Ftracker.ccc.de%3A80&tr=udp%3A%2F%2Fopen.demonii.com%3A1337"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    public TransmissionServer(int port) throws IOException{
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }


    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            System.out.println("Request body: "+t.getRequestMethod());
            if(t.getRequestMethod().equals("POST")) {
                InputStream is = t.getRequestBody();
                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "utf-8");
                String message = writer.toString();
                String[] params = message.split("&");
                HashMap<String,String> postParams = new HashMap<String, String>();
                for(String s: params) {
                    String[] temp = s.split("=");
                    postParams.put(temp[0].replaceAll("\\s+",""),temp[1].replaceAll("\\s+",""));
                }

                if(postParams.containsKey("add")) {
                    try{
                        rpcService.addTorrent(URLDecoder.decode(postParams.get("add")));
                    }catch(RpcException e) {
                        System.out.println(e.toString());
                    }

                }
                if(postParams.containsKey("stop")) {
                    try{
                        rpcService.stopDownloads();
                    }catch(RpcException e) {
                        System.out.println(e.toString());
                    }
                }
                if(postParams.containsKey("resume")) {
                    try{
                        rpcService.resumeDownloads();
                    }catch(RpcException e) {
                        System.out.println(e.toString());
                    }
                }

            }



//            String response = "This is the response";
//            t.sendResponseHeaders(200, response.length());
//            OutputStream os = t.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
        }
    }



}
