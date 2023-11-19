package rownox.net.rowevents.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rownox.net.rowevents.RowEvents;
import rownox.net.rowevents.players.PlayerWrapper;

public class Join implements Listener {
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new PlayerWrapper(p.getUniqueId());
    }
}
