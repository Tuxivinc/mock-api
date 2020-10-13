package org.api.mock.model;

import lombok.Data;

@Data
public class SessionResponse {
        private boolean exist = Boolean.TRUE;
        private String value;

        public SessionResponse(boolean exist, String value) {
            this.exist = exist;
            this.value = value;
        }

        @Override
        public String toString() {
            return "SessionResponse{" +
                    "exist=" + exist +
                    ", value='" + value + '\'' +
                    '}';
        }
    }