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
        string studentId;
        string courseId;
        uint256 grade;
        string date;
        uint256 professorId;
    }

    ExamHistory[] public examHistory;
    event ExamStored(string indexed studentId, string indexed courseId, uint256 grade, string date, uint256 professorId);

    /**
     * @dev Constructor to set the deployer as the owner
     */
    constructor() {
        owner = msg.sender;
    }

    /**
     * @dev Store value in variable
     * @param studentId value to store
     * @param courseId Exam ID to store
     * @param grade Grade to store
     * @param date Date to store
     * @param professorId Professor ID to store
     */
    function addExamResult(string calldata studentId,
    string calldata courseId,
    uint256 grade,
    string calldata date,
    uint256 professorId) public {
        require(msg.sender == owner, "Only the owner can store exam data");

        ExamHistory memory newExam = ExamHistory(studentId, courseId, grade, date, professorId);
        examHistory.push(newExam);

        emit ExamStored(studentId, courseId, grade, date, professorId);
    }
    
    /**
     * @dev Return value 
     * @return value of 'studentId, courseId, grade, date, professorId'
     */
    function retrieveLatestExam() public view returns (string memory, string memory, uint256, string memory, uint256) {
        require(examHistory.length > 0, "No exams recorded yet");
        ExamHistory memory latestExam = examHistory[examHistory.length - 1];
        return (latestExam.studentId, latestExam.courseId, latestExam.grade, latestExam.date, latestExam.professorId);
    }
}