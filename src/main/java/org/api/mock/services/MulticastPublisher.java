package org.api.mock.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;

@Service
public class MulticastPublisher {

    @Value("${multicast.ip}")
    private String multicastIp;
    @Value("${multicast.port}")
    private int multicastPort;

    private static final Logger LOG = LoggerFactory.getLogger(MulticastPublisher.class);

    public void multicast(String multicastMessage) {
        LOG.info("Send : {}", multicastMessage);
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(multicastIp);
            byte[] buf = multicastMessage.getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, multicastPort);
            socket.send(packet);
        } catch (SocketException | UnknownHostException e) {
            LOG.error("Connexion Fail on multicast socket", e);
        } catch (IOException e) {
            LOG.error("Error when sending multicast message", e);
        }

    }
}