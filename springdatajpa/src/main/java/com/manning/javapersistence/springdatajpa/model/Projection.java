package com.manning.javapersistence.springdatajpa.model;

import org.springframework.beans.factory.annotation.Value;

public class Projection {
    // interface-based projection needs to be created at runtime (proxy)
    public interface UserSummary{
        String getUsername();
        @Value("#{target.username} #{target.email}")
        String getInfo();
    }
    public static class UsernameOnly{
        private String username;
        // parameter names are important which are used to determine which properties should be included
        public UsernameOnly(String username) {
            this.username = username;
        }
        public String getUsername() {
            return username;
        }

        @Override
        public String toString() {
            return "UsernameOnly{" +
                    "username='" + username + '\'' +
                    '}';
        }
    }
}
