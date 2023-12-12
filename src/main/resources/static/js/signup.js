function validateInput(inputId, regex, errorMessage) {
    const input = document.getElementById(inputId);
    const messageId = `${inputId}Message`;
    const message = document.getElementById(messageId);

    if (regex.test(input.value)) {
        message.textContent = '올바릅니다';
        message.className = 'message';
    } else {
        message.textContent = errorMessage;
        message.className = 'message error';
    }
}

function validateForm() {
    const memberId = document.getElementById('memberId');
    const memberPw = document.getElementById('memberPw');
    const memberEmail = document.getElementById('memberEmail');
    const memberName = document.getElementById('memberName');
    const memberNickname = document.getElementById('memberNickname');
    const memberPhone = document.getElementById('memberPhone');
    const memberBirth = document.getElementById('memberBirth');
    const address = document.getElementById('sample6_detailAddress');

    // 각 필드에 대한 유효성 검사
    if (
        !validateInputWithMessage(memberId, /^[a-z0-9_\-]{5,20}$/, '5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.') ||
        !validateInputWithMessage(memberPw, /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,}$/, '영문+숫자+특수문자 포함 8자 이상.') ||
        !validateInputWithMessage(memberName, /^.{2,10}$/, '2자 이상, 10자 이하.') ||
        !validateInputWithMessage(memberNickname, /^.{2,10}$/, '2자 이상, 10자 이하.') ||
        !validateInputWithMessage(memberPhone, /^\d{3}\d{4}\d{4}$/, '010-1234-5678 형식.') ||
        !validateInputWithMessage(memberBirth, /^\d{8}$/, '생년월일 8자리.') ||
        !validateInputWithMessage(address, /^.{1,}$/, '상세주소를 입력해주세요.')
    ) {
        return false;
    }

    return true;
}

function validateInputWithMessage(input, regex, errorMessage) {
    const messageId = `${input.id}Message`;
    const message = document.getElementById(messageId);

    if (regex.test(input.value)) {
        message.textContent = '올바릅니다';
        message.className = 'message';
        return true;
    } else {
        message.textContent = errorMessage;
        message.className = 'message error';
        return false;
    }
}

var code2 = "";
$("#phoneChk").click(function(){
    alert("인증번호 발송이 완료되었습니다.\n휴대폰에서 인증번호 확인을 해주십시오.");
    var phone = $("#phone").val();
    $.ajax({
        type:"GET",
        url:"phoneCheck?phone=" + phone,
        cache : false,
        success:function(data){
            if(data == "error"){
                alert("휴대폰 번호가 올바르지 않습니다.")
                $(".successPhoneChk").text("유효한 번호를 입력해주세요.");
                $(".successPhoneChk").css("color","red");
                $("#phone").attr("autofocus",true);
            }else{
                $("#phone2").attr("disabled",false);
                $("#phoneChk2").css("display","inline-block");
                $(".successPhoneChk").text("인증번호를 입력한 뒤 본인인증을 눌러주십시오.");
                $(".successPhoneChk").css("color","green");
                $("#phone").attr("readonly",true);
                code2 = data;
            }
        }
    });
});