package com.api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {

	private Long id;

	@NotEmpty
	@Size(min = 4, message = "Min size of category title is 4")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "Min size of category description is 10")
	private String description;

}
