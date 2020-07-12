package com.owlling.cookbook.community.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public final class PostBean implements Serializable {

    public boolean getIsWatched() {
        return isWatched == 1;
    }

    public void setIsWatched(boolean isWatched) {
        this.isWatched = isWatched ? 1 : 0;
    }

    /**
     * id : 8
     * content : 计时开始快开学免洗面膜吃嘛吃嘛吃嘛嘛
     * post_img : null
     * author_id : 9
     * c_comment : 2
     * c_liked : 12
     * post_time : 1558101195
     * nickname : ABC
     * comment : [{"nickname":"在吗","comment_id":12,"content":"你提哦去T1您","author_id":10,"post_id":8},{"nickname":"在吗","comment_id":13,"content":"在吗","author_id":10,"post_id":8}]
     */

    @SerializedName("is_watched")
    private int isWatched;

    private int id;
    private String content;
    @SerializedName("post_img")
    private String postImg;
    @SerializedName("author_id")
    private int authorId;

    public boolean isLiked = false;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @SerializedName("c_comment")
    private int commentCount;

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    @SerializedName("c_liked")
    private int likedCount;
    @SerializedName("post_time")
    private int postTime;
    private String nickname;
    private List<PreCommentBean> comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPostTime() {
        return postTime;
    }

    public void setPostTime(int postTime) {
        this.postTime = postTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<PreCommentBean> getComment() {
        return comment;
    }

    public void setComment(List<PreCommentBean> comment) {
        this.comment = comment;
    }

    public static class PreCommentBean {
        /**
         * nickname : 在吗
         * comment_id : 12
         * content : 你提哦去T1您
         * author_id : 10
         * post_id : 8
         */

        private String nickname;
        @SerializedName("comment_id")
        private int commentId;
        private String content;
        @SerializedName("author_id")
        private int authorId;
        @SerializedName("post_id")
        private int postId;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }
    }
}