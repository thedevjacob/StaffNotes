package org.thedevjacob.staffnotes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            Player staff = (Player) commandSender;

            // if they are passing in any arguments
            if (args.length>0) {
                OfflinePlayer player = checkUser(args, staff);

                if (player != null) {
                    // If the command usage is 'add', will be specified at index 1
                    // ex: '/note Notch add {Note Contents}'
                    if (args[1].equalsIgnoreCase("add")) {
                        // If there is note content, add the note
                        if (args.length > 2) {
                            addNote(args, player, staff);
                            Note chime = new Note(2, Note.Tone.F, true);
                            staff.playNote(staff.getLocation(), Instrument.CHIME, chime);
                            staff.sendMessage(formatMessage("Note successfully created!"));
                        } else
                            staff.sendMessage(formatErrorMessage("Command usage: /note {Username} add (Note Content)"));
                    }
                    else { staff.sendMessage(formatErrorMessage("Please type '/note help' for a list of commands.")); }
                }

            } else { staff.sendMessage(formatErrorMessage("Please type '/note help' for a list of commands.")); }

        }

        return true;

    }

    /**
     * Adds a note to a Player
     *
     * @param args The arguments passed into the command
     * @param player The player the note is for
     * @param staff The staff member running the command
     * @return A boolean denoting the success of the note addition
     */
    public boolean addNote(String[] args, OfflinePlayer player, Player staff) {

        return false;
    }

    /**
     * Checks whether a user is found with the given username
     * The given username is in the args list at index [0]
     *
     * @param args The arguments given to us by the staff member
     * @param staff A Player object of the staff member sending the command
     * @return A boolean denoting if a user was found with the username
     */
    public OfflinePlayer checkUser(String[] args, Player staff) {
        // The name of the player the note is being added to should be the argument at index 0
        // ex: /note Notch add {Note Content}
        String playerName = args[0];

        // Get the player connected with the playerName
        OfflinePlayer player = Bukkit.getPlayer(playerName);
        if (player == null){
            player = Bukkit.getOfflinePlayer(playerName);
        }

        // Get that player's UUID
        // NOTE: May be the wrong player's UUID, if the playerName is invalid
        UUID playerUUID = player.getUniqueId();

        /* Check if the Username given to us is
         1) a player that has joined the server, and
         2) is the player that they want to add a note to */
        try {
            if (!(playerName.equals(Bukkit.getPlayer(playerUUID).getName()))) {
                staff.sendMessage(
                        formatErrorMessage("A Player could not be found with the specified Username.")
                );
                return null;
            }
        } catch (Exception e) {
            staff.sendMessage(
                    formatErrorMessage("A Player could not be found with the specified Username.")
            );
            return null;
        }

        return Bukkit.getPlayer(playerUUID);
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
