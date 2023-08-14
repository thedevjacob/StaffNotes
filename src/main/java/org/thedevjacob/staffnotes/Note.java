package org.thedevjacob.staffnotes;

import java.time.Clock;
import java.util.UUID;

public class Note {

    /**
     * The UUID of the Player that the note is being added to
     */
    private UUID playerID;

    /**
     * The UUID of the Staff member who created the note
     */
    private UUID staffID;

    /**
     * The actual note being added
     */
    private String note;


    /**
     * The date and time at which the note was added
     */
    private Clock time;

    public Note(UUID playerID, UUID staffID, String note){
        this.playerID = playerID;
        this.staffID = staffID;
        this.note = note;

        this.time = Clock.systemUTC();
    }

}
