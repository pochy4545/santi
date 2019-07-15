package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;


import ar.edu.unlp.info.bd2.repositories.HibernateBithubRepository;
import org.bson.types.ObjectId;

import javax.persistence.metamodel.IdentifiableType;
import java.util.*;
import java.util.stream.Collectors;

public class HibernateBithubService implements BithubService<Long>{

    private HibernateBithubRepository repository;


    public HibernateBithubService(HibernateBithubRepository repository){
        this.repository = repository;
    }

    public User createUser(String email, String name){
        User user = new User(email, name);
        return repository.persistUser(user);

    }
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }


    public Branch createBranch(String name){
        Branch branch = new Branch(name);
        return repository.persistBranch(branch);
    }
    public  File createFile(String content, String name){
        File file  = new File(content, name);
        return repository.persistFile(file);
    }


    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch){
        Commit commit = new Commit(description,hash,author,files,branch);
        return repository.persistCommit(commit);
    }

    public Optional<Commit> getCommitByHash(String commitHash){
        Optional<Commit> commit = repository.getCommitByHash(commitHash);
        return commit;


    }

    public Optional<Branch> getBranchByName(String branchName){
        Optional<Branch> branch= repository.getBranchByname(branchName);
        return  branch;
    }

    public Tag createTagForCommit(String commitHash, String name) throws BithubException{
        Optional<Commit> optional = this.getCommitByHash(commitHash);

        if (optional.isPresent()) {


            Tag tag = new Tag(optional.get(), name);
            return repository.persistTag(tag);


        }
        else
            throw new BithubException("No existe ningún Commit con ese hash");

     }
    public  FileReview addFileReview(Review review, File file, int lineNumber, String comment)throws BithubException {

        List<Commit> commits= review.getBranch().getCommits();

        for (Commit commit : commits){
            if(commit.getFiles().contains(file)){
                FileReview fileReview = new FileReview(review,file,lineNumber,comment);
                return repository.persistFileReview(fileReview);

            }

        }
        throw new BithubException("no corresponde con el branch en el cual se encuentra  el archivo");


    }

    public Optional<Tag> getTagByName(String name){
        Optional<Tag> tag = repository.getTagByName(name);
        return tag;


    }
    public Review createReview(Branch branch, User user){
        Review review = new Review(branch,user);
        return repository.persistReview(review);

    }
    @Override
    public  Optional<Review> getReviewById(Long id){
        Optional<Review> review = repository.getReviewById(id);
        return review;

    }

    public List<Commit> getAllCommitsForUser(Long userId) {
        return  this.repository.getAllCommitsForUser(userId);
    }
    public Map<Long, Long> getCommitCountByUser(){
        List<User> users= this.repository.getAllUsers();
        Map map = new HashMap();

        for (User user : users){
            map.put(new Long (user.getId()), new Long(repository.getAllCommitsForUser(user.getId()).size()));
        }
        return map;
    }
    public List<User> getUsersThatCommittedInBranch(String branchName) throws BithubException{

        Optional<Branch> branch= this.getBranchByName(branchName);
        if (branch.isPresent()){
            List<User> users= this.repository.getUsersThatCommittedInBranch( branch.get().getId());
            return users;
        }
        throw new BithubException("No existe ese branch");

    }


}