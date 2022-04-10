const email1 = $('#email1');// 이메일 앞자리 input
const email2 = $('#email2'); // 이메일 뒷자리 input
const emailSelect = $('#emailSelect') ; // 이메일 select
const emailCheckBox = $('#emailCheckBox'); // 이메일 중복검사 결과 div

email2.prop('readonly', true); // 이메일 뒷자리 readOnly 추가
// 이메일 select 변경시 동작 함수
emailSelect.on('change', () => {
    const emailSelectedValue = emailSelect.val(); // 이메일 select 값
    if (emailSelectedValue == "none") { // none일 경우
        email2.val(""); // value 초기화
        email2.prop('readonly', false); // readOnly 해제
    } else { // none 이외
        email2.val(emailSelectedValue); // 이메일 뒷자리 내용 변경
        email2.prop('readonly', true); // readOnly 추가
    }
})

// 이벤트 발생시 이메일 중복 검사 함수 추가 ///////////////////////////
// email 앞자리 input에서 keyup시
email1.on('keyup', function (){
    isDuplicatedEmail();
});
// email select 변경시
emailSelect.on('change', function (){
    isDuplicatedEmail();
});
// email 뒷자리 input에서 keyup시 (email select 직접입력 상태)
email2.on('keyup', function (){
    isDuplicatedEmail();
});
//////////////////////////////////////////////////////////////
// 이메일 중복 검사 함수
function isDuplicatedEmail() {
    $.ajax({
        url: "isDuplicatedEmail",
        type: "POST",
        data: {"email" : email1.val() + "@" + email2.val()}, // email 값을 map 형태로 전달
        success: function(data){
            if (data == 'true'){ // 중복되었을 경우
                emailCheckBox.text("이메일이 중복되었습니다."); // div 내용 변경
                emailCheckBox.css("color", "red"); // 글자색 변경
                return true; // true 리턴
            } else if (email1.val() == ''){
                emailCheckBox.text("이메일이 비어있습니다."); // div 내용 변경
                emailCheckBox.css("color", "red"); // 글자색 변경
                return true; // true 리턴
            } else { // 중복이 안되었을 경우
                emailCheckBox.text("사용가능한 이메일입니다."); // div 내용 변경
                emailCheckBox.css("color", "black"); // 글자색 변경
                return false; // false 리턴
            }
        }
    });
    return false;
}

const original = document.getElementById("pwd"); // 비밀번호 input
const passwordCheck = document.getElementById("passwordCheck"); // 비밀번호 확인 input

// 비밀번호 input 포커스 시 유효성 검사 함수 추가
original.onfocus = () => {
    original.addEventListener("keyup", isPwdValid);
}

