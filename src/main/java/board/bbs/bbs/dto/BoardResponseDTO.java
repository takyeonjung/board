package board.bbs.bbs.dto;


import board.bbs.bbs.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private String username;
    private String email;


    @Builder
    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
        this.username = board.getUser().getUsername();
        this.email = board.getUser().getEmail();
    }



}
