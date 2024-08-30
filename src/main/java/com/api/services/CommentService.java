package com.api.services;

import com.api.dtos.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto , Long postId);
	
	void deleteComment(Long commentId);

}