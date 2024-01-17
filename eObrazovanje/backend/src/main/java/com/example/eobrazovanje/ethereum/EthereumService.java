package com.example.eobrazovanje.ethereum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.IOException;
import java.math.BigInteger;

@Service
public class EthereumService {

    @Autowired
    EObrazovanjeContractClient contractClient;

    public String triggerTransaction(
            String studentId, String courseId, BigInteger grade, String date, BigInteger professorId
    ) throws IOException {

        // Convert frontend data to EthereumTransactionData
        EthereumTransactionData data = new EthereumTransactionData();
        data.setStudentId(new Utf8String(studentId));
        data.setCourseId(new Utf8String(courseId));
        data.setGrade(new Uint256(grade));
        data.setDate(new Utf8String(date));
        data.setProfessorId(new Uint256(professorId));

        // Trigger the Ethereum contract with the converted data
        return contractClient.invokeContract(data);
    }
}
