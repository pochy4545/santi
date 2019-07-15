package ar.edu.unlp.info.bd2.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name= "branches")
public class Branch extends PersistentObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
   private List<Commit> commits = new ArrayList<>();

    public Branch() {
    }


    public Branch(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
