package com.example.groupservice.repository;

import com.example.groupservice.model.Group;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group,String> {

    @Aggregation(pipeline = {
            "{ '$match': { 'result.username' : {'$regex': ?0} } }",
            "{ '$limit' : 5 }"
    })
    List<Group> findByGroupNameWithRegex(String groupName);

    @Aggregation(pipeline = {
            "{ '$match': { 'result.username' : ?0 } }",
    })
    Optional<Group> findByGroupName(String groupName);

    @Aggregation(pipeline = {
            "{ '$match': { 'result.id' : ?0 } }",
    })
    Optional<Group> findByGroupId(Long id);


    List<Group> findByAdminId(Long id);
}
