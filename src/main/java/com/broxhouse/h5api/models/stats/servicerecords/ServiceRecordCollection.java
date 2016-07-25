

package com.broxhouse.h5api.models.stats.servicerecords;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@JsonIgnoreProperties({"Links"})
public class ServiceRecordCollection<T> {

    @SerializedName("Results")
    private List<PlayerQueryResponse<T>> results;

    public List<PlayerQueryResponse<T>> getResults() {
        return results;
    }
}
