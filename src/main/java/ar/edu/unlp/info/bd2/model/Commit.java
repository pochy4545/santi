package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonIgnore;

@Entity
@Table(name= "commits")
public class Commit extends PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String hash;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @ManyToOne
    @BsonIgnore
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;
    @OneToMany
    private List<File> files = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Commit() {
    }

    public Commit(String description, String hash, User user, List<File> files , Branch branch) {
        this.description = description;
        this.hash = hash;
        this.user = user;
        user.addCommit(this);
        this.branch = branch;
        branch.getCommits().add(this);
        this.files= files;
    }
    public void noSerializer(String description,String hash,User user,List<File> files,Branch branch){
        this.description=description;
        this.hash = hash;
        this.user =user;
        this.files=files;
        this.branch=branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public void setComments(List<File> files) {
        this.files = files;
    }

    public List<File> getComments(){return this.files;}

    public String getMessage() {
        return this.getDescription();
    }

    public User getAuthor() {
        return this.user;
    }

}
