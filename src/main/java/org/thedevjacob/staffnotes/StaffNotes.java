package org.thedevjacob.staffnotes;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class StaffNotes extends JavaPlugin {

    private File noteFile;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Retrieve or create notes file
        noteFile = new File(getDataFolder(), "playerNotes.json");
        if (!noteFile.exists()) saveResource(noteFile.getName(), false);

        this.getCommand("note").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }


}
