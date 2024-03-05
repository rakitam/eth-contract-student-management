package com.example.eobrazovanje.ethereum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

@Component
public class EObrazovanjeContractClient {
    private static final Logger log = LoggerFactory.getLogger(EObrazovanjeContractClient.class);

//    @Value("${ethereum.node.url}")
//    private String nodeUrl;
//
//    @Value("${contract.address}")
//    private String contractAddress;
//
//    String privateKey = System.getenv("CONTRACT_OWNER_PK");

    public String invokeContract(EthereumTransactionData data) throws IOException {

        String nodeUrl = "http://127.0.0.1:8545";
        String contractAddress = "0x47b976202A82a4E30b5A729D944221Ae91d7479B";
        String privateKey = System.getenv("CONTRACT_OWNER_PK");

        // Connect to Ethereum node
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));
        log.info("Connected to Ethereum client version: " +
                web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // Load credentials
        Credentials credentials = Credentials.create(privateKey);

        Utf8String studentId = data.getStudentId();
        Utf8String courseId = data.getCourseId();
        Uint256 grade = data.getGrade();
        Utf8String date = data.getDate();
        Uint256 professorId = data.getProfessorId();

        Function function = new Function(
                "addExamResult",
                Arrays.asList(studentId, courseId, grade, date, professorId),
                Collections.emptyList());

        // Encode the function data
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger gasLimit = BigInteger.valueOf(300000);
        BigInteger gasPrice = BigInteger.valueOf(1_000_000_000L);

        try {
            // Send the transaction
            String transactionHash = web3j.ethSendTransaction(Transaction.createFunctionCallTransaction(
                    credentials.getAddress(),
                    null,
                    gasPrice,
                    gasLimit,
                    contractAddress,
                    encodedFunction)).send().getTransactionHash();

            log.info("Transaction Hash: " + transactionHash);

            return transactionHash;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
