package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.transport.ObjectTable;
import javax.persistence.Query;

public class HibernateBithubRepository {

  @Autowired private SessionFactory sessionFactory;

  public User persistUser(User user){
    this.sessionFactory.getCurrentSession().save(user);
    return user;

  }

  public Branch persistBranch(Branch branch){
    this.sessionFactory.getCurrentSession().save(branch);
    return branch;
  }

  public Tag persistTag(Tag tag){
    this.sessionFactory.getCurrentSession().save(tag);
    return tag;
  }


  public File persistFile(File file) {
    this.sessionFactory.getCurrentSession().save(file);
    return file;
  }

  public Commit persistCommit(Commit commit) {
    this.sessionFactory.getCurrentSession().save(commit);
    return commit;
  }
  public Review persistReview(Review review) {
    this.sessionFactory.getCurrentSession().save(review);
    return review;
  }


  public Optional<Commit> getCommitByHash(String commitHash){
    Optional<Commit> commit =this.sessionFactory.getCurrentSession().createQuery("from Commit where hash = :commitHash")
            .setParameter("commitHash", commitHash).uniqueResultOptional();

    return commit;
  }

  public Optional<Branch> getBranchByname(String branchName){
    Optional<Branch> branch= this.sessionFactory.getCurrentSession().createQuery("from Branch where name = :branchName")
            .setParameter("branchName",branchName).uniqueResultOptional();
    return branch;
  }

  public Optional<Tag> getTagByName(String tagName){
    Optional<Tag> tag= this.sessionFactory.getCurrentSession().createQuery("from Tag where name = :tagName")
            .setParameter("tagName",tagName).uniqueResultOptional();
    return tag;
  }

  public FileReview persistFileReview(FileReview fileReview) {
    this.sessionFactory.getCurrentSession().save(fileReview);
    return fileReview;
  }

  public Optional<Review> getReviewById(long id) {
    Optional<Review> review= this.sessionFactory.getCurrentSession().createQuery("from Review where id = :id")
            .setParameter("id",id).uniqueResultOptional();
    return review;
  }

  public List<Commit> getAllCommitsForUser(long userId) {
    User user = (User) this.sessionFactory.getCurrentSession().createQuery("from User where id = :id")
            .setParameter("id", userId).getSingleResult();
    List<Commit> commits = (List<Commit> ) this.sessionFactory.getCurrentSession().createQuery("from Commit where user_id = :id").setParameter("id", userId).getResultList();
    return commits;
  }

  public  List<User> getAllUsers() {
    return  ( List<User>) this.sessionFactory.getCurrentSession().createQuery("from User").getResultList();
  }


  public List<User> getUsersThatCommittedInBranch(long branchId) {
    return  ( List<User>) this.sessionFactory.getCurrentSession().createQuery(
            "SELECT DISTINCT(c.user) FROM Commit c INNER JOIN c.branch cb WHERE cb.id = :branchId")
            .setParameter("branchId", branchId).getResultList();
  }

}
