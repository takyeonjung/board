package board.bbs.bbs.service;

import board.bbs.bbs.domain.Board;
import board.bbs.bbs.domain.User;
import board.bbs.bbs.dto.BoardResponseDTO;
import board.bbs.bbs.dto.BoardWriteRequestDTO;
import board.bbs.bbs.repository.BoardRepository;
import board.bbs.bbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public Long saveBoard(BoardWriteRequestDTO boardWriteRequestDTO, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Board result = Board.builder()
                .title(boardWriteRequestDTO.getTitle())
                .content(boardWriteRequestDTO.getContent())
                .user(user)
                .build();
        boardRepository.save(result);

        return result.getId();
    }

    public BoardResponseDTO boardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        BoardResponseDTO result = BoardResponseDTO.builder()
                .board(board)
                .build();
        return result;
    }

    public Page<BoardResponseDTO> boardList(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return getBoardResponseDTOS(pageable, boards);
    }

    public Page<BoardResponseDTO> searchingBoardList(String keyword, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword, pageable);
        return getBoardResponseDTOS(pageable, boards);
    }

    private Page<BoardResponseDTO> getBoardResponseDTOS(Pageable pageable, Page<Board> boards) {
        List<BoardResponseDTO> boardDTOs = new ArrayList<>();

        for (Board board : boards) {
            BoardResponseDTO result = BoardResponseDTO.builder()
                    .board(board)
                    .build();
            boardDTOs.add(result);
        }

        return new PageImpl<>(boardDTOs, pageable, boards.getTotalElements());
    }

    public Long boardUpdate(Long id, BoardWriteRequestDTO boardWriteRequestDTO) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        board.update(boardWriteRequestDTO.getTitle(), boardWriteRequestDTO.getContent());
        boardRepository.save(board);

        return board.getId();
    }


    public void boardRemove(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        boardRepository.delete(board);
    }



}