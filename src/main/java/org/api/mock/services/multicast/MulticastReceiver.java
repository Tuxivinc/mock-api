package org.api.mock.services.multicast;

import org.api.mock.model.ExchangeSession;
import org.api.mock.services.helper.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.*;

@Service
public class MulticastReceiver implements Runnable {

    @Value("${multicast.ip}")
    private String multicastIp;
    @Value("${multicast.port}")
    private int multicastPort;

    @Resource
    private MulticastService multicastService;

    public static final String KEY_SYNC = "sync";
    private static final Logger LOG = LoggerFactory.getLogger(MulticastReceiver.class);

    @Override
    public void run() {
        connectReceiver();
    }

    private void connectReceiver() {
        LOG.info("Listen Multicast");
        try (MulticastSocket socket = new MulticastSocket(multicastPort)) {
            byte[] buf = new byte[256];
            SocketAddress group = new InetSocketAddress(multicastIp, multicastPort);
            socket.joinGroup(group, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
            // Send Sync Request
            multicastService.sendSyncRequest();
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                if ("end".equals(received)) {
                    LOG.info("Received end request, shutdown receiver");
                    break;
                } else if (KEY_SYNC.equals(received)) {
                    LOG.debug("Received sync request");
                    multicastService.sendAllOnMulticast();
                } else {
                    LOG.debug("Received: {}", received);
                    ExchangeSession exchangeSession = JsonHelper.getValue(new ExchangeSession(), received);
                    multicastService.setValueInSession(exchangeSession);
                }
            }
            socket.leaveGroup(group, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        } catch (IOException e) {
            LOG.error("Error when connect receiver on multicast virtual IP", e);
        }
    }
}