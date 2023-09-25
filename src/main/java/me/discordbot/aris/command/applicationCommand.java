package me.discordbot.aris.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class applicationCommand {
    public static List<String> checkCommand (MessageReceivedEvent event, String[] messageArray) {
        List<String> result = new ArrayList<>();

        if (messageArray[0].equalsIgnoreCase("주니야") && messageArray[1].equalsIgnoreCase("크루전체조회")) {
            result.add(event.getAuthor().getName() + "님 크루들의 정보는 아래와 같아요!");
            // result.add(checkBodyCommandForAPICall(messageArray));
            return result;
        } else if (messageArray[0].equalsIgnoreCase("주니야")){
            result.add(event.getAuthor().getName() + "님 어떤 정보가 필요하세요?");
            // result.add(checkBodyCommand(event, messageArray));
            return result;
        } else {
            return result;
        }
    }
}
