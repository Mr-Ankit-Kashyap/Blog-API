package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{
	

}
