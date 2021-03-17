package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class FollowCountRequest {
        private String userAlias;

        public FollowCountRequest(String userAlias) {
            this.userAlias = userAlias;
        }

        public FollowCountRequest() {
        }

        public String getUserAlias() {
            return userAlias;
        }

        public void setUserAlias(String userAlias) {
            this.userAlias = userAlias;
        }
}
