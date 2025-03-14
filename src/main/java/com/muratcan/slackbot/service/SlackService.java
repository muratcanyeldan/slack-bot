package com.muratcan.slackbot.service;

import com.muratcan.slackbot.config.SlackMethodsClientConfig;
import com.muratcan.slackbot.config.SlackProperties;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.muratcan.slackbot.constant.SlackConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

	private final SlackProperties slackProperties;
	private final SlackMethodsClientConfig slackConfiguration;

	public String retrieveConversationsHistory() {
		MethodsClient client = slackConfiguration.methods();
		try {
			ConversationsHistoryResponse response = client.conversationsHistory(r -> r
					.token(slackProperties.getToken())
					.channel(slackProperties.getChannelId())
					.limit(1));

			return Optional.ofNullable(response)
					.map(ConversationsHistoryResponse::getMessages)
					.filter(messages -> !messages.isEmpty())
					.map(List::getFirst)
					.filter(message -> !isMessageFromBot(message))
					.map(Message::getText)
					.orElse(NULL);

		} catch (IOException | SlackApiException e) {
			log.error("Encountered an exception while retrieving history", e);
			sendPostMessage(RETRIEVE_CONVERSATIONS_HISTORY_ERROR + " and detail message :" + e.getMessage());
			return NULL;
		}
	}

	private boolean isMessageFromBot(Message message) {
		return Optional.ofNullable(message.getBotProfile())
				.map(botProfile -> botProfile.getName().equals(slackProperties.getUsername()))
				.orElse(false);
	}

	public void sendPostMessage(String textMessage) {
		MethodsClient client = slackConfiguration.methods();
		try {
			client.chatPostMessage(r -> r
					.token(slackProperties.getToken())
					.channel(slackProperties.getChannelId())
					.username(slackProperties.getUsername())
					.text(textMessage));
		} catch (IOException | SlackApiException e) {
			log.error("Encountered an exception while sending post message", e);
			sendPostMessage(SENDING_POST_MESSAGE_ERROR + " and detail message :" + e.getMessage());
		}
	}
}
