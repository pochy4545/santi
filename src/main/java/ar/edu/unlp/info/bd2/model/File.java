package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name= "files")
public class File  extends PersistentObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;

    public File() {
    }

    public File(String content, String name) {
        this.name = name;
        this.content = content;
    }

    public Long getId() {
        return id;
    }



    public String getName(){ return this.name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return this.name;
    }
}
