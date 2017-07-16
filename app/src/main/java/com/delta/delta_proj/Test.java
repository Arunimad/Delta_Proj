package com.delta.delta_proj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public  class Test {

    public void main(String[] args) throws Exception {
        String url = "https://stackoverflow.com/questions/2835505";
        Document document = Jsoup.connect(url).get();

        String question = document.select("#question .post-text").text();
        System.out.println("Question: " + question);

        Elements answerers = document.select("#answers .user-details a");
        for (Element answerer : answerers) {
            System.out.println("Answerer: " + answerer.text());
        }
    }

}
