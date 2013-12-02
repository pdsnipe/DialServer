//make sure you read incoming searches right
//make sure the response is sent correctly  i.e. the ip is right

import messages.MSearch;
import messages.MSearchResponse;
import messages.SSDP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.DatagramPacket;

import java.util.List;
import java.util.Arrays;


public class DialServer implements Runnable
{

    SocketAddress mSSDPMulticastGroup;
    MulticastSocket mSSDPSocket;

    public DialServer() throws IOException {
        InetAddress localInAddress = InetAddress.getLocalHost();

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

    public void sendMSearch() throws IOException {
        byte[] buf = new byte[1024];
        MSearch mSearch = new MSearch();
        InetAddress IPAddress = InetAddress.getByName("239.255.255.250");
        buf = mSearch.toString().getBytes();
        DatagramPacket dp = new DatagramPacket(buf,buf.length,IPAddress, 1900);
        mSSDPSocket.send(dp);

    }

    public void sendSearchResponse() throws IOException {
        byte[] buf = new byte[1024];
        MSearchResponse mresponse = new MSearchResponse("http://"+InetAddress.getLocalHost().getHostAddress()+""+"/transmission");
        InetAddress IPAddress = InetAddress.getByName("239.255.255.250");
        buf = mresponse.toString().getBytes();
        DatagramPacket dp = new DatagramPacket(buf,buf.length,IPAddress, 1900);
        mSSDPSocket.send(dp);

    }

    public Boolean analyzeDatagramForDial(DatagramPacket datagramPacket) {

        String message = new String(datagramPacket.getData());

        if(message.contains("M-SEARCH * HTTP/1.1") && message.contains("ST: urn:dial-multiscreen-org:service:dial:1")) {
            return true;
        }
        return false;
    }

    public void close() {
        if (mSSDPSocket != null) {
            mSSDPSocket.close();
        }
    }

    public Boolean analyzeDatagramForMSeachResponse(DatagramPacket datagramPacket) {
        String message = new String(datagramPacket.getData());

        if(message.contains("HTTP/1.1 200 OK") && message.contains("ST: urn:dial-multiscreen-org:service:dial:1")) {
            return true;
        }
        return false;
    }

    public String getLocationFromDatagram(DatagramPacket datagramPacket) {

        String message = new String(datagramPacket.getData());

        List<String> temp = Arrays.asList(message.split(" "));

        for(String t: temp) {
            System.out.println(t);
        }

        System.out.println(temp.indexOf("LOCATION:"));

        return "";

    }

    @Override
    public void run() {
       // System.out.println("Started");
        try {
            this.sendMSearch();
        }catch (IOException e) {
            System.out.println("Error sending MSearch");
        }
        while(true) {
            try{
                DatagramPacket dp = this.receive();
                if(analyzeDatagramForDial(dp)) {
                    this.sendSearchResponse();
                }
                if(analyzeDatagramForMSeachResponse(dp)) {
                    this.getLocationFromDatagram(dp);
                }

            }catch (IOException e){
                System.out.println("IO exception");
            }

        }
    }
}


