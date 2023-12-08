package com.emlakjet.purchasing.config;

import com.emlakjet.purchasing.shared.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "purchasing-config")
public class PurchasingConfigData {

    private Limit limit;

    public PurchasingConfigData() {
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public static class Limit {
        private Integer max = Constants.DEFAULT_LIMIT_MAX;

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
    }
}
