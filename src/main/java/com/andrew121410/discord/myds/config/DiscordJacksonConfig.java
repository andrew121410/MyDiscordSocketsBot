package com.andrew121410.discord.myds.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscordJacksonConfig {

    @JsonProperty("discord-token")
    private String token = "discord token goes here.";

    @JsonProperty("discord-prefix")
    private String prefix = "?>";
}
