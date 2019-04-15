package com.java11.rxjava;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Java11Test {
  public static void main(String[] args) {
    httpClient();
  }

  private static void httpClient() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.youseniu.com/portal/staffapi/globalConfig/findByParameterName/APP_VERSION"))
//        .GET()
//        .POST(null)
        .build();
    client.sendAsync(request, BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out::println)
    .join();
  }
}
