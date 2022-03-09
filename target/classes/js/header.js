// 유저 모달
const userModal = document.getElementsByClassName("user-modal")[0];

// 유저 모달 여는 함수
function openUserModal(){
    // 유저 모달 열기
    userModal.style = "";
    // 유저 모달 이외의 영역 클릭시 닫는 기능 추가
    document.addEventListener("click", closeUserModal);
}

// 유저 모달 닫는 함수
const closeUserModal = (event) => {
    // 타겟 확인
    const target = event.target;
    // 로그인 모달이 포함하는 클래스들
    const classes =["user-modal", "mypage", "mypage-btn", "logout", "logout-btn"];

    // 타겟이 로그인 모달에 포함되는지 확인
    for (const classesKey of classes) {
        if (target.classList.value == classesKey) return;
    }

    // 로그인 모달 닫기
    userModal.style = "display: none";
    // 이벤트 제거
    document.removeEventListener("click", closeUserModal);
}

// 모든 모달 닫는 함수
function allClose(){
    closeLoginModal();
    closePasswordModal();
    closeChangePwdModal();
    closeIdModal();
    closeIdResultModal();
}

// 로그인 모달
const loginModal = document.getElementsByClassName("login-modal")[0];

// 로그인 모달 여는 함수
function openLoginModal(){
    allClose();
    loginModal.style = "";
}

// 로그인 모달 닫는 함수
function closeLoginModal(){
    document.loginForm.reset();
    loginModal.style = "display: none";
}

// 비밀번호 찾기 모달
const passwordModal = document.getElementsByClassName("password-modal")[0];

// 비밀번호 찾기 모달 여는 함수
function openPasswordModal(){
    allClose();
    passwordModal.style = "";
    $('#findPwdResult').html('비밀번호 찾기');
    $('#findPwdResult').css("color", "black");
    $('#findPwdResult').css("font-size", "25px");
}

// 비밀번호 찾기 모달 닫는 함수
function closePasswordModal(){
    document.passwordForm.reset();
    passwordModal.style = "display: none";
}

// 전화번호 중간자리
const mobileMiddleinFindPwd = document.getElementById("password-mobile2");
// 중간자리에 숫자 4개를 입력하면 뒷자리로 포커스하는 함수
mobileMiddleinFindPwd.onfocus = () => {
    mobileMiddleinFindPwd.addEventListener("keyup", () => {
        if (mobileMiddleinFindPwd.value.length == 4){
            document.getElementById("password-mobile3").focus();
        }
    })
}

let memnum = '';
let memEmail = '';

// 비밀번호 찾기 함수
function doFindPwd() {
    const findPwdEmail = $('[name=passwordForm] [name=password-id]').val();
    const findPwdMobile = $('[name=passwordForm] [name=password-mobile1]').val()
        + '-' + $('[name=passwordForm] [name=password-mobile2]').val()
        + '-' + $('[name=passwordForm] [name=password-mobile3]').val();
    $.ajax({
        url: "doFindPwd",
        type: "POST",
        data: {"email": findPwdEmail, "mobile": findPwdMobile}, // email, mobile 값을 map 형태로 전달
        success: function(data){
            if (data != 'failed'){ // 비밀번호 찾기 성공
                memnum = data;
                memEmail = findPwdEmail;
                openChangePwdModal();
            } else { // 비밀번호 찾기 실패
                $('#findPwdResult').html('조건에 맞는 아이디를 찾지 못하였습니다.');
                $('#findPwdResult').css("color", "red");
                $('#findPwdResult').css("font-size", "23px");
                return false; // false 리턴
            }
        }
    });
    return false;
}

// 비밀번호 변경 모달
const changePwdModal = document.getElementsByClassName("change-pwd-modal")[0];

// 비밀번호 변경 모달 여는 함수
function openChangePwdModal(){
    allClose();
    $('input[name=change-pwd-memnum]').val(memnum);
    $('input[name=change-pwd-id]').val(memEmail);
    changePwdModal.style = "";
}

// 비밀번호 변경 모달 닫는 함수
function closeChangePwdModal(){
    document.changePwdModal.reset();
    changePwdModal.style = "display: none";
}

const changePwd = document.getElementById("changePwd"); // 새로운 비밀번호
const changePwdCheck = document.getElementById("changePwdCheck"); // 비밀번호 확인
const checkPwd = document.getElementById("checkPwd"); // 결과 출력창

// 비밀번호 확인 input 포커스시 비밀번호 확인 기능 함수 추가
changePwdCheck.onfocus = () => {
    // 비밀번호 확인 창에서 keyup 이벤트 발생 시 비밀번호 확인하는 함수 추가
    changePwdCheck.addEventListener("keyup", doCheckPwd);
    // 새로운 비밀번호 창에서 keyup 이벤트 발생 시 비밀번호 확인하는 함수 추가
    changePwd.addEventListener("keyup", doCheckPwd);
}

