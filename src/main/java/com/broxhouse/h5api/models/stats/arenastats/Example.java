package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Example {

    @SerializedName("Results")
    @Expose
    private List<Result> results = new ArrayList<Result>();
    @SerializedName("Links")
    @Expose
    private Object links;

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The Results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * @return The links
     */
    public Object getLinks() {
        return links;
    }

    /**
     * @param links The Links
     */
    public void setLinks(Object links) {
        this.links = links;
    }


}
