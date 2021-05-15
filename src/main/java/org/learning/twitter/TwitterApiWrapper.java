package org.learning.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterApiWrapper {

    private List<String> getTweets() throws TwitterException {
        Twitter twitter = getTwitterinstance();

        return twitter.getHomeTimeline().stream()
                .map(item -> item.getText())
                .collect(Collectors.toList());
    }

    public List<String> searchTweetsBy(String filterString) {
        Query query = new Query("track:" + filterString);
        Twitter twitterinstance = getTwitterinstance();
        try {
            QueryResult search = twitterinstance.search(query);
            return search.getTweets().stream()
                    .map(Status::getText)
                    .collect(Collectors.toList());
        } catch (TwitterException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    private Twitter getTwitterinstance() {

        /*
        oauth.consumerKey=yF5YiagJjbFnFvRsVeDdNdjT0
oauth.consumerSecret=ElU34URrAIlyJtfLOfBdGuwnyX0st6xd2GJ62O85ToIi7H3h2y
oauth.accessToken=1100293267017682945-PTmxMOWGnINL8oNgeZGeGJZJsyumrt
oauth.accessTokenSecret=n5z11Y4k4rMyQG4g3l0KsCodih7TP8599yorgr7M4Mo7O

         */
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("CONSUMER KEY")
                .setOAuthConsumerSecret("CONSUMER SECRETE")
                .setOAuthAccessToken("ASSCESS TOKEN")
                .setOAuthAccessTokenSecret("TOKEN SECRETE");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

    public static void streamFeed() {

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg) {
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            }

            @Override
            public void onStallWarning(StallWarning warning) {
            }

            @Override
            public void onStatus(Status status) {
                System.out.println("In onStatus: " + status.toString());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        twitterStream.addListener(listener);
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track("IPL");
        twitterStream.filter(filterQuery);

        twitterStream.sample();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twitterStream.shutdown();
    }
}
