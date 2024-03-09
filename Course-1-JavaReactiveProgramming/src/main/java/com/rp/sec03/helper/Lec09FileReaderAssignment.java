package com.rp.sec03.helper;

import com.rp.courseutil.Util;
import com.rp.sec03.assignment.FileReaderService;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Lec09FileReaderAssignment {

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderService();
        Path path = Paths.get("src/main/resources/assignment/sec03/file01.txt");
//        fileReaderService.readSync(path).subscribe(Util.subscriber("Test"));
        fileReaderService.read(path).subscribe(Util.subscriber("Test"));
    }

}
