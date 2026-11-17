package com.lms.letter_management_system.controllers;

import com.lms.letter_management_system.datatable.DataTableRequest;
import com.lms.letter_management_system.datatable.DataTablesResponse;
import com.lms.letter_management_system.dto.LetterDetails;
import com.lms.letter_management_system.dto.LetterRow;
import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.service.LetterService;
import java.nio.file.Path;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/letter")
public class LetterController {

    private final LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    @PostMapping("/letters")
    public DataTablesResponse<LetterRow> getLetters(@RequestBody DataTableRequest request) throws Exception {
        return letterService.getLetters(request);
    }

    @GetMapping("/details/{id}")
    public LetterDetails getDetails(@PathVariable Integer id) {
        return letterService.getLetterDetails(id);
    }

    @GetMapping("/letters/{id}/file/{kind}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id, @PathVariable String kind) {
        Path file = letterService.getStoredFile(id, kind);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(file.getFileName().toString())
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .header("Content-Disposition", ContentDisposition.inline()
                        .filename(file.getFileName().toString()).build().toString())
                .body(new FileSystemResource(file));
    }

    @PostMapping("/search-letter-users")
    public Iterable<SlimSelectDTO> searchUsers(@RequestParam String search) throws Exception {
        return letterService.searchDesignations(search);
    }

    @PostMapping("/search-division-users")
    public Iterable<SlimSelectDTO> searchDivisionUsers(@RequestParam(required = false) String search) {
        return letterService.searchDivisions(search);
    }

    @PostMapping("/save-letter")
    public ResponseEntity<CommonResponse> saveLetter(
            @RequestParam Integer addressTo,
            @RequestParam Integer sentBy,
            @RequestParam String subject,
            @RequestParam String letterMyNo,
            @RequestParam String documentDate,
            @RequestParam(required = false) List<Integer> actionTakenBy,
            @RequestParam(defaultValue = "false") boolean replyRequired,
            @RequestParam(required = false) String replyLetterMyNo,
            @RequestParam(required = false) String replyDate,
            @RequestParam(required = false) MultipartFile replyFile,
            @RequestParam MultipartFile letterFile) throws Exception {
        letterService.saveLetter(addressTo, sentBy, subject, letterMyNo, documentDate,
                actionTakenBy, replyRequired, replyLetterMyNo, replyDate, letterFile, replyFile);
        return new ResponseEntity<>(new CommonResponse("Letter saved successfully.", 200), HttpStatus.OK);
    }

    @PostMapping("/letters/{id}/reply")
    public ResponseEntity<CommonResponse> saveReply(
            @PathVariable Integer id,
            @RequestParam String replyLetterMyNo,
            @RequestParam(required = false) String replyDate,
            @RequestParam MultipartFile replyFile) throws Exception {
        letterService.saveReply(id, replyLetterMyNo, replyDate, replyFile);
        return new ResponseEntity<>(new CommonResponse("Reply letter saved successfully.", 200), HttpStatus.OK);
    }
}
