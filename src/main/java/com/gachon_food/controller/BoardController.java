package com.gachon_food.controller;

import com.gachon_food.domain.Board;
import com.gachon_food.domain.Files;
import com.gachon_food.repository.BoardRepository;
import com.gachon_food.service.FilesService;
import com.gachon_food.validator.BoardValidator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private FilesService filesService;
    /*
    홈 보기 (GET)
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    /*
    게시글 목록 (GET)
     */
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 9) Pageable pageable,
                      @RequestParam(required = false, defaultValue = "") String searchText){
        // Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.max(boards.getTotalPages(),boards.getPageable().getPageNumber() - 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list";
    }
    /*
    게시글 폼 보기 (GET)
     */
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Integer id){
        if(id == null){
            model.addAttribute("board",new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            Files files = filesService.findByBoard(board);
            model.addAttribute("board", board);
            if(files != null){
                model.addAttribute("file", files);
            }
        }

        return "board/form";
    }
    /*
    게시글 폼 작성 (POST)
     */
    @PostMapping("/form")
    public String form(Model model, @Valid Board board, BindingResult bindingResult,
                       @RequestPart MultipartFile files, Authentication authentication) throws IOException {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        // 현재 로그인된 사용자 정보 세션 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        board.setAuthor(userDetails.getUsername());
        Board boardFile = boardRepository.save(board);

        // 파일 넣기
        if(files != null) {
            Files file = new Files();
            String sourceFileName = files.getOriginalFilename();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            File destinationFile;
            String destinationFileName;
            String filePath = "D:/가천대학교/3학년 2학기/웹 DB 프로그래밍/기말 프로젝트/서버/gachon_food/src/main/resources/static/boardImage/";
            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension; // 랜덤으로된 파일명
                destinationFile = new File(filePath + destinationFileName);
            } while (destinationFile.exists());
            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);
            file.setFileName(destinationFileName);
            file.setSaveFileName(sourceFileName);
            file.setFilePath(filePath);
            file.setBoard(boardFile);
            filesService.save(file);
        }
        return "redirect:/board/list";
    }
    /*
    제목이나 내용으로 게시글 검색하기 (GET)
     */
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return boardRepository.findAll();
        }else{
            return boardRepository.findByTitleOrContent(title, content);
        }
    }
    // end::get-aggregate-root[]
    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return boardRepository.save(newBoard);
    }

    // Single item
    @GetMapping("/boards/{id}")
    Board one(@PathVariable Integer id) {
        return boardRepository.findById(id).orElse(null);
    }
    /*
    게시글 수정 (PUT)
     */
    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Integer id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setBoardId(id);
                    return boardRepository.save(newBoard);
                });
    }
    /*
    게시글 삭제 (DELETE)
     */
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Integer id) {
        boardRepository.deleteById(id);
    }
}
