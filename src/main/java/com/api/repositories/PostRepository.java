package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.Category;
import com.api.entities.Post;
import com.api.entities.User;

public interface PostRepository extends JpaRepository<Post , Long>{

	List<Post> getByCategory(Category category);

	List<Post> getByUser(User user);

	List<Post> findByTitleContaining(String keyword);

}
