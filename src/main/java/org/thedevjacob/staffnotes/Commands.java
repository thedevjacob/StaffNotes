package org.thedevjacob.staffnotes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    /**
     * A listener for every time a command is run. In our case, we only have the "note" command
     *
     * @param commandSender The CommandSender object of who sent the command
     * @param command The Command object of the command run
     * @param label The first word of the command; the command's name
     * @param args The rest of the arguments passed to the command
     * @return A boolean denoting whether to send the command usage message denoted in plugin.yml
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        /*
            Check if the thing sending the command is an actual Player
            and not the console or a command block
        */
        if (commandSender instanceof Player) {

            // Fetching our staff  player object
            Player player = (Player) commandSender;



            // Fetch our called player ID from name
            String apiURL = "https://api.mojang.com/users/profiles/minecraft/" + args[0];
            InputStreamReader conn = null;
            BufferedReader inpStr = null;
            String playerUUID = "";
            try {
                conn = new InputStreamReader(new URL(apiURL).openStream());
                inpStr = new BufferedReader(conn);

                playerUUID = (((JsonObject)new JsonParser().parse(inpStr)).get("id")).toString()
                        .replaceAll("\"", "");

            } catch (IOException e) {
                player.sendMessage(
                        formatErrorMessage("A Player could not be found with the specified Username.")
                );
                return true;
            }

            player.sendMessage(formatMessage("Player ID: " + playerUUID));

        }

        return true;

    }

    /**
     * Adds a note to a Player
     *
     * @param player The Player to add the note to
     * @param note The note to add to the Player
     * @return A boolean denoting the success of the note addition
     */
    public boolean addNote(Player player, String note) {
        return false;
    }

    /**
     * Removes
     * @param player The Player to add the note to
     * @param index The index of the note to remove from the Player (accessible via `listNotes(Player)`)
     * @return A boolean denoting the success of the note removal
     */
    public boolean removeNote(Player player, int index) {
        return false;
    }

    /**
     * Returns a list of the Player's notes
     *
     * @param player The player to retrieve notes for
     * @return The list of Notes of the Player
     */
    public List<String> listNotes(Player player) {
        return new ArrayList<>();
    }

    /**
     * Returns a formatted version of the Player's notes, ready for output
     *
     * @return The formatted String of Player notes
     */
    public String formatNotes(List<String> notesList){
        StringBuilder output = new StringBuilder();

        return output.toString();
    }

    /**
     * Returns a formatted plugin message including the plugin prefix with a specified chat color
     *
     * @param bracketColor The color of the brackets
     * @param messageColor The color of the message
     * @param message The message to format with colors
     * @return The formatted String
     */
    public String formatMessage(ChatColor bracketColor, ChatColor messageColor, String message) {
        String prefix = bracketColor + "[" + messageColor + "StaffNotes" + bracketColor + "] ";
        String coloredMessage = messageColor + message;

        return prefix + coloredMessage;
    }

    /**
     * If a color is not provided, format the message with a green color
     * Overloads the `formatMessage(ChatColor, ChatColor, message)` method
     *
     * @param message The message to format with colors
     * @return The formatted String
     */
    public String formatMessage(String message){
        return formatMessage(ChatColor.DARK_GREEN, ChatColor.GREEN, message);
    }

    /**
     * Returns a formatted plugin ERROR message, using red color codes
     * Uses the `formatMessage(ChatColor, ChatColor, message)` method
     *
     * @param message The message to format with colors
     * @return The formatted String
     */
    public String formatErrorMessage(String message){
        return formatMessage(ChatColor.DARK_RED, ChatColor.RED, message);
    }

}
