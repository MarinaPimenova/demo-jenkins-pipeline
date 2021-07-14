package com.mp.service;

import com.mp.config.GoogleLibraryProperties;
import com.mp.exception.BookException;
import com.mp.model.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Service
public class GoogleBooksServiceImpl implements GoogleBooksService {
    @Autowired
    private GoogleLibraryProperties libraryProperties;

    private WebClient googleClient = WebClient
            .builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public Book findByIsbn(String isbn) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .fromHttpUrl(libraryProperties.getUrl())
                .queryParam("q", "isbn:" + isbn)
                .queryParam("key", libraryProperties.getKey()).build();
        Map<String, Object> result = googleClient.
                get()
                .uri(uriComponents.toUri())
                .retrieve()
                .bodyToMono(Map.class).block();

        return generateBookFromJson(isbn, result);
    }

    private Book generateBookFromJson(String isbn, Map<String, Object> result) {
        if (CollectionUtils.isEmpty(result) || !result.containsKey("items")) {
            throw new BookException("Failed to find book by ISBN: " + isbn);
        }
        Map<String, Object> volumeInfo = (Map<String, Object>) ((Map) ((List) result.get("items")).get(0)).get("volumeInfo");
        String authors = String.join(",",((List<String>) volumeInfo.get("authors")));
        return new Book(isbn, (String) volumeInfo.get("title"), authors);
    }
}
