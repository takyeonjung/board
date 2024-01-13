package board.bbs.bbs.dto;

import board.bbs.bbs.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDTO {

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long id;
    private String content;
    private String username;
    private String email;

    @Builder
    public CommentResponseDTO(Comment comment) {
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.email = comment.getUser().getEmail();
    }
}
