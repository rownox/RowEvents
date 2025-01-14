package rownox.net.rowevents.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rownox.net.rowevents.events.Event;
import rownox.net.rowevents.files.EventConfig;
import rownox.net.rowevents.players.PlayerWrapper;
import world.ntdi.nrcore.utils.ArmorManager;

import java.util.ArrayList;
import java.util.List;

public class EventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player p)) {
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!p.hasPermission("RowEvents.host")) return true;
            if (args.length < 2) return false;
            String type = args[1];

            List<String> eventKeys = new ArrayList<>();
            for (EventConfig eventConfig : EventConfig.eventConfigList) {
                eventKeys.add(eventConfig.getKey());
            }

            if (!eventKeys.contains(type)) {
                p.sendMessage(ChatColor.RED + "Invalid event name.");
                sendEventList(p);
            }

            String eventName = type + "_" + p.getName();

            new Event(p.getUniqueId(), eventName, EventConfig.getConfig(type));

            p.sendMessage(ChatColor.GREEN + "You've successfully created an event. Name: " + ChatColor.DARK_GREEN + eventName);

        } else if (args[0].equalsIgnoreCase("join")) {
            PlayerWrapper pw = PlayerWrapper.getWrapper(p.getUniqueId());
            if (pw == null) return true;

            if (pw.getCurrentEvent() != null) {
                p.sendMessage(ChatColor.RED + "You are already in an event.");
                return true;
            }

            String eventName = args[1];
            Location position = p.getLocation();
            pw.setPosBeforeEvent(position);

            ArmorManager.storeAndClearInventory(p);

            for (Event event : Event.events) {
                if (event.getEventName().equalsIgnoreCase(eventName)) {
                    event.getParticipantList().add(p.getUniqueId());
                }
            }

        } else if (args[0].equalsIgnoreCase("begin")) {
            sendEventList(p);
        } else if (args[0].equalsIgnoreCase("list")) {
            sendEventList(p);
        }

        return false;
    }

    private void sendEventList(Player p) {
        p.sendMessage(ChatColor.YELLOW + "List of events:");
        for (Event event : Event.events) {
            p.sendMessage(ChatColor.GOLD + "- " + event.getEventName());
        }
    }
}
