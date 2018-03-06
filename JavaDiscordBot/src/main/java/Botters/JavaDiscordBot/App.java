package Botters.JavaDiscordBot;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class App extends ListenerAdapter {
	private boolean stop;
	private final String KAPPA = "382387471423504384";
	static JDA bot;
    public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
    	bot = new JDABuilder(AccountType.BOT).setToken("NDIwMzk1OTM4MjcyNjQxMDM0.DX-KZA.li9zGD_02OWPZonDNt9mnq11nSU").buildBlocking();
    	bot.addEventListener(new App());
//    	begForDonations(1800000, bot); // 30 minutes
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
//    	Message msg = e.getMessage();
//    	MessageChannel c = e.getChannel();
//    	User u = e.getAuthor();
    	brianStuff(e);
    	stop = false;
    	checkCurses(e);
    	if(stop) return;
    	checkHelp(e);
    	checkRoll(e);
    	checkHardstuck(e);
		checkGreets(e);
    }
    




	private void brianStuff(MessageReceivedEvent e)
	{
		// TODO Auto-generated method stub
     if(!e.getAuthor().isBot())	
     {
    	if(e.getMessage().getContentDisplay().equals("test")) {
    		e.getChannel().sendMessage("hello").queue();
    		}
    	
    if(e.getMessage().getContentDisplay().equals("test1")) {
    		e.getChannel().sendMessage("test2 " + e.getAuthor().getAsMention()).queue();
    		}
    {
    	java.util.List<Member> members = e.getMessage().getMentionedMembers();
    
    if(getMessage(e).contains("slap @")) {
    	sendMessage(e, getMention(e) + "slapped " + members.get(0).getAsMention());
    }
    }
    }	}
	
	private void checkHelp(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("help") == 1) {
			sendMessage(e, getMention(e) + "``` " + "Here is a list of my commands:\n" +
					"`help - prompts for this window \n" + 
					"`roll - rolls a 6 sided dice \n" +
					"`hardstuck - someone who is hardstuck in league \n" +
					"```");
		}
	}
	
	private void checkHardstuck(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("hardstuck") == 1) {
			sendMessage(e, bot.getUserById("152954300933472256").getAsMention() + " is hardstuck " + bot.getEmoteById(KAPPA).getAsMention());
		}
	}
	
	
	private void checkRoll(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) return;
		if(e.getMessage().getContentDisplay().startsWith("`") && e.getMessage().getContentDisplay().indexOf("roll") == 1) {
			int roll = (int)(Math.random()*6+1);
			switch(roll) {
				case 0: 
					sendMessage(e, getMention(e) + " rolled a :zero:");
					break;
				case 1:
					sendMessage(e, getMention(e) + " rolled a :one:");
					break;
				case 2:
					sendMessage(e, getMention(e) + " rolled a :two:");
					break;
				case 3:
					sendMessage(e, getMention(e) + " rolled a :three:");
					break;
				case 4:
					sendMessage(e, getMention(e) + " rolled a :four:");
					break;
				case 5:
					sendMessage(e, getMention(e) + " rolled a :five:");
					break;
				case 6:
					sendMessage(e, getMention(e) + " rolled a :six:");
					break;
			}
		}
	}
	
	private static void begForDonations(int ms, final JDA bot) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
		            	bot.getTextChannelById("152954629993398272").sendMessage("If you like this bot, please consider donating RP to the creators, " 
		            	+ bot.getUserById("152957206025863168").getAsMention() + " and " + bot.getUserById("152954180984635392").getAsMention() + ".").queue();
		            }
		        }, 100, ms); // ms = milleseconds until the next message, can alter this in the main function
	}

    public void checkCurses(MessageReceivedEvent e) {
    	String[] swears = {"arse", "ass", "asshole", "bastard", "bitch", "crap", "cunt", "fuck", "nigga","nigger","shit","son of a bitch","faggot"};
    	if(e.getAuthor().isBot()) return;
    	for(int i = 0; i < swears.length; i ++) { // check for all possible greetings
			if(getMessage(e).toLowerCase().indexOf(swears[i]) > -1) {
				sendMessage(e, getMention(e) + ", watch your mouth!");
				stop = true;
				break;
			}
    				
    	}
    }
    
	public void checkGreets(MessageReceivedEvent e) {
		String[] greetings = {"Hello ", "Hi ", "Greeting ", "Konichiwa ", "Howdy ", "Ni hao ", "Hola ", "Sup ", "Yo ", "Anyoung haseyo "};
    	if(e.getAuthor().isBot()) return;
    	for(int i = 0; i < greetings.length; i ++) { // check for all possible greetings
			if(getMessage(e).toLowerCase().indexOf(greetings[i].toLowerCase()) == 0) {
				sendMessage(e, greetings[(int) (Math.random()*greetings.length)] + " " + getMention(e) + "!");
				break;
			}
    				
    	}
    }
	
	public String getMention(MessageReceivedEvent e) {
		return e.getAuthor().getAsMention();
	}
	public String getMessage(MessageReceivedEvent e) {
        return e.getMessage().getContentDisplay();
    }

    public void sendMessage(MessageReceivedEvent e, String s) {
        e.getChannel().sendMessage(s).queue();
    }
}
