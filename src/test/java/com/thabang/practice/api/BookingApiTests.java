package com.thabang.practice.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.thabang.practice.core.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * API practice against https://restful-booker.herokuapp.com (a public practice API).
 * Flow: auth -> create -> read -> update -> delete -> confirm 404.
 */
@Tag("api")
class BookingApiTests extends BaseApiTest {

    private static final Gson GSON = new Gson();

    private static Map<String, Object> newBooking(String firstName) {
        return Map.of(
                "firstname", firstName,
                "lastname", "Motshoane",
                "totalprice", 450,
                "depositpaid", true,
                "bookingdates", Map.of("checkin", "2026-12-01", "checkout", "2026-12-05"),
                "additionalneeds", "Breakfast");
    }

    private String token() {
        APIResponse res = api.post("/auth", RequestOptions.create()
                .setData(Map.of("username", "admin", "password", "password123")));
        assertThat(res.status()).isEqualTo(200);
        return GSON.fromJson(res.text(), JsonObject.class).get("token").getAsString();
    }

    @Test
    @Tag("smoke")
    void health_check_returns_201() {
        APIResponse res = api.get("/ping");
        assertThat(res.status()).isEqualTo(201);
    }

    @Test
    void booking_full_lifecycle() {
        // CREATE
        APIResponse created = api.post("/booking", RequestOptions.create().setData(newBooking("Thabang")));
        assertThat(created.status()).isEqualTo(200);
        JsonObject createdJson = GSON.fromJson(created.text(), JsonObject.class);
        int id = createdJson.get("bookingid").getAsInt();
        assertThat(createdJson.getAsJsonObject("booking").get("firstname").getAsString()).isEqualTo("Thabang");

        // READ
        APIResponse read = api.get("/booking/" + id);
        assertThat(read.status()).isEqualTo(200);
        assertThat(GSON.fromJson(read.text(), JsonObject.class).get("lastname").getAsString())
                .isEqualTo("Motshoane");

        // UPDATE
        APIResponse updated = api.put("/booking/" + id, RequestOptions.create()
                .setHeader("Cookie", "token=" + token())
                .setData(newBooking("Updated")));
        assertThat(updated.status()).isEqualTo(200);
        assertThat(GSON.fromJson(updated.text(), JsonObject.class).get("firstname").getAsString())
                .isEqualTo("Updated");

        // DELETE
        APIResponse deleted = api.delete("/booking/" + id, RequestOptions.create()
                .setHeader("Cookie", "token=" + token()));
        assertThat(deleted.status()).isEqualTo(201);

        // CONFIRM GONE
        assertThat(api.get("/booking/" + id).status()).isEqualTo(404);
    }

    @Test
    void delete_without_token_is_forbidden() {
        APIResponse res = api.delete("/booking/1");
        assertThat(res.status()).isEqualTo(403);
    }
}
