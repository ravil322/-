package ru.ravnasybullin.DoiReg.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class Metadata {

    String authors;

    String title;

    String keyWords;

    Integer publicationYear;

    public Metadata(String authors, String title, String keyWords, int publicationYear) {
        this.authors = authors;
        this.title = title;
        this.keyWords = keyWords;
        this.publicationYear = publicationYear;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authors", authors);
        jsonObject.put("title", title);
        jsonObject.put("key_words", keyWords);
        jsonObject.put("publication_year", publicationYear);
        return jsonObject;
    }
}
