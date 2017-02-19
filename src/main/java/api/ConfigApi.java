package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.config.Config;
import persist.ConfigOfy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/10/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class ConfigApi {
    Logger logger = Logger.getLogger(ConfigApi.class.getName());

    @ApiMethod(name = "config.save", path = "config_save", httpMethod = "POST")
    public Config create(@Named("groupName") String groupName, @Named("name") String name, @Named("value") String value) {
        List<Config> configList = ConfigOfy.loadByGroupName(groupName.trim());
        Config config;
        Map<String, Object> configMap;
        logger.warning("Group Name = " + groupName);
        logger.warning("name = " + name);
        logger.warning("value = " + value);
        if (configList != null & configList.size() > 0) {
            config = configList.get(0);
            configMap = config.getConfigMap();
            configMap.put(name, value);
        } else {
            configMap = new HashMap<>();
            configMap.put(name, value);
            config = new Config(groupName.trim(), configMap);
        }
        return ConfigOfy.loadByKey(ConfigOfy.save(config));
    }

    @ApiMethod(name = "config.get", path = "config_get", httpMethod = "GET")
    public List<Config> loadByGroupName(@Named("groupName") String groupName) {
        logger.warning("Group = " + groupName);
        return ConfigOfy.loadByGroupName(groupName);
    }
}
