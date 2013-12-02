/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 4:56 AM
 * To change this template use File | Settings | File Templates.
 */

import nl.stil4m.transmission.rpc.RpcException;

public class Main
{
    public static void main(String[] args) throws RpcException
    {
        try {
            MultiCastServer dialServer = new MultiCastServer();
            TransmissionServer httpServer = new TransmissionServer(52235);
            Thread dial = new Thread(dialServer);
//            Thread http = new Thread(httpServer);
            dialServer.run();

//            TransmissionServer.run():
//            test = new TransmissionServer(52235);
//            test.sendTestPost();
//            sock = new MultiCastServer();
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
