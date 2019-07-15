package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "reviews")
public class Review extends PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private User author;
    @OneToMany
    private List<FileReview> filesReview = new ArrayList<>();

    public List<FileReview> getReviews() {
        return filesReview;
    }
    public List<FileReview> getFilesReview() {
        return this.getReviews();
    }


    public void setFilesReview(List<FileReview> filesReview) {
        this.filesReview = filesReview;
    }
    public void addFileReview(FileReview fileReview){
        this.filesReview.add(fileReview);
    }
    public Review() {
    }

    public Review(Branch branch, User author) {
        this.branch = branch;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}

