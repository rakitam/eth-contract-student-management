// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;

/**
 * @title EObrazovanje
 * @dev Store & retrieve value in a variable
 * @custom:dev-run-script ./scripts/deploy_with_ethers.ts
 */
contract EObrazovanje {

    address public owner;

    struct ExamHistory {
        uint256 studentId;
        uint256 examId;
        uint256 grade;
        uint256 date;
        uint256 professorId;
    }

    ExamHistory[] public examHistory;
    event ExamStored(uint256 indexed studentId, uint256 indexed examId, uint256 grade, uint256 date, uint256 professorId);

    /**
     * @dev Constructor to set the deployer as the owner
     */
    constructor() {
        owner = msg.sender;
    }

    /**
     * @dev Store value in variable
     * @param _studentId value to store
     * @param _examId Exam ID to store
     * @param _grade Grade to store
     * @param _date Date to store
     * @param _professorId Professor ID to store
     */
    function store(uint256 _studentId, uint256 _examId, uint256 _grade, uint256 _date, uint256  _professorId) public {
        require(msg.sender == owner, "Only the owner can store exam data");

        ExamHistory memory newExam = ExamHistory(_studentId, _examId, _grade, _date, _professorId);
        examHistory.push(newExam);

        emit ExamStored(_studentId, _examId, _grade, _date, _professorId);
    }
    
    /**
     * @dev Return value 
     * @return value of 'studentId, examId, grade, date, professorId'
     */
    function retrieveLatestExam() public view returns (uint256, uint256, uint256, uint256, uint256) {
        require(examHistory.length > 0, "No exams recorded yet");
        ExamHistory memory latestExam = examHistory[examHistory.length - 1];
        return (latestExam.studentId, latestExam.examId, latestExam.grade, latestExam.date, latestExam.professorId);
    }
}