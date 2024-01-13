package board.bbs.bbs.controller;


import board.bbs.bbs.dto.BoardResponseDTO;
import board.bbs.bbs.dto.BoardWriteRequestDTO;
import board.bbs.bbs.dto.CommentResponseDTO;
import board.bbs.bbs.service.CommentService;
import board.bbs.bbs.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;


    /**
     * 게시글 작성
     *
     * @return 게시글 작성 페이지
     */
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    /**
     * 게시글 작성 post
     *
     * @param boardWriteRequestDTO 게시글 정보
     * @param authentication       유저 정보
     * @return 게시글 디테일 페이지
     */
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO boardWriteRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boardService.saveBoard(boardWriteRequestDTO, userDetails.getUsername());

        return "redirect:/";
    }

    /**
     * 게시글 상세 조회
     *
     * @param id    게시글 ID
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        BoardResponseDTO result = boardService.boardDetail(id);
        List<CommentResponseDTO> commentResponseDTO = commentService.commentList(id);

        model.addAttribute("comments", commentResponseDTO);
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        System.out.println("id 는??" + id);

        return "board/detail";
    }


    /**
     * 게시글 수정
     *
     * @param id             게시글 ID
     * @param model
     * @param authentication 유저 정보
     * @return 게시글 수정 페이지
     */
    @GetMapping("/{id}/update")
    public String boardUpdateForm(@PathVariable Long id, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardResponseDTO result = boardService.boardDetail(id);
        System.out.println("출력---------------------------------------------------" + result.getEmail());
        System.out.println("출력---------------------------------------------------" + userDetails.getUsername());
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        if (result.getEmail().equals(userDetails.getUsername())) {
            System.out.println("if 출력---------------------------------------------------" + result.getEmail());
            System.out.println("if 출력---------------------------------------------------" + userDetails.getUsername());
            return "board/update";
        } else {
            System.out.println("else 출력---------------------------------------------------" + result.getEmail());
            System.out.println("else 출력---------------------------------------------------" + userDetails.getUsername());
            return "redirect:/";
        }
    }


    /**
     * 게시글 수정 post
     *
     * @param id                   게시글 ID
     * @param boardWriteRequestDTO 수정 정보
     * @return 게시글 상세 조회 페이지
     */
    @PostMapping("/{id}/update")
    public String boardUpdate(@PathVariable Long id, BoardWriteRequestDTO boardWriteRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardResponseDTO result = boardService.boardDetail(id);

        if (!Objects.equals(result.getEmail(), userDetails.getUsername())) {
            return "redirect:/";
        } else {
            boardService.boardUpdate(id, boardWriteRequestDTO);
            return "redirect:/board/" + id;
        }
    }

    /**
     * 게시글 삭제
     * @param id 게시글 ID
     * @param authentication 유저 정보
     * @return
     */
    @GetMapping("/{id}/remove")
    public String boardRemove(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardResponseDTO result = boardService.boardDetail(id);
        if (!Objects.equals(result.getEmail(), userDetails.getUsername())) {
            return "redirect:/";
        }

        boardService.boardRemove(id);

        return "redirect:/";
    }




}

