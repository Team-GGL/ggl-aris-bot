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
import java.util.Random;

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
        } else if (commandName.equals("할까말까")) {
            Random random = new Random();
            boolean randomBoolean = random.nextBoolean();

            String bResultMsg = "";
            String content = "";

            if (randomBoolean) {
                content = "<:aris_7:967759079265161236>";
                bResultMsg = "⭕ 긍정. 당신의 고민거리, 아리스의 대답으로 대체되었다.\n아리스에게서 좋은 반응 얻다.";
            } else {
                content = "<:aris_8:1016677932070092891>";
                bResultMsg = "❌ 부정. 당신의 고민거리, 아리스의 대답으로 대체되었다.\n아리스에게서 부정적인 반응 얻다.";
            }

            if (bResultMsg.equals("")) {
                bResultMsg = "❗ 오류. 당신의 고민거리, 아리스가 대답을 할 수 없습니다.\n아리스에게서 다시 시도하라는 반응 얻다.";
            }

            OptionMapping optionMapping = event.getOption("고민거리");
            String enteredText = optionMapping.getAsString();

            String userAvatarUrl = event.getUser().getAvatarUrl();
            String userName = event.getUser().getName();

            EmbedBuilder embed = new EmbedBuilder();

            if (userAvatarUrl != null) {
                embed.setAuthor(userName, null, userAvatarUrl);
            } else {
                embed.setAuthor(userName, null, null);
            }

            embed.setTitle("아리스가 골라드리겠습니다!");
            embed.setColor(new Color(103, 153, 255));
            embed.addField("\uD83D\uDC8E 고민거리", "```" + enteredText + "```", false);
            embed.addField("\uD83D\uDC8E 아리스의 선택", "```" + bResultMsg + "```", false);

            event.reply(content).setEmbeds(embed.build()).queue();
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

        OptionData option2 = new OptionData(OptionType.STRING, "고민거리", "할지 말지 고민하고 계신걸 적어주세요!", true);
        commandData.add(Commands.slash("할까말까", "할지 말지 결정장애가 올 때는 아리스_GGL에게 물어보세요!").addOptions(option2));

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