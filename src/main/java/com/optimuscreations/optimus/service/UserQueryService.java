package com.optimuscreations.optimus.service;

import com.optimuscreations.optimus.entity.File;
import com.optimuscreations.optimus.entity.UserQuery;
import com.optimuscreations.optimus.repository.FileRepository;
import com.optimuscreations.optimus.repository.UserQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserQueryService {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<UserQuery> getAllQueries() {
        return userQueryRepository.findAll();
    }

    public Optional<UserQuery> getQueryById(Long id) {
        return userQueryRepository.findById(id);
    }

    public List<UserQuery> getQueriesByEmail(String email) {
        return userQueryRepository.findByEmail(email);
    }

    public UserQuery createQuery(UserQuery userQuery, Long fileId) {
        if (fileId != null) {
            Optional<File> fileOpt = fileRepository.findById(fileId);
            fileOpt.ifPresent(userQuery::setFile);
        }
        return userQueryRepository.save(userQuery);
    }

    public UserQuery updateQuery(Long id, UserQuery userQueryDetails) {
        return userQueryRepository.findById(id)
            .map(query -> {
                query.setName(userQueryDetails.getName());
                query.setEmail(userQueryDetails.getEmail());
                query.setMessage(userQueryDetails.getMessage());
                query.setProjectType(userQueryDetails.getProjectType());
                query.setTimeline(userQueryDetails.getTimeline());
                
                // Only update file if it's provided
                if (userQueryDetails.getFile() != null) {
                    query.setFile(userQueryDetails.getFile());
                }
                
                return userQueryRepository.save(query);
            })
            .orElseThrow(() -> new RuntimeException("Query not found with id: " + id));
    }

    public void deleteQuery(Long id) {
        userQueryRepository.deleteById(id);
    }
}