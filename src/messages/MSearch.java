package messages;

/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 12/1/13
 * Time: 8:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class MSearch {


    static final String ST = "ST: urn:dial-multiscreen-org:service:dial:1";


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        content.append(SSDP.SL_MSEARCH).append(SSDP.NEWLINE);
        content.append("HOST: 239.255.255.250:1900").append(SSDP.NEWLINE);
        content.append(SSDP.ST_DISCOVER).append(SSDP.NEWLINE);
        content.append(SSDP.SERVER).append(SSDP.NEWLINE);
        content.append(ST).append(SSDP.NEWLINE);
        content.append(SSDP.NEWLINE);

        return content.toString();
    }
}
