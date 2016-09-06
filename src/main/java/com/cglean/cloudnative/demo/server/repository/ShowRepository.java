package com.cglean.cloudnative.demo.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cglean.cloudnative.demo.server.model.Show;

@RepositoryRestResource(collectionResourceRel = "shows", path = "shows")
public interface ShowRepository extends JpaRepository<Show, Long> {

    @RestResource(exported = false)
    Show findById(Long id);

    @RestResource(path = "name", rel = "name")
    Page<Show> findByTitleIgnoreCase(@Param("q") String title, Pageable pageable);

}
