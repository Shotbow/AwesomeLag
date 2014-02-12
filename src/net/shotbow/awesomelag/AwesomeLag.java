package net.shotbow.awesomelag;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Austin
 * Date: 2/12/14
 * Time: 1:09 PM
 */
public class AwesomeLag extends JavaPlugin implements Listener {
    private Pattern lag = Pattern.compile("((?<= )(?i)(?:l|1|7)+(?:\\W|_)*(?:a|4)+(?:\\W|_)*g+(?:\\W|_)*(?:y|i|n|g|\\W)*|(?<!.)(?i)l+(?:\\W|_)*(?:a|4|)+(?:\\W|_)*g+(?:\\W|_)*(?:y|i|n|g|\\W)*)");
    private Pattern caps = Pattern.compile("^[A-Z]{2,}");
    private Random random = new Random();

    private List<String> words = new ArrayList<String>(){{
        add("awesome");
        add("fun");
        add("incredible");
        add("amazing");
        add("cool");
        add("wow");
    }};

    public void onEnable()
    {
        Bukkit.getPluginManager().registerEvents(this, this);
        initConfig();
    }

    private void initConfig() {
        String config_key = "positiveWords";
        FileConfiguration config = getConfig();
        config.addDefault(config_key,words);
        config.options().copyDefaults(true);
        saveConfig();
        words = getConfig().getStringList(config_key);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        Matcher matcher = lag.matcher(e.getMessage());
        if(matcher.find())
        {
            boolean allCaps = caps.matcher(matcher.group(1)).matches();
            e.setMessage(matcher.replaceAll(getRandomPositiveWord(allCaps)));
        }
    }

    private String getRandomPositiveWord(boolean caps) {
        String word = words.get(random.nextInt(words.size()));
        if(caps)
            word = word.toUpperCase();
        return word;
    }
}
