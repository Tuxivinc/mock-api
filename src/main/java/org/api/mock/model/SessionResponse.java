package org.api.mock.model;

public class SessionResponse {
        private boolean exist = Boolean.TRUE;
        private String value;

        public SessionResponse(boolean exist, String value) {
            this.exist = exist;
            this.value = value;
        }

        /**
         * Getter for property 'exist'.
         *
         * @return Value for property 'exist'.
         */
        public boolean isExist() {
            return exist;
        }

        /**
         * Setter for property 'exist'.
         *
         * @param exist Value to set for property 'exist'.
         */
        public void setExist(boolean exist) {
            this.exist = exist;
        }

        /**
         * Getter for property 'value'.
         *
         * @return Value for property 'value'.
         */
        public String getValue() {
            return value;
        }

        /**
         * Setter for property 'value'.
         *
         * @param value Value to set for property 'value'.
         */
        public void setValue(String value) {
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