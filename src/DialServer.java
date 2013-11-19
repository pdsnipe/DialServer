/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 1:59 AM
 * To change this template use File | Settings | File Templates.
 */


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.DatagramPacket;



public class DialServer implements Runnable
{

    SocketAddress mSSDPMulticastGroup;
    MulticastSocket mSSDPSocket;

    public DialServer() throws IOException {
        InetAddress localInAddress = InetAddress.getLocalHost();
        System.out.println("Local address: " + localInAddress.getHostAddress());

        mSSDPMulticastGroup = new InetSocketAddress(SSDP.ADDRESS, SSDP.PORT);
        mSSDPSocket = new MulticastSocket(1900);

        NetworkInterface netIf = NetworkInterface
                .getByInetAddress(localInAddress);
        mSSDPSocket.joinGroup(mSSDPMulticastGroup, netIf);
    }

    public DatagramPacket receive() throws IOException {
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        mSSDPSocket.receive(dp);

        return dp;
    }

    public void sendResponse() {

    }

    public Boolean analyzeDatagramForDial(DatagramPacket datagramPacket) {
        if(datagramPacket.getData().toString().contains("urn:dial-multiscreen-org:service:dial:1"))
            return true;
        return false;
    }

    public void close() {
        if (mSSDPSocket != null) {
            mSSDPSocket.close();
        }
    }

    @Override
    public void run() {
        while(true) {
            try{
                DatagramPacket dp = this.receive();
                if(analyzeDatagramForDial(dp)) {

                }
            }catch (IOException e){
                System.out.println("IO exception");
            }

        }
    }
}


