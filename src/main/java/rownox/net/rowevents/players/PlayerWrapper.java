package rownox.net.rowevents.players;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import rownox.net.rowevents.events.Event;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class PlayerWrapper {

    public static HashMap<UUID, PlayerWrapper> wrappers;

    private Event currentEvent;

    private Location posBeforeEvent;

    public PlayerWrapper(UUID id) {
        wrappers.put(id, this);
    }

    public static PlayerWrapper getWrapper(UUID id) {
        return wrappers.get(id);
    }

    public static PlayerWrapper getWrapper(Player p) {
        return wrappers.get(p.getUniqueId());
    }
}
