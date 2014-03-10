package usr.pashik.securd.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.redis.RedisClient;
import usr.pashik.securd.redis.RedisServer;

import javax.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 13:44.
 */
public class SecurdApplication {
    @Inject
    ConfiguratorService config;

    Logger log = LogManager.getLogger(SecurdRunner.class);

    public void start() throws IOException {
        ServiceActivator.activateServices();

        ServerSocket serverSocket = new ServerSocket(config.getProxyPort());
        log.info(String.format("Start proxy [acceptingPort=%d, serverHost=%s, serverPort=%d]",
                               config.getProxyPort(),
                               config.getServerHost(),
                               config.getServerPort()));

        while (true) {
            Socket clientSocket = serverSocket.accept();
            log.info(String.format("Accepted client [host=%s, localPort=%d]",
                                   clientSocket.getInetAddress(),
                                   clientSocket.getLocalPort()));

            RedisClient client = new RedisClient(clientSocket);
            RedisServer server = new RedisServer();
//            new Thread(new ClientProcessor(client, server)).start();
        }
    }

}