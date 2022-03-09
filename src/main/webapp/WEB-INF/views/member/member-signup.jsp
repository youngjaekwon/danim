<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<%request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/member-signup.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="/resources/js/member-signup.js" defer></script>

</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <div class="main-inner">
    <p>Sign Up</p>
    <form action="/doSignup" method="post" name="signupForm" onsubmit="return isValidForm('signupForm')" accept-charset="UTF-8">
      <div class="member-info">
        <label for="email1">Email</label>
        <div>
          <input type="text" name="email1" id="email1">
          <span>@</span>
          <input type="text" name="email2" id="email2" value="naver.com">
          <select name="emailSelect" id="emailSelect">
            <option value="naver.com">naver.com</option>
            <option value="gmail.com">gmail.com</option>
            <option value="hanmail.net">hanmail.net</option>
            <option value="nate.com">nate.com</option>
            <option value="hotmail">hotmail.com</option>
            <option value="live.com">live.com</option>
            <option value="none">직접입력</option>
          </select>
          <div id="emailCheckBox"></div>
        </div>
        <label for="pwd">Password</label>
          <input type="password" name="pwd" id="pwd" />
          <div id="passwordValidCheckBox">대문자, 특수문자를 포함하여 8자 이상, 16자 이하</div>
        <label for="passwordCheck">Confirm Password</label>
        <div>
          <input type="password" name="passwordCheck" id="passwordCheck" />
          <div id="passwordCheckBox"></div>
        </div>
        <label for="name">Name</label>
        <input type="text" name="name" id="name" />
        <label for="nickname">Nickname</label>
        <input type="text" name="nickname" id="nickname" />
        <label for="zipcode">Zipcode</label>
        <input type="text" name="zipcode" id="zipcode" readonly>
        <a href="javascript:findAddr()" id="searchAddr">SEARCH</a>
        <label for="addr">Address</label>
        <input type="text" name="addr" id="addr" readonly>
        <input type="text" name="addrDetail" id="addrDetail">
        <label for="mobile2">Mobile</label>
        <select name="mobile1" id="mobile1">
          <option value="010">010</option>
          <option value="011">011</option>
          <option value="016">016</option>
          <option value="017">017</option>
          <option value="018">018</option>
          <option value="019">019</option>
        </select>
        <span>-</span>
        <input type="text" name="mobile2" id="mobile2" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
        <span>-</span>
        <input type="text" name="mobile3" id="mobile3" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
      </div>
      <input type="submit" id="signupSubmit" value="Register">
    </form>
  </div>
</section>
</body>

</html>
