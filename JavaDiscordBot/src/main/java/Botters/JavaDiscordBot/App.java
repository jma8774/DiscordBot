package Botters.JavaDiscordBot;

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
    	Message objMsg = e.getMessage();
    	MessageChannel objChannel = e.getChannel();
    	User objUser = e.getAuthor();
    	
    	if(objMsg.getContentDisplay().equals("hello")) objChannel.sendMessage("Hello, " + objUser.getAsMention() + "!").queue();
    	if(objMsg.getContentDisplay().indexOf("boom") > -1) objChannel.sendMessage(objUser.getAsMention() + " stfu, boom your mom.").queue();
    		
    }
}
