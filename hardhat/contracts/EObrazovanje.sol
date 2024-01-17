// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;

/**
 * @title EObrazovanje
 * @dev Store & retrieve value in a variable
 * @custom:dev-run-script ./scripts/deploy_with_ethers.ts
 */
contract EObrazovanje {

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
     * @dev Store value in variable
     * @param _studentId Student ID to store
     * @param _courseId Course ID to store
     * @param _grade Grade to store
     * @param _date Date to store
     * @param _professorId Professor ID to store
     */
    function store(string memory _studentId, string memory _courseId, uint256 _grade, string memory  _date, uint256  _professorId) public {
        ExamHistory memory newExam = ExamHistory(_studentId, _courseId, _grade, _date, _professorId);
        examHistory.push(newExam);

        emit ExamStored(_studentId, _courseId, _grade, _date, _professorId);
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