// 비밀번호 input 포커스 시 유효성 검사 함수 추가
changePwd.onfocus = () => {
    changePwd.addEventListener("keyup", isPwdValidinChangePwd);
}

// 비밀번호 유효성 검사
function isPwdValidinChangePwd() {
    const pwd = changePwd.value; // 입력된 비밀번호
    const passwordValidCheckBox = document.getElementById("pwdValidCheckBoxinChangePwd"); // 검사 결과 창
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

// 비밀번호 확인 기능 함수
function doCheckPwd(){

    // 두 input 모두 비어있을 경우
    if (changePwd.value == "" && changePwdCheck.value == "") {
        checkPwd.innerHTML = ""; // 텍스트 삭제
        return false;
    }

    // 일치
    if (changePwd.value == changePwdCheck.value){
        checkPwd.style = "color: black";
        checkPwd.innerHTML = "비밀번호가 서로 일치합니다.";

    // 불일치
    } else {
        checkPwd.style = "color: red";
        checkPwd.innerHTML = "비밀번호가 서로 일치하지 않습니다.";
    }
    // 새로운 비밀번호 창에서 keyup 이벤트 발생 시 비밀번호 확인하는 함수 추가
    original.addEventListener("keyup", doCheckPwd);
}

// 비밀번호 변경 form이 유효한지 확인하는 함수
function isValidChangePwdForm() {
    // password가 비어있으면 password focus 후 false 리턴
    if (changePwd == '') {
        // 비어있으면 alert 출력 후 false return
        alert("이메일을 반드시 입력해주세요.");
        changePwd.focus();
        return false;
        // password가 유효하지 않으면 alert 출력, password focus 후 false 리턴
    } else if (!isPwdValidinChangePwd()) {
        alert("유효하지 않은 비밀번호 입니다.")
        changePwd.focus();
        return false;
        // password가 서로 맞지 않으면 alert 출력, password focus 후 false 리턴
    } else if (!checkPwdinSignup()) {
        alert("비밀번호가 서로 맞지 않습니다.")
        changePwdCheck.focus();
        return false;
    }
}

// 아이디 찾기 모달
const idModal = document.getElementsByClassName("id-modal")[0];

// 아이디 찾기 모달 여는 함수
function openIdModal(){
    allClose();
    idModal.style = "";
}

// 아이디 찾기 모달 닫는 함수
function closeIdModal(){
    idModal.style = "display: none";
}

// 전화번호 중간자리
const mobileMiddleinFindEmail = document.getElementById("id-mobile2");
// 중간자리에 숫자 4개를 입력하면 뒷자리로 포커스하는 함수
mobileMiddleinFindEmail.onfocus = () => {
    mobileMiddleinFindEmail.addEventListener("keyup", () => {
        if (mobileMiddleinFindEmail.value.length == 4){
            document.getElementById("id-mobile3").focus();
        }
    })
}

let foundEmail = '';

// 아이디 찾기 함수
function doFindEmail() {
    const findEmailName = $('[name=idForm] [name=id-name]').val();
    const findEmailMobile = $('[name=idForm] [name=id-mobile1]').val()
        + '-' + $('[name=idForm] [name=id-mobile2]').val()
        + '-' + $('[name=idForm] [name=id-mobile3]').val();
    console.log(findEmailName);
    console.log(findEmailMobile);
    $.ajax({
        url: "doFindEmail",
        type: "POST",
        data: {"name": findEmailName, "mobile": findEmailMobile}, // email, mobile 값을 map 형태로 전달
        success: function(data){
            if (data != 'failed'){ // 비밀번호 찾기 성공
                foundEmail = data;
                openIdResultModal();
            } else { // 비밀번호 찾기 실패
                $('#findEmailResult').html('조건에 맞는 아이디를 찾지 못하였습니다.');
                $('#findEmailResult').css("color", "red");
                $('#findEmailResult').css("font-size", "23px");
                return false; // false 리턴
            }
        }
    });
    return false;
}

// 아이디 찾기 결과 모달
const idResultModal = document.getElementsByClassName("id-result-modal")[0];

// 아이디 찾기 결과 모달 여는 함수
function openIdResultModal(){
    allClose();
    $('input[name=idResult]').val(foundEmail);
    idResultModal.style = "";
}

// 아이디 찾기 결과 모달 닫는 함수
function closeIdResultModal(){
    idResultModal.style = "display: none";
}

// 아이디 찾기 결과 포함하여 로그인하는 함수
function openLoginWithIdResult(){
    openLoginModal();
    document.loginForm.id.value = document.idResultForm.idResult.value;
}


