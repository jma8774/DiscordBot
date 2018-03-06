package Botters.JavaDiscordBot;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class App extends ListenerAdapter {
    public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
    	JDA bot = new JDABuilder(AccountType.BOT).setToken("NDIwMzk1OTM4MjcyNjQxMDM0.DX-KZA.li9zGD_02OWPZonDNt9mnq11nSU").buildBlocking();
    	bot.addEventListener(new App());
    	
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
//    	Message msg = e.getMessage();
//    	MessageChannel c = e.getChannel();
//    	User u = e.getAuthor();
//    	brianStuff(e);
    	checkCurses(e);
		checkGreets(e);
    	
    }
    
    private void brianStuff(MessageReceivedEvent e) {
		// TODO Auto-generated method stub
     if(!e.getAuthor().isBot())	{
    	if(e.getMessage().getContentDisplay().equals("test")) {
    		e.getChannel().sendMessage("hello").queue();
    		}
    	
    	if(e.getMessage().getContentDisplay().equals("test1")) {
    		e.getChannel().sendMessage("test2 " + e.getAuthor().getAsMention()).queue();
    	}
    	
    }
    			
    }	
	

    public void checkCurses(MessageReceivedEvent e) {
    	String[] swears = {"arse", "ass", "asshole", "bastard", "bitch", "crap", "cunt", "fuck", "nigga","nigger","shit","son of a bitch","faggot"};
    	if(!e.getAuthor().isBot()) { // if not a bot
        	for(int i = 0; i < swears.length; i ++) { // check for all possible greetings
    			if(getMessage(e).toLowerCase().indexOf(swears[i]) > -1) {
    				sendMessage(e, e.getAuthor().getAsMention() + " watch your mouth, I'll let you off the hook for now until further implementation.");
    				break;
    			}
        				
        	}
        	}
    }
    
	public void checkGreets(MessageReceivedEvent e) {
		String[] greetings = {"Hello", "Hi", "Greeting", "Konichiwa", "Howdy", "Ni hao", "Hola", "Sup", "Yo", "Anyoung haseyo"};
    	if(!e.getAuthor().isBot()) { // if not a bot
    	for(int i = 0; i < greetings.length; i ++) { // check for all possible greetings
			if(getMessage(e).toLowerCase().indexOf(greetings[i].toLowerCase()) > -1) {
				sendMessage(e, greetings[(int) (Math.random()*greetings.length)] + " " + e.getAuthor().getAsMention() + "!");
				break;
			}
    				
    	}
    	}
    }
	
	public String getMessage(MessageReceivedEvent e) {
        return e.getMessage().getContentDisplay();
    }

    public void sendMessage(MessageReceivedEvent e, String s) {
        e.getChannel().sendMessage(s).queue();
    }
}
