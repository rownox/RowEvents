package rownox.net.rowevents.events;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rownox.net.rowevents.files.EventConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Event {

    public static List<Event> events;

    private final String eventName;
    private final List<UUID> participantList;
    private final List<UUID> livingList;
    private final EventConfig eventConfig;
    private boolean inProgress;

    public Event(String eventName, List<UUID> livingList, EventConfig eventConfig) {
        this.eventName = eventName;
        this.livingList = livingList;
        this.participantList = new ArrayList<>();
        this.eventConfig = eventConfig;
        this.inProgress = false;
        events.add(this);

        TextComponent component = new TextComponent(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "CLICK HERE" + ChatColor.GREEN + "to participate");
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to join the event").create()));
        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.RUN_COMMAND, "/events join " + eventName));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(component);
        }
    }

    private void begin() {
        participantList.forEach(playerID -> {
            Player p = Bukkit.getPlayer(playerID);
            kitPlayer(p);
        });
    }

    private void end() {

    }

    private void updatedStatus() {
        if (livingList.size() <= 1) {
            final Player winner = Bukkit.getPlayer(livingList.get(0));

            if (winner != null) {
                Bukkit.broadcastMessage("\n" + ChatColor.GREEN + "" + ChatColor.BOLD + "Event Concluded\n" +
                        ChatColor.YELLOW + "Winner: " + ChatColor.GOLD + winner.getName() + "\n"
                );
            } else {
                Bukkit.broadcastMessage("\n" + ChatColor.GREEN + "" + ChatColor.BOLD + "Event Concluded\n");
            }

            end();
        }
    }

    private void kitPlayer(Player p) {

        for (String itemString : eventConfig.getItemList()) {
            Material itemMaterial = Material.matchMaterial(itemString);
            if (itemMaterial == null) return;

            ItemStack item = new ItemStack(itemMaterial);
            if (EnchantmentTarget.ARMOR.includes(item)) {
                if (p.getEquipment() == null) return;

                switch (itemMaterial) {
                    case DIAMOND_HELMET:
                        p.getEquipment().setHelmet(item);
                        break;
                    case DIAMOND_CHESTPLATE:
                        p.getEquipment().setChestplate(item);
                        break;
                    case DIAMOND_LEGGINGS:
                        p.getEquipment().setLeggings(item);
                        break;
                    case DIAMOND_BOOTS:
                        p.getEquipment().setBoots(item);
                        break;
                    default:
                        break;
                }
            } else {
                p.getInventory().addItem(item);
            }
        }

        for (String command : eventConfig.getCommandList()) {
            if (command.contains("<player>")) {
                Bukkit.getLogger().info(command.replace("<player>", p.getName()));
            }
        }
    }
}
