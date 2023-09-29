package me.discordbot.aris;

import me.discordbot.aris.listener.SlashCommandListener;
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
import java.util.Random;

@SpringBootApplication
public class DiscordBotApplication {
    protected static String[] RANDOM_PLAYING_MESSAGE = {"갓겜 개발", "테일즈 사가 크로니클", "GGL 관리", "블루 아카이브", "명령어: /도움말"};

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DiscordBotApplication.class, args);
        DiscordBotToken discordBotTokenEntity = context.getBean(DiscordBotToken.class);
        String discordBotToken = discordBotTokenEntity.getDiscordBotToken();

        JDABuilder jda = JDABuilder.createDefault(discordBotToken);
        Random random = new Random();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int randomNumber = random.nextInt(5);

            jda.setActivity(Activity.playing(RANDOM_PLAYING_MESSAGE[randomNumber]))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new applicationListener())
                    .addEventListeners(new SlashCommandListener())
                    .build();
        }, 0, 1, TimeUnit.MINUTES);
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