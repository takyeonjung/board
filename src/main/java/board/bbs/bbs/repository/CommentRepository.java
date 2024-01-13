package board.bbs.bbs.repository;

import board.bbs.bbs.domain.Board;
import board.bbs.bbs.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoard(Board board);


}