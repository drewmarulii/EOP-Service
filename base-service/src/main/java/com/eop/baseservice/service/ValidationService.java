package com.eop.baseservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class ValidationService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public void validateIdExists(ID id) {
        if (!getRepository().existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }
    }

    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Version not matched");
        }
    }
}
