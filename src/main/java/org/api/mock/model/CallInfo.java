package org.api.mock.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Information when call mock (url, headers, body).
 */
@Data
public class CallInfo {
    private String hostName;
    private String hostIp;
    private String url;
    private String uri;
    private String query;
    private Map<String, List<String>> headers;

    private String body;

    private static final Logger LOG = LoggerFactory.getLogger(CallInfo.class);

    public static final class CallInfoBuilder {
        private String hostName;
        private String hostIp;
        private String url;
        private String uri;
        private String query;
        private Map<String, List<String>> headers;
        private String body;

        private CallInfoBuilder() {
        }

        public static CallInfoBuilder aCallInfo() {
            return new CallInfoBuilder();
        }

        public CallInfoBuilder withHostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        public CallInfoBuilder withHostIp(String hostIp) {
            this.hostIp = hostIp;
            return this;
        }

        public CallInfoBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public CallInfoBuilder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public CallInfoBuilder withQuery(String query) {
            this.query = query;
            return this;
        }

        public CallInfoBuilder withHeaders(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        public CallInfoBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public CallInfo build() {
            CallInfo callInfo = new CallInfo();
            callInfo.setHostName(hostName);
            callInfo.setHostIp(hostIp);
            callInfo.setUrl(url);
            callInfo.setUri(uri);
            callInfo.setQuery(query);
            callInfo.setHeaders(headers);
            callInfo.setBody(body);
            return callInfo;
        }
    }
}
