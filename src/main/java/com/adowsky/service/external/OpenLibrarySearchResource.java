package com.adowsky.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibrarySearchResource {

    @JsonProperty("author_name")
    private List<String> authors;
    private String title;
    private String subtitle;
}
