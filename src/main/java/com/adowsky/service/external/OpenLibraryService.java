package com.adowsky.service.external;

import com.adowsky.model.BookMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OpenLibraryService {
    private static final String FIND_URL = "http://openlibrary.org/search.json?limit=5&";
    private static final String SOURCE = "openlibrary.org";
    private final RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<BookMetadata> findByAuthorAndTitle(String author, String title) {
        String query = FIND_URL;
        if (StringUtils.isBlank(author))
            query += "title=" + title;
        else if (StringUtils.isBlank(title))
            query += "author=" + author;
        else
            query += "author=" + author + "&title=" + title;
        String json = restTemplate.getForObject(query, String.class);
        try {
            OpenLibrarySearchResponse books = objectMapper.readValue(json, OpenLibrarySearchResponse.class);
            return books.getDocs().stream()
                    .filter(resource -> resource.getAuthors() != null && resource.getTitle() != null)
                    .map(resource -> new BookMetadata(this.extractTitle(resource),
                            resource.getAuthors().stream().collect(Collectors.joining(", ")), SOURCE))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String extractTitle(OpenLibrarySearchResource resource) {
        String title = resource.getTitle();
        if (resource.getSubtitle() != null) {
            title += ". " + resource.getSubtitle();
        }
        return title;
    }
}
