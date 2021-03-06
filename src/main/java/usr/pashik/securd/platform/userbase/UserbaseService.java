package usr.pashik.securd.platform.userbase;

import usr.pashik.securd.platform.userbase.provider.UserbaseProvider;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

/**
 * Created by pashik on 10.03.14 17:48.
 */
@ApplicationScoped
public class UserbaseService {
    Map<String, UserInfo> userInfoMap = new HashMap<>();

    SortedSet<UserbaseProvider> providers = new TreeSet<>();

    public UserInfo getUserInfo(String id) {
        return userInfoMap.get(id);
    }

    public void reFetchAllUsers() {
        userInfoMap.clear();
        for (UserbaseProvider provider : providers) {
            reFetchUsers(provider);
        }
    }

    public boolean updateUser(UserInfo userInfo) {
        for (UserbaseProvider provider : providers) {
            if (provider.haveUser(userInfo)) {
                provider.updateUser(userInfo);
                return true;
            }
        }
        return false;
    }

    public void registerProvider(UserbaseProvider userbaseProvider) {
        providers.add(userbaseProvider);
    }

    public void reFetchUsers(UserbaseProvider provider) {
        userInfoMap.putAll(provider.reFetchAllUsers());
    }

    public Collection<UserInfo> getRegisteredUsers() {
        return userInfoMap.values();
    }

    public void initialize() {
        reFetchAllUsers();
    }
}
