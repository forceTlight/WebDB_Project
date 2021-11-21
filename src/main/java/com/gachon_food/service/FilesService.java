package com.gachon_food.service;

import com.gachon_food.domain.Board;
import com.gachon_food.domain.Files;
import com.gachon_food.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FilesService {
    @Autowired
    FilesRepository filesRepository;

    /*
    파일 저장
     */
    public void save(Files files){
        Files f = new Files();
        f.setFileName(files.getFileName());
        f.setSaveFileName(files.getSaveFileName());
        f.setFilePath(files.getFilePath());
        f.setBoard(files.getBoard());
        filesRepository.save(f);
    }
    /*
    BoardId 외래키로 Files 찾기위한 메서드
     */
    public Files findByBoard(Board board){
        return filesRepository.findByBoard(board);
    }
}
