package com.adowsky.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibrarySearchResponse {
    private List<OpenLibrarySearchResource> docs;
}
