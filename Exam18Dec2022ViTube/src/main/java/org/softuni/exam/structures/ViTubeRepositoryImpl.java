package org.softuni.exam.structures;

import org.softuni.exam.entities.User;
import org.softuni.exam.entities.Video;

import java.util.*;
import java.util.stream.Collectors;

public class ViTubeRepositoryImpl implements ViTubeRepository {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Video> videos = new HashMap<>();

    private Map<User, Set<Video>> watchedVideos = new HashMap<>();
    private Map<User, Set<Video>> likedVideos = new HashMap<>();
    private Map<User, Set<Video>> dislikedVideos = new HashMap<>();

    private Map<String, List<Video>> videosToUser = new HashMap<>();

    @Override
    public void registerUser(User user) {
        this.users.put(user.getId(), user);
        videosToUser.put(user.getId(), new ArrayList<>());
        watchedVideos.put(user, new HashSet<>());
        likedVideos.put(user, new HashSet<>());
        dislikedVideos.put(user, new HashSet<>());
    }

    @Override
    public void postVideo(Video video) {
        this.videos.put(video.getId(), video);

    }

    @Override
    public boolean contains(User user) {
        return this.users.containsValue(user);
    }

    @Override
    public boolean contains(Video video) {
        return this.videos.containsValue(video);
    }

    @Override
    public Iterable<Video> getVideos() {
        return this.videos.values();
    }

    @Override
    public void watchVideo(User user, Video video) throws IllegalArgumentException {
        if (!contains(user) || contains(video)) throw new IllegalArgumentException();

        watchedVideos.get(user).add(video);
        video.setViews(video.getViews() + 1);
//        Video videoWatched = videos.get(video.getId());
//        videoWatched.setViews(video.getViews() + 1);

    }

    @Override
    public void likeVideo(User user, Video video) throws IllegalArgumentException {
        if (!contains(user) || contains(video)) throw new IllegalArgumentException();
//        Video videoWatched = videos.get(video.getId());
//        videoWatched.setLikes(video.getLikes() + 1);
        likedVideos.get(user).add(video);
        video.setLikes(video.getLikes() + 1);
    }

    @Override
    public void dislikeVideo(User user, Video video) throws IllegalArgumentException {
        if (!contains(user) || contains(video)) throw new IllegalArgumentException();
//        Video videoWatched = videos.get(video.getId());
//        videoWatched.setDislikes(video.getDislikes() + 1);
        dislikedVideos.get(user).add(video);
        video.setDislikes(video.getDislikes() + 1);
    }

    @Override
    public Iterable<User> getPassiveUsers() {


        List<User> passiveUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (watchedVideos.get(user).isEmpty() && likedVideos.get(user).isEmpty()
                    && dislikedVideos.get(user).isEmpty()) passiveUsers.add(user);
        }
        return passiveUsers;
    }

    @Override
    public Iterable<Video> getVideosOrderedByViewsThenByLikesThenByDislikes() {

        return videos.values()
                .stream()
                .sorted(Comparator.comparing(Video::getViews).reversed()
                        .thenComparing(Video::getLikes).reversed()
                        .thenComparing(Video::getDislikes)).collect(Collectors.toList());

    }

    @Override
    public Iterable<User> getUsersByActivityThenByName() {
        List<User> usersBy = new ArrayList<>(users.values());

        usersBy.sort(Comparator.comparingInt(user -> likedVideos.get(user).size()).reversed()
                .thenComparingInt(user -> watchedVideos.get(user).size()).reversed()
                .thenComparingInt(user -> dislikedVideos.get(user).size()).reversed());
        return usersBy;
    }
}
