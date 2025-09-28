package com.optimuscreations.optimus.controller;

import com.optimuscreations.optimus.entity.UserQuery;
import com.optimuscreations.optimus.service.UserQueryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @GetMapping
    public ResponseEntity<List<UserQuery>> getAllQueries() {
        List<UserQuery> queries = userQueryService.getAllQueries();
        return ResponseEntity.ok(queries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserQuery> getQueryById(@PathVariable Long id) {
        return userQueryService.getQueryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserQuery>> getQueriesByEmail(@PathVariable String email) {
        List<UserQuery> queries = userQueryService.getQueriesByEmail(email);
        return ResponseEntity.ok(queries);
    }

    @PostMapping
    public ResponseEntity<UserQuery> createQuery(
            @Valid @RequestBody UserQuery userQuery,
            @RequestParam(required = false) Long fileId) {
        UserQuery savedQuery = userQueryService.createQuery(userQuery, fileId);
        return ResponseEntity.ok(savedQuery);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserQuery> updateQuery(
            @PathVariable Long id,
            @Valid @RequestBody UserQuery userQueryDetails) {
        try {
            UserQuery updatedQuery = userQueryService.updateQuery(id, userQueryDetails);
            return ResponseEntity.ok(updatedQuery);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        userQueryService.deleteQuery(id);
        return ResponseEntity.noContent().build();
    }
}