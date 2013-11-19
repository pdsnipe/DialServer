/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 4:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class SSDP
{
    public static final String NEWLINE = "\r\n";

    public static final String ADDRESS = "239.255.255.250";
    public static final int PORT = 1900;

    /* Definitions of start line */
    public static final String SL_NOTIFY = "NOTIFY * HTTP/1.1";
    public static final String SL_MSEARCH = "M-SEARCH * HTTP/1.1";
    public static final String SL_OK = "HTTP/1.1 200 OK";

    /* Definitions of search targets */
    public static final String ST_RootDevice = "ST:rootdevice";
    public static final String ST_ContentDirectory = "ST:urn:schemas-upnp- org:service:ContentDirectory:1";

    /* Definitions of notification sub type */
    public static final String NTS_ALIVE = "NTS:ssdp:alive";
    public static final String NTS_BYE = "NTS:ssdp:byebye";
    public static final String NTS_UPDATE = "NTS:ssdp:update";

    public static final String CACHE = "CACHE-CONTROL:  max-age=1800";
    public static final String EXIT = "EXIT:";
    public static final String BOOTID = "BOOTID.UPNP.ORG: 1";
    public static final String SERVER = "SERVER: OS/version UPnP/1.1 product/version";

}
