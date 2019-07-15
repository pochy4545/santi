package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.Association;
import ar.edu.unlp.info.bd2.repositories.MongoDBBithubRepository;
import org.bson.types.ObjectId;

import java.util.HashMap;
import javax.swing.text.html.Option;
/*import java.util.List;
import java.util.Map;
import java.util.Optional;*/
import java.util.*;

public class MongoDBBithubServiceImplementation implements BithubService<ObjectId> {

    private MongoDBBithubRepository repository;

    public MongoDBBithubServiceImplementation(MongoDBBithubRepository repository) {
        this.repository = repository;

    }
    @Override
    public User createUser(String email, String name) {
        return this.repository.createUser(new User(email, name));
    }


    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Branch createBranch(String name) {
        return this.repository.createBranch(new Branch(name));
    }

    @Override
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        Optional<Commit> commit = this.getCommitByHash(commitHash);
        if (!commit.isPresent()){
            throw new BithubException("El commit no existe");
        }
        try{
            return (Tag) this.repository.createTag(new Tag(commit.get(), name));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<Commit> getCommitByHash(String commitHash) { return Optional.ofNullable(this.repository.findCommit(commitHash)); }

    @Override
    public File createFile(String name, String content) { return this.repository.createFile(new File(name, content)); }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        return Optional.ofNullable(this.repository.findTag(tagName));
    }

    @Override
    public Review createReview(Branch branch, User user) { return this.repository.createReview(new Review(branch,user)); }

    @Override
    public FileReview addFileReview(Review review, File file, int lineNumber, String comment) throws BithubException {
      List<Commit> commits = this.repository.findCommitsOfBranch(review.getBranch().getObjectId());
        for (Commit commit : commits){
            for (File fileOfCommit : commit.getFiles()){
                if (fileOfCommit.getObjectId().equals(file.getObjectId())) {
                    FileReview fileReview = new FileReview();
                    fileReview.FileReviewConfig(review, file,lineNumber, comment);
                    FileReview persistedFileReview = this.repository.createFileReview(fileReview);
                    Association reviews = new Association(review.getObjectId(), persistedFileReview.getObjectId());
                    this.repository.create(reviews, "reviews_fileReview", Association.class);
                    return persistedFileReview;
                }
            }
        }
        throw new BithubException("El file no pertenece al branch");
    }

    @Override
    public Optional<Review> getReviewById(ObjectId id) { return this.repository.findReview(id); }

    @Override
    public List<Commit> getAllCommitsForUser(ObjectId userId) { return this.repository.findCommitsForUser(userId); }

    @Override
    //VER ESTO
    public Map<ObjectId,Long> getCommitCountByUser() {
        try{
            List<User> users = this.repository.findAllUsers();
            Map<ObjectId,Long> map = new HashMap<>();
            for(User user : users){
                map.put(user.getObjectId(),new Long(repository.findCommitsForUser(user.getObjectId()).size() )); }
            return map; }
        catch (Exception e)
        { return null; }
    }

    @Override
        public List<User> getUsersThatCommittedInBranch(String branchName) throws BithubException {
            Optional<Branch> branch = this.getBranchByName(branchName);
            if(branch.isPresent()){
                List<Commit> commits = branch.get().getCommits();
                List<User> users = new ArrayList<>();
                for (Commit commit : commits){
                    if (!(this.existCommit(users,commit))) {
                        users.add(commit.getAuthor()); }
                }
                return users;
            }else{
                throw new BithubException("El branch no existe"); }
        }
    public boolean existCommit(List<User> users, Commit commit){
        for(User user : users) {
            if (user.getObjectId().equals(commit.getAuthor().getObjectId())) {
                return true;}
        }return false;
    }

    @Override
    public Optional<Branch> getBranchByName(String branchName) {
        return this.repository.findBranch(branchName);
    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch) {
        Commit commit = new Commit();
        commit.noSerializer(description, hash, author, files, branch);
        // si no lo hacemos de esta manera se nos serializa por el constructor del commit y entramos en loop
        Commit persistCommit=this.repository.createCommit(commit);
        Association author_commits = new Association(author.getObjectId(), persistCommit.getObjectId());
        this.repository.create(author_commits, "author_commits", Association.class);
        Association commits_branch = new Association(branch.getObjectId(), persistCommit.getObjectId());
        this.repository.create(commits_branch, "commits_branch", Association.class);
        return persistCommit;
    }
}
