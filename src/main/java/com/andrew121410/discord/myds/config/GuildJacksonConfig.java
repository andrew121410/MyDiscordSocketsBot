package com.andrew121410.discord.myds.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GuildJacksonConfig {

    public GuildJacksonConfig(String guildID) {
        this.guildID = guildID;
    }

    @JsonProperty("guild-id")
    private String guildID;

    @JsonProperty("already-verified-map")
    private Map<String, String> verifiedMap = new HashMap<>();

    @JsonProperty("accepting-new-connections")
    private boolean acceptingNewConnections = true;
}
