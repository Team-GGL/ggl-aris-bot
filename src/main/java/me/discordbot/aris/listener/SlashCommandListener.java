package me.discordbot.aris.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (commandName.equals("환영인사")) {
            OptionMapping optionMapping = event.getOption("옵션");
            String type = optionMapping.getAsString();

            String replyMessage = "";
            switch (type.toLowerCase()) {
                case "채널 등록" -> {
                    replyMessage = "채널 등록 완료";
                }
                case "채널 삭제" -> {
                    replyMessage = "채널 삭제 완료";
                }
            }

            event.reply(replyMessage).queue();
        } else if (commandName.equals("도움말")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Hello Command");
            embed.setColor(Color.GREEN);
            embed.setDescription("Hello, world!");

            event.replyEmbeds(embed.build()).queue();
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("도움말", "GGL 아리스봇 도움말을 출력해요."));

        OptionData option1 = new OptionData(OptionType.STRING, "옵션", "옵션을 선택해 주세요", true)
                .addChoice("채널 등록", "채널 등록")
                .addChoice("채널 삭제", "채널 삭제");
        commandData.add(Commands.slash("환영인사", "(관리자) 서버에 신규 인원이 들어오면 이 채널에서 환영인사를 보내요.").addOptions(option1));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        TextChannel welcomeChannel = guild.getTextChannelsByName("잡담", true).get(0);

        if (welcomeChannel != null) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("환영합니다!");
            embed.setDescription("서버에 오신 것을 환영합니다!");
            embed.setColor(Color.GREEN);

            welcomeChannel.sendMessageEmbeds(embed.build()).queue();
        }
    }
}