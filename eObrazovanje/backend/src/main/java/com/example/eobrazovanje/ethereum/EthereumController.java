package com.example.eobrazovanje.ethereum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/ethereum")
public class EthereumController {

    @Autowired
    EthereumService ethereumService;

    @PostMapping("/trigger-transaction")
    public ResponseEntity<String> triggerTransaction(@RequestBody EthereumTransactionData data) throws IOException {
        // Extract values from EthereumTransactionData
        String studentId = data.getStudentId().getValue();
        String courseId = data.getCourseId().getValue();
        BigInteger grade = data.getGrade().getValue();
        String date = data.getDate().getValue();
        BigInteger professorId = data.getProfessorId().getValue();

        String transactionHash = ethereumService.triggerTransaction(studentId, courseId, grade, date, professorId);

        return ResponseEntity.ok(transactionHash);
    }
}
