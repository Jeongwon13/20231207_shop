function isValidInput(value, fieldName) {
    // 값이 비어있지 않은지 확인
    if (value.trim() === '') {
        alert(fieldName + '을(를) 올바르게 입력해주세요.');
        return false;
    }

    return true;
}

function submitForm() {
    // 입력 필드 값 가져오기
    var memberId = document.getElementById('memberId').value;
    var memberPw = document.getElementById('memberPw').value;
    var memberEmail = document.getElementById('memberEmail').value;
    var memberName = document.getElementById('memberName').value;
    var memberNickname = document.getElementById('memberNickname').value;
    var memberPhone = document.getElementById('memberPhone').value;
    var memberBirth = document.getElementById('memberBirth').value;
    var address = document.getElementById('address').value;

    // 모든 필드가 비어있는지 확인
    if (!isValidInput(memberId, "아이디") ||
        !isValidInput(memberPw, "비밀번호") ||
        !isValidInput(memberEmail, "이메일") ||
        !isValidInput(memberName, "이름") ||
        !isValidInput(memberNickname, "닉네임") ||
        !isValidInput(memberPhone, "전화번호") ||
        !isValidInput(memberBirth, "생년월일") ||
        !isValidInput(address, "주소")) {
        return;
    }

    // 여기에 회원가입 로직을 추가하세요.
    // ...

    // 회원가입이 성공하면 메시지 표시
    // alert('회원가입이 완료되었습니다.\n아이디: ' + memberId + '\n이메일: ' + memberEmail);
}
