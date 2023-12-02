package rownox.net.rowevents.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener {

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player victim)) return;

        if (e instanceof EntityDamageByEntityEvent dbe) {
            if (dbe.getDamager() instanceof Player damager) {
                if (victim.getHealth() <= 1) {
                    e.setCancelled(true);
                    victim.setGameMode(GameMode.SPECTATOR);
                }
            }
        }

    }

}
