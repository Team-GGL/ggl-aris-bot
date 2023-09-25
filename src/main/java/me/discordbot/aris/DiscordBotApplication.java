package me.discordbot.aris;

import me.discordbot.aris.listener.applicationListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DiscordBotApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DiscordBotApplication.class, args);
        DiscordBotToken discordBotTokenEntity = context.getBean(DiscordBotToken.class);
        String discordBotToken = discordBotTokenEntity.getDiscordBotToken();

        JDABuilder jda = JDABuilder.createDefault(discordBotToken);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            jda.setActivity(Activity.playing("갓겜 개발"));
        }, 0, 2, TimeUnit.MINUTES);

        jda.enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new applicationListener())
                .build();
    }
}

@Component
class DiscordBotToken {
    @Value("${discord.bot.token}")
    private String discordBotToken;

    public String getDiscordBotToken() {
        return discordBotToken;
    }
}