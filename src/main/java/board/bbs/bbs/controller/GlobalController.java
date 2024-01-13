package board.bbs.bbs.controller;


import board.bbs.bbs.dto.BoardResponseDTO;
import board.bbs.bbs.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GlobalController {

    private final BoardService boardService;


    /**
     * Home 화면
     * @return 홈 페이지
     */
    @GetMapping("/")
    public String home(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable, String keyword) {
        if(keyword == null) {
            model.addAttribute("boardList", boardService.boardList(pageable));
        } else {
            model.addAttribute("boardList", boardService.searchingBoardList(keyword, pageable));
        }

        return "home";
    }



}
