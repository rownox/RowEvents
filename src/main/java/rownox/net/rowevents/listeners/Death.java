package rownox.net.rowevents.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import rownox.net.rowevents.events.Event;
import rownox.net.rowevents.players.PlayerWrapper;

public class Death implements Listener {

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        if (victim == null) return;
        PlayerWrapper pw = PlayerWrapper.getWrapper(victim);
        Event event = pw.getCurrentEvent();

        if (event != null) {
            event.getParticipantList().remove(victim.getUniqueId());
            pw.setCurrentEvent(null);
            event.updatedStatus();
            victim.spigot().respawn();
        }
    }
}
