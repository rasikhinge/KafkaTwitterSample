package org.learning.twitter;

import org.learning.twitter.kafka.producer.Producer;

import java.util.List;

public class TwitterKafkaImpl {

    private final Producer kafkaProducer;
    private final TwitterApiWrapper twitterApiWrapper;

    public TwitterKafkaImpl() {
        kafkaProducer = new Producer();
        twitterApiWrapper = new TwitterApiWrapper();
    }

    public static void main(String[] args) {
        new TwitterKafkaImpl().run();
    }

    public void run() {
        List<String> iplTweets = twitterApiWrapper.searchTweetsBy("IPL");
        iplTweets.forEach(kafkaProducer::send);
        kafkaProducer.close();
    }
}
