package com.example.visit.database;


import com.google.gson.annotations.SerializedName;

    public class CurrentTripPatch {
        private String username;

        @SerializedName("feedback")
        private String feedback;

        @SerializedName("trip_id")
        private String tripId;


        public CurrentTripPatch(String username, String tripId) {
            this.username = username;
            this.tripId = tripId;
        }

        public String getFeedback() {
            return feedback;
        }

        public String getUsername() {
            return username;
        }

        public String getTripId() {
            return tripId;
        }


        @Override
        public String toString() {
            return "{" +
                    "feedback:'" + feedback + "'" +
                    "}";
        }
    }
