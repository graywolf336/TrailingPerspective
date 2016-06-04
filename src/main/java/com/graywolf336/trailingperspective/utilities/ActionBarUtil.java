package com.graywolf336.trailingperspective.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author fillpant AKA mine-care
 *         <h1>Info:</h1> This is a utility class to overcome the problem that occurs in the latest
 *         versions (1.8.3+) where ChatSerialiser class cannot be found.
 */
public class ActionBarUtil {
    private static final String BV = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    private static boolean initialized = false;
    private static Constructor<?> chatSer;
    private static Constructor<?> packetChat;
    private static Method getPlayerHandle;
    private static Field playerConnection;
    private static Method sendPacket;

    static {
        try {
            chatSer = Class.forName("net.minecraft.server." + BV + ".ChatComponentText").getConstructor(String.class);
            packetChat = Class.forName("net.minecraft.server." + BV + ".PacketPlayOutChat").getConstructor(Class.forName("net.minecraft.server." + BV + ".IChatBaseComponent"), byte.class);
            getPlayerHandle = Class.forName("org.bukkit.craftbukkit." + BV + ".entity.CraftPlayer").getDeclaredMethod("getHandle");
            playerConnection = Class.forName("net.minecraft.server." + BV + ".EntityPlayer").getDeclaredField("playerConnection");
            sendPacket = Class.forName("net.minecraft.server." + BV + ".PlayerConnection").getDeclaredMethod("sendPacket", Class.forName("net.minecraft.server." + BV + ".Packet"));
            initialized = true;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            Bukkit.getServer().getLogger().warning("Cannot initialise Action Bar Utils (Blame fillpant)");
            initialized = false;
        }
    }

    /**
     * This method checks if the ActionBarUtil has been initialized properly
     * 
     * @return returns a boolean indicating initialization.
     */
    public static boolean isinitialized() {
        return initialized;
    }

    /**
     * This method sends an ActionBar message to a player, ChatColor works as well.
     * 
     * @param message The message to be sent to the player(s).
     * @param p The player(s) to receive the ActionBar message.
     * @return Returns a boolean indicating if the method execution was successful. <br>
     *         <b>NOTE: If anything goes wrong the utility will automatically stop working to
     *         prevent massive amounts of errors showing up in console.</b>
     */
    public static boolean sendActionBar(String message, Player... p) {
        if (!initialized)
            return false;
        try {
            Object o = chatSer.newInstance(message);
            Object packet = packetChat.newInstance(o, (byte) 2);
            sendPacket(packet, p);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            initialized = false;
        }
        return initialized;
    }

    /** IGNORE THIS! */
    private static void sendPacket(Object packet, Player... pl) throws ReflectiveOperationException {
        for (Player p : pl) {
            Object entityplayer = getPlayerHandle.invoke(p);
            Object PlayerConnection = playerConnection.get(entityplayer);
            sendPacket.invoke(PlayerConnection, packet);
        }
    }
}
