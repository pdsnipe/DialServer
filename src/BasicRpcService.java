

import nl.stil4m.transmission.api.domain.*;
import nl.stil4m.transmission.api.torrent.TorrentStatus;
import org.codehaus.jackson.map.ObjectMapper;

import nl.stil4m.transmission.api.TransmissionRpcClient;
import nl.stil4m.transmission.api.domain.ids.OmittedIds;
import nl.stil4m.transmission.rpc.RpcClient;
import nl.stil4m.transmission.rpc.RpcConfiguration;
import nl.stil4m.transmission.rpc.RpcException;

import java.net.URI;



public class BasicRpcService
{

    private TransmissionRpcClient rpcClient;

    public BasicRpcService()
    {
        try
        {
            org.codehaus.jackson.map.ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            RpcConfiguration rpcConfiguration = new RpcConfiguration();
            rpcConfiguration.setHost(URI.create("http://localhost:9091/transmission/rpc"));
            RpcClient client = new RpcClient(rpcConfiguration, objectMapper);
            rpcClient = new TransmissionRpcClient(client);
            TorrentInfoCollection result = rpcClient.getAllTorrentsInfo();
        }catch(RpcException rpc)
        {
            System.out.println("rpc exception");
        }

    }

    public boolean stopDownloads() throws RpcException
    {
        rpcClient.doAction(new OmittedIds(), TorrentAction.STOP);

        TorrentInfoCollection result = rpcClient.getAllTorrentsInfo();
        TorrentInfo torrentInfo = result.getTorrents().get(0);
        torrentInfo = result.getTorrents().get(1);
        return true;
    }

    public boolean resumeDownloads() throws RpcException
    {
        rpcClient.doAction(new OmittedIds(), TorrentAction.START);

        TorrentInfoCollection result = rpcClient.getAllTorrentsInfo();
        TorrentInfo torrentInfo = result.getTorrents().get(0);

        torrentInfo = result.getTorrents().get(0);
        return true;
    }

    public boolean addTorrent(String torrentURI) throws RpcException
    {
        AddTorrentInfo addTorrentInfo = new AddTorrentInfo();
        addTorrentInfo
                .setFilename(torrentURI);
        addTorrentInfo.setPaused(true);
        AddedTorrentInfo result = rpcClient.addTorrent(addTorrentInfo);
        TorrentInfo info = result.getTorrentAdded();
        return true;
    }


}
