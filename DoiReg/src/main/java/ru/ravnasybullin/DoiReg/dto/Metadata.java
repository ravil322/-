package ru.ravnasybullin.DoiReg.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class Metadata {

    String authors;

    String title;

    Integer publicationYear;

    public Metadata(String authors, String title, int publicationYear) {
        this.authors = authors;
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authors", authors);
        jsonObject.put("title", title);
        jsonObject.put("publication_year", publicationYear);
        return jsonObject;
    }
}
