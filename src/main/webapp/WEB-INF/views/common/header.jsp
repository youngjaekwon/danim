<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name ="google-signin-client_id" content="598157732367-l9c5qv6hkl06uf21ov45muv74v7jbdhh.apps.googleusercontent.com">


    <link rel="stylesheet" href="/resources/css/header.css">
    <!-- fontawesome v5 cdn -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

    <title>다님 : 여행을 다니다</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="/resources/js/header.js" defer></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js"></script>
    <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
</head>
<body>
<header>
    <div class="header_inner">
        <div class="logo">
            <a href="/">logo</a>
        </div>
        <div class="title">
            <a href="/">title</a>
        </div>
        <div class="nav">
            <ul>
                <li><a href="#">여행지</a></li>
                <li><a href="#">숙박</a></li>
                <li><a href="#">교통</a></li>
                <li><a href="#">즐길거리</a></li>
                <li><a href="#">shop</a></li>
                <li><a href="#">고객센터</a></li>
            </ul>
        </div>
        <!-- index 이외에서 검색 -->
        <div class="header-search" style="display: none">
            <input class="header-search-input" type="text">
        </div>
        <script>
            // index 페이지 이외에서 검색창 활성화
            const thisURI = '<%=request.getRequestURI()%>'
            if (thisURI != '/WEB-INF/views/index.jsp') $('.header-search').eq(0).prop("style", "");
        </script>
        <!-- 로그인 전 -->
        <sec:authorize access="!isAuthenticated()">
            <div class="before-login" id="beforeLogin">
                <a href="javascript:openLoginModal()" class="login_btn">로그인</a>
            </div>
        </sec:authorize>
        <!-- 로그인 후 -->
        <sec:authorize access="isAuthenticated()">
            <div class="after-login" id="afterLogin">
                <a href="javascript:openUserModal()" class="header-user"><i class="fas fa-user"></i></a>
                <a href="/shop/basket" class="header-basket"><i class="fas fa-shopping-cart"></i></a>
            </div>
        </sec:authorize>
        <!-- 유저버튼 누를시 -->
        <div class="user-modal" style="display: none">
            <div class="mypage">
                <sec:authorize access="hasRole('MEMBER')">
                    <a href="/member/mypage" class="mypage-btn">마이페이지</a>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <a href="/admin/orders" class="mypage-btn">관리자페이지</a>
                </sec:authorize>
            </div>
            <div class="logout">
                <a href="/member/doLogout" class="logout-btn">로그아웃</a>
            </div>
        </div>
    </div>
    <!-- 로그인 모달 -->
    <div class="login-modal" style="display: none">
        <div class="login-modal-inside">
            <a href="javascript:closeLoginModal()" class="modal-logo">logo</a>
            <form action="/login" class="modal-form" name="loginForm" method="post">
                <input type="email" name="id" placeholder="이메일을 입력하세요" class="login-input">
                <input type="password" name="password" placeholder="비밀번호를 입력하세요" class="login-input">
                <input type="submit" value="로그인" class="login-submit">
            </form>
            <div class="social">
                <a href="javascript:void(0)" id="GgCustomLogin" class="google">google</a>
                <a href="javascript:void(0)" id="naverIdLogin_loginButton" class="naver">naver</a>
                <a href="javascript:kakaoLogin()" class="kakao">kakao</a>
            </div>
            <div class="modal-bottom">
                <ul>
                    <li><a href="javascript:openPasswordModal()">비밀번호 찾기</a></li>
                    <li><a href="javascript:openIdModal()">아이디 찾기</a></li>
                    <li><a href="signup">회원가입</a></li>
                </ul>
            </div>
            <a href="javascript:closeLoginModal()" class="login-close"><i class="far fa-times-circle"></i></a>
        </div>
    </div>
    <!-- 비밀번호 찾기 모달 -->
    <div class="password-modal" style="display: none">
        <div class="password-modal-inside">
            <p id="findPwdResult">비밀번호 찾기</p>
            <!-- 비밀번호 찾기 실패 시 : innerHtml을 "조건에 맞는 아이디를 찾지 못하였습니다."로 변경 -->
            <form action="javascript:doFindPwd()" class="modal-form" name="passwordForm" method="post">
                <label for="password-id">이메일</label>
                <input type="email" id="password-id" name="password-id" placeholder="이메일을 입력하세요" class="login-input">
                <label for="password-mobile2">전화번호</label>
                <select name="password-mobile1" id="password-mobile1" class="mobile1">
                    <option value="010">010</option>
                    <option value="011">011</option>
                    <option value="016">016</option>
                    <option value="017">017</option>
                    <option value="018">018</option>
                    <option value="019">019</option>
                </select>
                <span>-</span>
                <input type="text" name="password-mobile2" id="password-mobile2" class="mobile2" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                <span>-</span>
                <input type="text" name="password-mobile3" id="password-mobile3" class="mobile3" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                <input type="submit" value="찾기" class="password-submit">
            </form>
            <div class="modal-bottom">
                <ul>
                    <li><a href="javascript:openLoginModal()">로그인</a></li>
                    <li><a href="javascript:openIdModal()">아이디 찾기</a></li>
                    <li><a href="signup">회원가입</a></li>
                </ul>
            </div>
            <a href="javascript:closePasswordModal()" class="password-close"><i class="far fa-times-circle"></i></a>
        </div>
    </div>
    <!-- 비밀번호 변경 모달 -->
    <div class="change-pwd-modal" style="display: none">
        <div class="password-modal-inside">
            <p>비밀번호 변경</p>
            <form action="doChangePwd" class="modal-form" name="changePwdModal" onsubmit="return isValidChangePwdForm()" method="post">
                <input type="text" name="change-pwd-memnum" hidden>
                <input type="text" name="change-pwd-id" class="login-input" disabled>
                <input type="password" id="changePwd" name="change-pwd-pwd" placeholder="새 비밀번호를 입력하세요" class="login-input">
                <div id="pwdValidCheckBoxinChangePwd"></div>
                <input type="password" id="changePwdCheck" name="change-pwd-pwd-check" placeholder="비밀번호 확인" class="login-input">
                <div id="checkPwd"></div>
                <input type="submit" value="변경" class="change-pwd-submit">
            </form>
            <a href="javascript:closeChangePwdModal()" class="change-pwd-close"><i class="far fa-times-circle"></i></a>
        </div>
    </div>
    <!-- 아이디 찾기 모달 -->
    <div class="id-modal" style="display: none">
        <div class="id-modal-inside">
            <p id="findEmailResult">아이디 찾기</p>
            <!-- 아이디 찾기 실패 시 : innerHtml을 "조건에 맞는 아이디를 찾지 못하였습니다."로 변경 -->
            <form action="javascript:doFindEmail()" class="modal-form" name="idForm">
                <label for="id-name">이름</label>
                <input type="text" id="id-name" name="id-name" placeholder="이름을 입력하세요" class="login-input">
                <label for="id-mobile2">전화번호</label>
                <select name="id-mobile1" class="mobile1">
                    <option value="010">010</option>
                    <option value="011">011</option>
                    <option value="016">016</option>
                    <option value="017">017</option>
                    <option value="018">018</option>
                    <option value="019">019</option>
                </select>
                <span>-</span>
                <input type="text" name="id-mobile2" id="id-mobile2" class="mobile2" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                <span>-</span>
                <input type="text" name="id-mobile3" id="id-mobile3" class="mobile3" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                <input type="submit" value="찾기" class="id-submit">
            </form>
            <div class="modal-bottom">
                <ul>
                    <li><a href="javascript:openLoginModal()">로그인</a></li>
                    <li><a href="javascript:openPasswordModal()">비밀번호 찾기</a></li>
                    <li><a href="signup">회원가입</a></li>
                </ul>
            </div>
            <a href="javascript:closeIdModal()" class="id-close"><i class="far fa-times-circle"></i></a>
        </div>
    </div>
    <!-- 아이디 찾기 결과 모달 -->
    <div class="id-result-modal" style="display: none">
        <div class="id-modal-inside">
            <p>아이디 찾기 결과</p>
            <form action="" class="modal-form" id="id-result-form" name="idResultForm" method="post">
                <input type="email" name="idResult" class="login-input" readonly>
            </form>
            <div class="modal-bottom">
                <ul>
                    <li><a href="javascript:openLoginWithIdResult()">로그인</a></li>
                    <li><a href="javascript:openPasswordModal()">비밀번호 찾기</a></li>
                    <li><a href="signup">회원가입</a></li>
                </ul>
            </div>
            <a href="#" class="change-pwd-close"><i class="far fa-times-circle"></i></a>
        </div>
    </div>
