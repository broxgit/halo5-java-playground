

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiceRecordCollection<T> implements Serializable {

    @SerializedName("Results")
    private List<PlayerQueryResponse<T>> results;

    public List<PlayerQueryResponse<T>> getResults() {
        return results;
    }
}
