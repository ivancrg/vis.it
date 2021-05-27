package com.example.visit.database;


import com.google.gson.annotations.SerializedName;

    public class CurrentTripPatch {
        private String username;
        private int tripId;

        public CurrentTripPatch(String username, int tripId) {
            this.username = username;
            this.tripId = tripId;
        }

        @SerializedName("feedback")
        private String feedback;

        @SerializedName("trip_id")
        private int trip_id;

        public String getFeedback() {
            return feedback;
        }

        public String getUsername() {
            return username;
        }

        public int getTripId() {
            return tripId;
        }


        @Override
        public String toString() {
            return "{" +
                    "trip_id:'" + tripId + "'" +
                    "}";
        }
    }
