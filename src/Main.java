/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 4:56 AM
 * To change this template use File | Settings | File Templates.
 */

import java.net.DatagramPacket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Collections;
import java.net.SocketException;
import java.net.InetAddress;
import java.io.IOException;

import nl.stil4m.transmission.rpc.RpcException;

public class Main
{
    public static void main(String[] args) throws RpcException
    {
        try {
            DialServer dialServer = new DialServer();
            HTTPServer httpServer = new HTTPServer(52235);
            Thread dial = new Thread(dialServer);
//            Thread http = new Thread(httpServer);
            dialServer.run();

//            HTTPServer.run():
//            test = new HTTPServer(52235);
//            test.sendTestPost();
//            sock = new DialServer();
//            sock.sendMSearch();
//            while (true) {
//
//                DatagramPacket dp = sock.receive();
//                String c = new String(dp.getData());
//                System.out.println(c);
//                sock.sendSearchResponse();
//            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