</header>
<script>
    // 로그인 실패시
    if ('${loginCheck}' == 'failed' || '${param.get("loginCheck")}' == 'failed') alert("로그인 실패");
    else if ('${loginCheck}' == 'false'){
        alert("로그인 후 이용바랍니다.");
        $('.login-modal').first().toggle();
    }
    // 로그인 성공시 (회원 가입 시도한 상태가 아닐때)
    else if ('${signup}' == '' && '${param.get("loginCheck")}' == 'true') {
        alert("로그인 성공");
    }

    // 로그아웃 성공시
    if ('${param.get("logout")}' == 'true') {
        alert("로그아웃");
    }

    // 관리자 권한이 없는 경우
    if ('${isAdmin}' == 'false') alert('권한이 없습니다.');

    // 회원가입 성공시
    if ('${signup}' == 'passed') {
        alert("성공적으로 가입했습니다.");
    }
    // 회원가입 실패시
    else if ('${signup}' == 'failed') alert("회원가입에 실패했습니다.");

    // 비밀 번호 변경 성공시
    if ('${doChangePwd}' == 'passed'){
        alert("비밀번호를 성공적으로 변경했습니다.");
    } else if ('${doChangePwd}' == 'failed') {
        alert("비밀번호 변경에 실패했습니다.");
    }

    // 회원 탈퇴 성공시
    if ('${signout}' == 'passed'){
        alert("회원탈퇴에 성공했습니다.");
    } else if ('${signout}' == 'failed'){
        alert("회원탈퇴에 실패했습니다.");
    } else if ('${signout}' == 'pwdMismatch'){
        alert("비밀번호가 맞지 않습니다.");
    }

    // 잘못된 경로로 접근한 경우
    if ('${invalidAccess}' == 'true'){
        alert("잘못된 접근");
    }

    // 주문 성공/실패
    const isCheckedOut = '${checkOut}'; // 체크아웃 성공여부
    if (isCheckedOut == 'passed') {
        alert("주문처리에 성공했습니다.");
    } else if (isCheckedOut == 'failed') {
        alert("주문처리에 실패했습니다.");
    }
</script>
<%
    session.removeAttribute("loginCheck"); // 로그인 확인 attribute 제거
%>
<script src="https://apis.google.com/js/api:client.js"></script>
<form name="socialLogin" method="post">
    <input type="hidden" name="user"/>
</form>
</body>
</html>
