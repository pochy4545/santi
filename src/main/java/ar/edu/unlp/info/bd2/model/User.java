package ar.edu.unlp.info.bd2.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")

public class User extends PersistentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany
    private List<Commit> commits = new ArrayList<>();

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String emai) {
        this.email = emai;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User(){

    }

    public User(String email, String name) {
        this.name = name;
        this.email = email;
    }
    public void addCommit(Commit commit){
        this.getCommits().add(commit);
    }


}
