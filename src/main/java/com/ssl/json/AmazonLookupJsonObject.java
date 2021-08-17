package com.ssl.json;

import com.ssl.data.SchemePriceData;
import java.util.List;


public class AmazonLookupJsonObject {

    String result;
    String message;
    List<SchemePriceData> priceData;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SchemePriceData> getPriceData() {
        return priceData;
    }

    public void setPriceData(List<SchemePriceData> priceData) {
        this.priceData = priceData;
    }
}
