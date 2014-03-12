package usr.pashik.securd.proxy.clientprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.connection.ConnectedClientService;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.protocol.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.protocol.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.response.RedisObject;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by pashik on 06.03.14 23:47.
 */
public class TransparentClientProcessor extends ClientProcessor {
    @Inject
    ConfiguratorService config;
    @Inject
    ConnectedClientService clientService;

    RedisChannel client;
    RedisChannel server;

    Logger log = LogManager.getLogger(TransparentClientProcessor.class);


    TransparentClientProcessor() {
    }

    @Override
    public void runInjected() {
        ConnectedClient connectedClient = clientService.registerConnection(client.getSocket());
        try {
            while (true) {
                RedisCommand command = client.readCommand();
                log.info(command);
                server.sendCommand(command);
                RedisObject response = server.readResponse();
                client.sendResponse(response);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            clientService.unregisterConnection(connectedClient);
        }
    }
}
