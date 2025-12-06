package com.ryanlab.contentbase.model.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreationRequest {
  @NotBlank
  String articleId;
  @NotBlank(message = "Comment content cannot be empty or blank")
  @Length(min = 1, max = 1000, message = "Comment must be between 1 and 1000 characters")
  String content;
}
