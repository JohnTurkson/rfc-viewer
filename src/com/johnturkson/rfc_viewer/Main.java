package com.johnturkson.rfc_viewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static final String RFC_ENDPOINT = "https://tools.ietf.org/rfc/";
    
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("RFC: ");
                String input = reader.readLine();
                System.out.println();
                if (input.equals("q")) {
                    break;
                }
                if (!input.matches("\\d+")) {
                    System.out.println("Input must correspond to an RFC number.");
                    continue;
                }
                System.out.println(getRFC(Integer.parseInt(input)));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static String getRFC(int rfc) throws IOException, InterruptedException {
        return HttpClient.newHttpClient()
                .send(HttpRequest.newBuilder()
                        .uri(URI.create(RFC_ENDPOINT + "rfc" + rfc + ".txt"))
                        .GET()
                        .build(), HttpResponse.BodyHandlers.ofString())
                .body();
    }
}
