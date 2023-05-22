package com.vulnsc.social.Comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** @author preetkaran20@gmail.com KSASAN */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

}


