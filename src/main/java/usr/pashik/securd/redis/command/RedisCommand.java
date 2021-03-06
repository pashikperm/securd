package usr.pashik.securd.redis.command;

import usr.pashik.securd.platform.protocol.ProtocolCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.protocol.object.RedisObject;

/**
 * Created by pashik on 11.03.14 23:37.
 */
public abstract class RedisCommand implements ProtocolCommand {
    protected RedisCommandMnemonic mnemonic;
    protected RedisCommandType type;
    protected RedisCommandFamily family;
    protected RedisObject raw;

    public RedisCommandMnemonic getMnemonic() {
        return mnemonic;
    }

    public int getId() {
        return mnemonic.ordinal();
    }

    public RedisCommandType getType() {
        return type;
    }

    public RedisCommandFamily getFamily() {
        return family;
    }

    public RedisObject getRaw() {
        return raw;
    }

    public abstract String getPrimaryKey();

    public abstract String getSecondaryKey();

    public abstract RedisObject getArguments();
}
