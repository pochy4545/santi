package ar.edu.unlp.info.bd2.model;


import javax.persistence.*;

@Entity
@Table(name= "fileReviews")
public class FileReview extends PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Review review;
    @ManyToOne
    private File file;
    private Integer lineNumber;
    private String comment;

    public FileReview(Review review, File file, Integer lineNumber, String comment) {
        this.review = review;
        review.addFileReview(this);
        this.file = file;
        this.lineNumber = lineNumber;
        this.comment = comment;
    }
    public void FileReviewConfig(Review review, File file, Integer lineNumber, String comment) {
        this.setReview(review);
        this.file = file;
        this.lineNumber = lineNumber;
        this.comment = comment;
    }
    public  FileReview(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public File getReviewedFile() {
        return this.getFile();
    }
}

