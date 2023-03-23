package tech.ada.java.movieslibrary.client.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ResultSearch {

    @JsonProperty("Search")
    private List<MovieMinimal> resultList;
    private Integer total;
    private Boolean response;
}
