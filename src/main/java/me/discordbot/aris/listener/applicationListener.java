package me.discordbot.aris.listener;

import me.discordbot.aris.command.applicationCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class applicationListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Message message = event.getMessage();

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            System.out.println("디스코드 Message 문자열 값 공백");
        }

        List<String> resultList = applicationCommand.checkCommand(event, message.getContentDisplay().split(" "));

        if (resultList.isEmpty()) {
            System.out.println("처리 결과 값 공백");
        }

        createSendMessage(event, resultList.get(0), Objects.requireNonNull(resultList.get(1)));
    }

    private void createSendMessage (MessageReceivedEvent event, String returnMessage, String returnEmbedMessage) {
        int discordAllowMessageSize = 1950;
        int resultMessageSize = returnEmbedMessage.length();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Team_GGL Aris_GGL");
        embed.setColor(Color.GREEN);
        embed.setFooter("ⓒ 2023. TEAM_GGL(developggl@gmail.com) All Rights Reserved. Site: https://github.com/Team-GGL/\n Bot Version : 1.0.0b\n");

        if (returnEmbedMessage.equals("명령어를 확인해 주세요.")) {
            embed.setColor(Color.RED);
        }

        if (resultMessageSize >= discordAllowMessageSize) {
            for (int index = 0; index < (resultMessageSize / discordAllowMessageSize) + 1; index++) {
                embed.setDescription(returnEmbedMessage.substring(0, discordAllowMessageSize));
                sendMessage(event, returnMessage, embed);
            }
        } else {
            embed.setDescription(returnEmbedMessage);
            sendMessage(event, returnMessage, embed);
        }
        embed.clear();
    }

    private void sendMessage(MessageReceivedEvent event, String returnMessage, EmbedBuilder embed) {
        TextChannel textChannel = event.getChannel().asTextChannel();
        textChannel.sendMessage(returnMessage).setEmbeds(embed.build()).queue();
    }
}
