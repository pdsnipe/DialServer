/**
 * Created with IntelliJ IDEA.
 * User: pd_snipe
 * Date: 11/18/13
 * Time: 4:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MSearchResponse {

    private String location = "LOCATION: ";

    static final String ST = "ST: urn:dial-multiscreen-org:service:dial:1";


    public MSearchResponse(String loc)
    {
        location += loc;

    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        content.append(SSDP.SL_OK).append(SSDP.NEWLINE);
        content.append(location).append(SSDP.NEWLINE);
        content.append(SSDP.CACHE).append(SSDP.NEWLINE);
        content.append(SSDP.EXIT).append(SSDP.NEWLINE);
        content.append(SSDP.BOOTID).append(SSDP.NEWLINE);
        content.append(SSDP.SERVER).append(SSDP.NEWLINE);
        content.append(ST).append(SSDP.NEWLINE);
        content.append(SSDP.NEWLINE);

        return content.toString();
    }
}
