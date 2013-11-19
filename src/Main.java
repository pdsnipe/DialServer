/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 4:56 AM
 * To change this template use File | Settings | File Templates.
 */

import java.net.DatagramPacket;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
//        DialServer sock;
        httpServer test;
        try {
            test = new httpServer();
//            sock = new DialServer();
//            test.
//            while (true) {
//
//                DatagramPacket dp = sock.receive();
//                String c = new String(dp.getData());
//                System.out.println(c);
//            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
