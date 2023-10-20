package com.picpay.splitfiles.controller;


import com.picpay.splitfiles.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.ParseException;

@Slf4j
@RestController
public class SplitController {



// TODO: Essentially, this service splits a file, reading it from <resources/folder>, by line numbers.

    private final FileService fileService;

    private static final int LINES_PER_FILE = 30;

    public SplitController(FileService fileService) throws IOException, ParseException {
        this.fileService = fileService;
        this.fileService.splitFile("input_file.csv", LINES_PER_FILE);
    }





}
