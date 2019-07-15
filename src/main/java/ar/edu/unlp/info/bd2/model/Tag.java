package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name= "tags")
public class Tag extends PersistentObject{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Commit commit;

    public Tag() {
    }

    public Tag(Commit commit, String name) {
        this.name = name;
        this.commit = commit;
    }

    public Long getId() {
        return this.id;
    }




    public void setName(String name) {
        this.name = name;
    }

    public Commit getCommit() {
        return this.commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public String getName() {
        return this.name;
    }
}
