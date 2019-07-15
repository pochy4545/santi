package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.Association;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.transport.ObjectTable;

public class MongoDBBithubRepository {

  @Autowired private MongoClient client;
  public Commit createCommit(Commit commit){
    MongoCollection<Commit> commits = client.getDatabase("bd2").getCollection("commits",Commit.class);
    commits.insertOne(commit);
    return commit;
  }

  public Branch createBranch(Branch branch){
    MongoCollection<Branch> branchs = client.getDatabase("bd2").getCollection("branches",Branch.class);
    branchs.insertOne(branch);
    return branch;
  }

  public User createUser(User user){
    MongoCollection<User> users = client.getDatabase("bd2").getCollection("users",User.class);
    users.insertOne(user);
    return user;
  }

  public Tag createTag(Tag tag){
    MongoCollection<Tag> tags = client.getDatabase("bd2").getCollection("tags",Tag.class);
    tags.insertOne(tag);
    return tag;
  }

  public File createFile(File file){
    MongoCollection<File> files = client.getDatabase("bd2").getCollection("files",File.class);
    files.insertOne(file);
    return file;
  }

    public FileReview createFileReview(FileReview fileReview){
        MongoCollection<FileReview> fileReviews = client.getDatabase("bd2").getCollection("fileReviews",FileReview.class);
        fileReviews.insertOne(fileReview);
        return fileReview;
    }
  public Review createReview(Review review){
    MongoCollection<Review> reviews = client.getDatabase("bd2").getCollection("reviews",Review.class);
    reviews.insertOne(review);
    return review;
  }

    public Optional<Review> findReview(ObjectId id){
        MongoCollection<Review> reviews = client.getDatabase("bd2").getCollection("reviews", Review.class);
        Optional<Review> review = Optional.ofNullable(reviews.find(eq("_id", id)).first());
        if(review.isPresent()){
            List<FileReview> filesReview = this.findFileReviewsOf(review.get().getObjectId());
            review.get().setFilesReview(filesReview);
        }
        return review;
    }

    public List<FileReview> findFileReviewsOf(ObjectId reviewId) {
        MongoCollection<Association> fileReviewsCollection = client.getDatabase("bd2").getCollection("reviews_fileReview", Association.class);
        FindIterable<Association> fileReviewsId = fileReviewsCollection.find(eq("source", reviewId));
        List<FileReview> fileReviews = new ArrayList<>();
        for (Association association : fileReviewsId) {
            ObjectId fileReviewId = association.getDestination();
            fileReviews.add(this.findFileReview(fileReviewId));
        }
        return fileReviews;
    }


    public FileReview findFileReview(ObjectId id){
        MongoCollection<FileReview> fileReviewS = client.getDatabase("bd2").getCollection("fileReviews", FileReview.class);
        FileReview fileReview = fileReviewS.find(eq("_id", id)).first();
        return fileReview;
    }


  public void create(Object object, String collection, Class tClass){
    MongoCollection<Object> objectMongo = client.getDatabase("bd2").getCollection(collection,tClass);
    objectMongo.insertOne(object);
  }

  public Commit findCommit(String commitHash){
    MongoCollection<Commit> commits = client.getDatabase("bd2").getCollection("commits",Commit.class);
    Commit commit = commits.find(eq("hash", commitHash)).first();
    return commit;
  }

 public List<User> findAllUsers(){
     MongoCollection<User> userss = client.getDatabase("bd2").getCollection("users", User.class);
     MongoCursor<User> usersCollection = userss.find().iterator();
     try {
         List<User> users = new ArrayList<>();
         while (usersCollection.hasNext()) {
             users.add(usersCollection.next());
         }
         return users;
     } finally {
         usersCollection.close();
     }

 }
 public Tag findTag(String tagName){
   MongoCollection<Tag> tags = client.getDatabase("bd2").getCollection("tags",Tag.class);
   return tags.find(eq("name",tagName)).first();
 }
  public Optional<Branch> findBranch(String branchName){
    MongoCollection<Branch> branches = client.getDatabase("bd2").getCollection("branches", Branch.class);
    Optional<Branch> branch = Optional.ofNullable(branches.find(eq("name", branchName)).first());
   if(branch.isPresent()) {
      List<Commit> branchCommits = this.findCommitsOfBranch(branch.get().getObjectId());
      branch.get().setCommits(branchCommits);
    }
    return branch;
  }

  public List<Commit> findCommitsOfBranch (ObjectId branchId){
    MongoCollection<Association> branchCommitsCollection = client.getDatabase("bd2").getCollection("commits_branch", Association.class);
    FindIterable<Association> commitsId = branchCommitsCollection.find(eq("source", branchId));
    List<Commit> commits = new ArrayList<>();
    for (Association association : commitsId){
        ObjectId idCommit = association.getDestination();
        commits.add(this.findCommit(idCommit));
    }
    return commits;
  }

    public List<Commit> findCommitsForUser (ObjectId id){
        MongoCollection<Association> authorCommitsCollection = client.getDatabase("bd2").getCollection("author_commits", Association.class);
        FindIterable<Association> commitsId = authorCommitsCollection.find(eq("source", id));
        List<Commit> commits = new ArrayList<>();
        for (Association idCommit : commitsId){
            commits.add(this.findCommit(idCommit.getDestination()));
        }
        return commits;
    }



  public Commit findCommit(ObjectId id){
    MongoCollection<Commit> commits = client.getDatabase("bd2").getCollection("commits",Commit.class);
    Commit commit = commits.find(eq("_id",id)).first();
    return commit;
  }
  public FileReview addFileReview(FileReview fileReview) {
    MongoCollection<FileReview> fileReviews = client.getDatabase("bd2").getCollection("filereview",FileReview.class);
    fileReviews.insertOne(fileReview);
    return fileReview;
  }

}
