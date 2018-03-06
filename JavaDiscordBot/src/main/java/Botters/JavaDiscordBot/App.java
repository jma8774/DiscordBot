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
    	Message msg = e.getMessage();
    	MessageChannel c = e.getChannel();
    	User u = e.getAuthor();
    	
    	if(msg.getContentDisplay().equals("hello")) c.sendMessage("Hello, " + u.getAsMention() + "!").queue();
    	if(msg.getContentDisplay().indexOf("boom") > -1 && !u.isBot()) c.sendMessage(u.getAsMention() + " stfu, boom your mom.").queue();
    	
    }
    
    public void checkGreets(MessageReceivedEvent e) {
    	ArrayList<String> greetings = new ArrayList<String>();
    	greetings.add("hello");
    	greetings.add("konichiwa");
    	greetings.add("howdy");
    	
    	if(!e.getAuthor().isBot()) { // if not a bot
    	for(int i = 0; i < greetings.size(); i ++) { // check for all possible greetings
			if(e.getMessage().getContentDisplay().equals(greetings.get(i))) {
				e.getChannel().sendMessage(greetings.get(i) + e.getAuthor().getAsMention() + "!").queue();
				break;
			}
    				
    	}
    	}
    }
}
