package com.backend.blog.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	
	@NotNull(message = "Title should not be null")
	@Size(min = 4, message = "Title should be min of 4 chars")
	private String categoryTitle;
	
	@NotNull(message = "Description should not be null")
	@Size(min = 10, message = "Description should be min of 10 chars")
	private String categoryDescription;
}
