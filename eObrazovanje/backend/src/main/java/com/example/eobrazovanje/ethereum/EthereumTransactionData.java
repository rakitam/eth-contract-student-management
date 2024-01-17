package com.example.eobrazovanje.ethereum;

import lombok.Data;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;


@Data
public class EthereumTransactionData {
        private Utf8String studentId;
        private Utf8String  courseId;
        private Uint256 grade;
        private Utf8String  date;
        private Uint256 professorId;
}
