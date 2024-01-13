package board.bbs.bbs.service;

import board.bbs.bbs.domain.Board;
import board.bbs.bbs.domain.Comment;
import board.bbs.bbs.domain.User;
import board.bbs.bbs.dto.CommentRequestDTO;
import board.bbs.bbs.dto.CommentResponseDTO;
import board.bbs.bbs.repository.CommentRepository;
import board.bbs.bbs.repository.BoardRepository;
import board.bbs.bbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    public Long writeComment(CommentRequestDTO commentRequestDTO, Long boardId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Comment result =  Comment.builder()
                .content(commentRequestDTO.getContent())
                .board(board)
                .user(user)
                .build();
        commentRepository.save(result);

        return result.getId();
    }


    public List<CommentResponseDTO> commentList(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByBoard(board);

        return comments.stream()
                .map(comment -> CommentResponseDTO.builder()
                        .comment(comment)
                        .build())
                .collect(Collectors.toList());

    }


    public void updateComment(CommentRequestDTO commentRequestDTO, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(commentRequestDTO.getContent());
        commentRepository.save(comment);
    }


    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


}