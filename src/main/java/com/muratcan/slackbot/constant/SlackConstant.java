package com.muratcan.slackbot.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SlackConstant {

	public static final String LIST_ALL = "LIST ALL";
	public static final String GET = "GET";
	public static final String SET = "SET";
	public static final String DELETE = "DELETE";
	public static final String HELP = "HELP";
	public static final String PING = "PING";
	public static final String NULL = "NULL";
	public static final String SUCCESSFULLY_ADDED = "Successfully added.";
	public static final String SUCCESSFULLY_DELETED = "Successfully deleted.";
	public static final String RETRIEVE_CONVERSATIONS_HISTORY_ERROR = "Encountered an exception while retrieving " +
			"conversations history!";
	public static final String SENDING_POST_MESSAGE_ERROR = "Encountered an exception while sending post message!";
	public static final String CACHE_IS_EMPTY = "Cache is empty.";
	public static final String PING_MESSAGE = "What can I help you ?";
	public static final String HELP_MESSAGE = """
			Redis Cache Manager Bot
			
			Overview
			This Slack bot provides a simple interface to manage your Redis cache directly from Slack.
			
			Available Commands:
			- GET key -> Retrieves the value stored for the specified key
			- SET key value -> Stores a value with the specified key (permanent storage)
			- SET key value ttl -> Stores a value with the specified key and time-to-live in seconds
			- DELETE key -> Removes the specified key from the cache
			- LIST ALL -> Shows all currently stored keys in the cache
			- HELP -> Displays this help message
			- PING -> Checks if the bot is active
			
			Examples:
			- GET user_123
			- SET user_123 "John Doe"
			- SET config_123 {"feature":"enabled","timeout":30}
			- SET temp_key "temporary value" 3600  (expires in 1 hour)
			- DELETE user_123
			- LIST ALL
			- HELP
			- PING
			
			Note: When setting JSON values, make sure to properly escape the quotes.
			""";
}