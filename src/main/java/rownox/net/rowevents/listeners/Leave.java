package rownox.net.rowevents.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rownox.net.rowevents.events.Event;
import rownox.net.rowevents.players.PlayerWrapper;

public class Leave implements Listener {
    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PlayerWrapper pw = PlayerWrapper.getWrapper(p);
        if (pw.getCurrentEvent() != null) {
            Event event = pw.getCurrentEvent();
            event.getParticipantList().remove(p.getUniqueId());
            event.updatedStatus();
        }
    }
}
