package com.owlling.cookbook.community.user.userdetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailBean {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * id : 123
     * nickname : nick
     * profile_name : UVuK8YxoWp1555170109
     * total_liked : 25
     * total_watcher : 2
     * is_liked : 0 or 1
     * posts : [{"content":"vvv","post_time":1555231918,"post_img":"IJ80LmmEFw1555231910","post_id":3,"c_liked":14,"c_comment":3},{"content":"手机壳大口大口大口大口打卡可大打卡可大卡卡","post_time":1555322108,"post_img":"35kbMddcYn1555322102","post_id":5,"c_liked":11,"c_comment":1}]
     */

    private int id;
    private String nickname;
    @SerializedName("profile_name")
    private String profileName;
    @SerializedName("total_liked")
    private int totalLiked;
    @SerializedName("total_watcher")
    private int totalWatcher;
    @SerializedName("is_watched")
    private int isWatched;

    private List<PrePostsBean> posts;

    public void setIsWatched(boolean b){
        isWatched = b ? 1 : 0;
    }

    public Boolean getIsWatched(){
        return isWatched == 1;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getTotalLiked() {
        return totalLiked;
    }

    public void setTotalLiked(int totalLiked) {
        this.totalLiked = totalLiked;
    }

    public int getTotalWatcher() {
        return totalWatcher;
    }

    public void setTotalWatcher(int totalWatcher) {
        this.totalWatcher = totalWatcher;
    }

    public List<PrePostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PrePostsBean> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "UserDetailBean{" +
                "nickname='" + nickname + '\'' +
                ", profileName='" + profileName + '\'' +
                ", totalLiked=" + totalLiked +
                ", totalWatcher=" + totalWatcher +
                ", isWatched=" + isWatched +
                ", posts=" + posts +
                '}';
    }

    public static class PrePostsBean {
        /**
         * content : vvv
         * post_time : 1555231918
         * post_img : IJ80LmmEFw1555231910
         * post_id : 3
         * c_liked : 14
         * c_comment : 3
         */

        private String content;
        @SerializedName("post_time")
        private int postTime;
        @SerializedName("post_img")
        private String postImg;
        @SerializedName("post_id")
        private int postId;
        @SerializedName("c_liked")
        private int likedCount;
        @SerializedName("c_comment")
        private int commentCount;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPostTime() {
            return postTime;
        }

        public void setPostTime(int postTime) {
            this.postTime = postTime;
        }

        public String getPostImg() {
            return postImg;
        }

        public void setPostImg(String postImg) {
            this.postImg = postImg;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public int getLikedCount() {
            return likedCount;
        }

        public void setLikedCount(int likedCount) {
            this.likedCount = likedCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}
