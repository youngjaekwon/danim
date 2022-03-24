<%--
  Created by IntelliJ IDEA.
  User: yjkwon
  Date: 2022/03/24
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js"></script>
</head>
<body>
<form name="naverLogin" action="/doNaverLogin" method="post">
    <input type="hidden" name="user"/>
</form>
<script>
    // 네이버 로그인
    var naverLogin = new naver.LoginWithNaverId(
        {
            clientId: "vvx373wzJPohrkuqHa9O",
            callbackUrl: "http://localhost:8000/naverLogin",
            isPopup: false, /* 팝업을 통한 연동처리 여부 */
        }
    );

    naverLogin.init();

    window.addEventListener('load', function () {
        naverLogin.getLoginStatus(function (status) {
            if (status) {
                $('[name=naverLogin] [name=user]').val(JSON.stringify(naverLogin.user));
                $('[name=naverLogin]').submit();
            } else {
                console.log("callback 처리에 실패하였습니다.");
            }
        });
    });
</script>
</body>
</html>
