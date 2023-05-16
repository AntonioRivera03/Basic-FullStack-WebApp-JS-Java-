package org.example;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiscordBot extends ListenerAdapter {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static config config = new config();


    public static void main(String[] args)  {
        String apiToken = config.API_token;
        //The main method creates the JDA object "bot". This object is used to apply the api key. As well as assign the discord
        //bot with any intents such as managing messages, calls, or anything else.
        JDA bot = JDABuilder.createDefault(apiToken)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new DiscordBot())
                .setActivity(Activity.listening("messages"))
                .build();
        config.conf();
    }
    //the onMessageReceived method acts as the hub in which all methods and other classes respond to.
    /*
    This method:
        - Takes in a message sent as event
        - Brakes down the message into core components such as author and content
        - Creates the DBConnection object
        - Checks if the content contains the users specified @
        - If it does, then it prints the user and its message
        - Finally, the message's username and content are sent to query creation in the DBConnection class
          and then pushed to the DBConnect method in DBconnection to be written to the database
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String userName = (event.getAuthor()).getName();
        //This creates userName, which gets the name of the user by ID
        String guild = (event.getGuild().getName());
        String time = getTime();
        String message = (event.getMessage()).getContentDisplay();
        //This takes the event message and grabs the message content.
        String userID = (event.getAuthor()).getId();
        String pfp = (event.getAuthor().getAvatarUrl());


        if(checkContent(message)){
            System.out.printf("Author Name : %s \nMessage Content : %s%n", userName, message);
            //Prints the message data
            try {
                DBConnection con = new DBConnection();
                con.uniqueUser(userID, userName, pfp);
                //This calls to the database write method in DBConnection and fills out the message and username portion.
                System.out.println(con.dbWrite(userID, userName, guild, time, message));
                //This prints complete if everything works and the SQL statement has inserted the row of data into the db.

            } catch (SQLException e) {System.out.println(e);}
        }

    }



    public boolean checkContent(String msg){
        String user = config.Searched_user;
        /*Currently, this looks for any reference of @Cairo independently. If found, then it returns true.
        Discord automatically adds a space after a mention. However, someone can manually remove the space which would
        make it invisible to the code. Something that needs to be fixed.
         */

        String[] msgSplit = msg.split(" ");
        for(String s : msgSplit){
            if (s.equals(user)){return true;}
        }
        return false;
    }

    public String getTime(){
        LocalDateTime now = LocalDateTime.now();
        String dateNow = dateFormat.format(now);
        LocalDate currentDate = LocalDate.parse(dateNow);

        String completeDate;

        int day = currentDate.getDayOfMonth();
        String month = String.valueOf(currentDate.getMonth());
        int year = currentDate.getYear();

        completeDate = String.format("%s %d, %d", month, day, year);

        return completeDate;
    }
}
