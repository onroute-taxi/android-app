package com.enamakel.backseattester.data.models;


import com.enamakel.backseattester.data.models.base.BaseModel;
import com.enamakel.backseattester.data.models.media.MediaModel;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class SessionModel extends BaseModel {
    @Expose @Inject TabletModel tablet;
    @Expose @Inject PassengerModel passenger;
    @Expose JourneyModel journey;
    @Expose List<Command> commands;
    @Expose List<MediaModel> movies;


    public SessionModel() {
        commands = new ArrayList<Command>();
    }


    /**
     * This function is used to update the session! Because we want to preserve some of the
     * variables, we use this special function to make sure that we update only the necessary
     * fields.
     *
     * @param source The source session to update from. (Ideally the session object from the server)
     */
    public void update(SessionModel source) {
        setPassenger(source.getPassenger());
        setJourney(source.getJourney());
        setCommands(source.getCommands());
        setMovies(source.getMovies());
    }


    /**
     * This class represents a commands. Commands are instructions sent from the server to execute a
     * certain action. Actions can be anything not relating to the data in the session. Like
     * updating the Movies list, Showing a notification, Taking a photo etc..
     * <p/>
     * Each command has an opcode which identifies the command, and a JSON string which provides
     * extra data for that command.
     */
    public class Command {
        @Expose @Getter @Setter String opcode;
        @Expose @Getter @Setter String json;
        @Getter @Setter boolean done = false;
    }


    public List<Command> getCommands() {
        List<Command> unfinished_commands = new ArrayList();

        // Itearate through all the commands and add the unfinished ones
        for (Command command : commands) if (!command.isDone()) unfinished_commands.add(command);

        return unfinished_commands;
    }
}
