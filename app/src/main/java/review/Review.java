package review;

/**
 * Created by Manish Menaria on 11-Jun-16.
 */
public class Review {
    private String author, review;

    public Review() {
    }

    public Review(String author, String review) {
        this.author = author;
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