// 비밀번호 유효성 검사
function isPwdValid() {
    const pwd = original.value; // 입력된 비밀번호
    const passwordValidCheckBox = document.getElementById("passwordValidCheckBox"); // 검사 결과 창
    // 대문자, 특수문자를 포함하여 8~16자 불충족
    if (!/(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/g.test(pwd)) {
        passwordValidCheckBox.style = "color: red";
        passwordValidCheckBox.innerHTML = "비밀번호가 유효하지 않습니다. / 대문자, 특수문자를 포함하여 8자 이상, 16자 이하";
        return false;
        // 충족
    } else {
        passwordValidCheckBox.style = "color: black";
        passwordValidCheckBox.innerHTML = "비밀번호가 유효합니다.";
        return true;
    }
}


// 비밀번호 확인 input 포커스시 비밀번호 확인 기능 함수 추가
passwordCheck.onfocus = () => {
    // 비밀번호 확인 창에서 keyup 이벤트 발생 시 비밀번호 확인하는 함수 추가
    passwordCheck.addEventListener("keyup", checkPwdinSignup);
    // 새로운 비밀번호 창에서 keyup 이벤트 발생 시 비밀번호 확인하는 함수 추가
    original.addEventListener("keyup", checkPwdinSignup);
}

// 비밀번호 확인 기능 함수
function checkPwdinSignup() {

    const checkPwdBox = document.getElementById("passwordCheckBox"); // 결과 출력창

    // 두 input 모두 비어있을 경우
    if (original.value == "" && passwordCheck.value == "") {
        checkPwdBox.innerHTML = ""; // 텍스트 삭제
        return false;
    }
    // 일치
    else if (original.value == passwordCheck.value) {
        checkPwdBox.style = "color: black";
        checkPwdBox.innerHTML = "비밀번호가 서로 일치합니다.";
        return true;
    }
    // 불일치
    else {
        checkPwdBox.style = "color: red";
        checkPwdBox.innerHTML = "비밀번호가 서로 일치하지 않습니다.";
        return false;
    }
}

// 카카오 지도 api 실행 함수
function findAddr() {
    new daum.Postcode({
        oncomplete: function (data) {
            const roadAddr = data.roadAddress; // 도로명 주소
            const jibunAddr = data.jibunAddress; // 지번 주소

            document.getElementById('zipcode').value = data.zonecode; // 우편번호 할당
            if (roadAddr !== '') {
                document.getElementById("addr").value = roadAddr; // 도로명주소 선택시 할당
            } else if (jibunAddr !== '') {
                document.getElementById("addr").value = jibunAddr; // 지번주소 선택시 할당
            }
        }
    }).open();
}

// 주소 검색 시, 상세주소 입력란에 placeholder 추가
document.getElementById("searchAddr").onclick = () => {
    document.getElementById("addrDetail").placeholder = "상세주소를 입력해주세요.";
}

// 전화번호 중간자리
const mobileMiddle = document.getElementById("mobile2");
// 중간자리에 숫자 4개를 입력하면 뒷자리로 포커스하는 함수
mobileMiddle.onfocus = () => {
    mobileMiddle.addEventListener("keyup", () => {
        if (mobileMiddle.value.length == 4){
            document.getElementById("mobile3").focus();
        }
    })
}

// submit전 form이 유효한지 확인하는 함수
function isValidForm(formName){
    // 이메일이 중복되었으면 alert 출력, email1 focus 후 false 리턴
    if (isDuplicatedEmail()){
        if (isValidEmail(document.forms[formName]['email1'].value)) { //email1이 비어있으면 email1 focus 후 false 리턴
            alert("이메일이 중복되었습니다.");
        }
        email1.focus();
        return false;
    // email1이 비어있으면 email1 focus 후 false 리턴
    } else if (!isValidEmail(document.forms[formName]['email1'].value)){
        document.getElementById("email1").focus();
        return false;
    // email2가 비어있으면 email2 focus 후 false 리턴
    } else if (!isValidEmailSecond(document.forms[formName]['email2'].value)){
        document.getElementById("email2").focus();
        return false;
    // password가 비어있으면 password focus 후 false 리턴
    } else if (!isValidPassword(document.forms[formName]['pwd'].value)){
        document.getElementById("pwd").focus();
        return false;
    // password가 유효하지 않으면 alert 출력, password focus 후 false 리턴
    } else if (!isPwdValid()){
        alert("유효하지 않은 비밀번호 입니다.")
        document.getElementById("pwd").focus();
        return false;
    // password가 서로 맞지 않으면 alert 출력, password focus 후 false 리턴
    } else if (!checkPwdinSignup()){
        alert("비밀번호가 서로 맞지 않습니다.")
        document.getElementById("passwordCheck").focus();
        return false;
    // name이 비어있으면 name focus 후 false 리턴
    } else if (!isValidName(document.forms[formName]['name'].value)){
        document.getElementById("name").focus();
        return false;
    // nickname이 비어있으면 nickname focus 후 false 리턴
    } else if (!isValidNickname(document.forms[formName]['nickname'].value)){
        document.getElementById("nickname").focus();
        return false;
    // signupZipcode가 비어있으면 searchAddr click 후 false 리턴
    } else if (!isValidAdrr(document.forms[formName]['zipcode'].value)){
        document.getElementById("searchAddr").click();
        return false;
    // signupAddr가 비어있으면 searchAddr click 후 false 리턴
    } else if (!isValidAdrr(document.forms[formName]['addr'].value)){
        document.getElementById("searchAddr").click();
        return false;
    // signupAddrDetail이 비어있으면 signupAddrDetail focus 후 false 리턴
    } else if (!isValidAdrrDetail(document.forms[formName]['addrDetail'].value)){
        document.getElementById("addrDetail").focus();
        return false;
    // signupMobile2가 비어있으면 signupMobile2 focus 후 false 리턴
    } else if (!isValidMobileMiddle(document.forms[formName]['mobile2'].value)){
        document.getElementById("mobile2").focus();
        return false;
    // signupMobile3가 비어있으면 signupMobile3 focus 후 false 리턴
    } else if (!isValidMobileLast(document.forms[formName]['mobile3'].value)){
        document.getElementById("mobile3").focus();
        return false;
    }
    return true;
}

// email 유효성 검사 함수
function isValidEmail(email){
    if (email == ''){
        // 비어있으면 alert 출력 후 false return
        alert("이메일을 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// email 뒷자리 유효성 검사 함수
function isValidEmailSecond(email){
    if (email == ''){
        // 비어있으면 alert 출력 후 false return
        alert("이메일을 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// password 유효성 검사 함수
function isValidPassword(password){
    if (password == ''){
        // 비어있으면 alert 출력 후 false return
        alert("비밀번호를 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// name 유효성 검사 함수
function isValidName(name){
    if (name == ''){
        // 비어있으면 alert 출력 후 false return
        alert("이름을 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// nickname 유효성 검사 함수
function isValidNickname(nickname){
    if (nickname == ''){
        // 비어있으면 alert 출력 후 false return
        alert("닉네임을 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// 주소 유효성 검사 함수
function isValidAdrr(adrr){
    if (adrr == ''){
        // 비어있으면 alert 출력 후 false return
        alert("주소검색을 진행해주세요.");
        return false;
    }
    return true;
}

// 상세주소 유효성 검사 함수
function isValidAdrrDetail(adrr){
    if (adrr == ''){
        // 비어있으면 alert 출력 후 false return
        alert("상세주소를 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// 전화번호 앞자리 유효성 검사 함수
function isValidMobileMiddle(num){
    if (num == ''){
        // 비어있으면 alert 출력 후 false return
        alert("전화번호를 반드시 입력해주세요.");
        return false;
    }
    return true;
}

// 전화번호 뒷자리 유효성 검사 함수
function isValidMobileLast(adrr){
    if (adrr == ''){
        // 비어있으면 alert 출력 후 false return
        alert("전화번호를 반드시 입력해주세요.");
        return false;
    }
    return true;
}

