<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/member-mypage.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/member-mypage.js" defer></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <div class="main-inner">
    <div class="main-left">
      <div class="main-nav">
        <ul>
          <li><a href="/member/mypage">회원정보 수정</a></li>
          <li><a href="/member/orderList">주문내역</a></li>
          <li><a href="/member/qnaList">1:1 문의</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>Edit Account</p>
      <form action="/member/doModifyMemberInfo" method="post" name="editAccountForm" onsubmit="return isValidForm('editAccountForm')">
        <div class="member-info">
          <label for="email">Email</label>
          <div>
            <input type="text" name="email" id="email" value="${userInfo.email}" disabled>
          </div>
          <label for="pwd">Current Password</label>
          <input type="password" name="pwd" id="pwd" />
          <label for="newPassword">New Password</label>
          <input type="password" name="newPassword" id="newPassword" />
          <div id="passwordValidCheckBox">대문자, 특수문자를 포함하여 8자 이상, 16자 이하</div>
          <label for="passwordCheck">Confirm Password</label>
          <input type="password" name="passwordCheck" id="passwordCheck" />
          <div id="passwordCheckBox"></div>
          <label for="name">Name</label>
          <input type="text" name="name" id="name" value="${userInfo.name}" disabled />
          <label for="nickname">Nickname</label>
          <input type="text" name="nickname" id="nickname" value="${userInfo.nickname}" />
          <label for="zipcode">Zipcode</label>
          <input type="text" name="zipcode" id="zipcode" value="${userInfo.zipcode}" readonly>
          <a href="javascript:findAddr()" id="searchAddr">SEARCH</a>
          <label for="addrDetail">Address</label>
          <input type="text" name="addr" id="addr" value="${userInfo.addr}" readonly>
          <input type="text" name="addrDetail" id="addrDetail" value="${userInfo.addrDetail}">
          <label for="mobile2">Mobile</label>
          <select name="mobile1" id="mobile1">
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
          </select>
          <script>
            $('#mobile1').val('${userInfo.mobile1}').prop("selected", true);
          </script>
          <span>-</span>
          <input type="text" name="mobile2" id="mobile2" value="${userInfo.mobile2}" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
          <span>-</span>
          <input type="text" name="mobile3" id="mobile3" value="${userInfo.mobile3}" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
        </div>
        <input type="submit" id="submit" value="Confirm">
        <a href="javascript:openSignoutModal()" class="signout">회원 탈퇴</a>
      </form>
      <div class="innerSignout" id="signoutModal" style="display: none">
        <div class="innerSignout">
          <p class="innerSignout">회원 탈퇴 하시겠습니까?</p>
          <form action="/member/doSignout" name="signout" method="post" onsubmit="return isValidSignout('signout')">
            <label for="signout-password" class="innerSignout">Password</label>
            <input type="password" name="signout-password" id="signout-password" class="innerSignout" />
            <input type="submit" id="signout-submit" value="탈퇴" class="innerSignout">
          </form>
        </div>
      </div>
    </div>
  </div>
</section>
<script>
  let modify = '${modify}';
  if (modify == "passed") alert("회원정보 수정 성공.");
  else if (modify == "pwdMisMatch") alert("기존 비밀번호가 맞지 않습니다.");
  else if (modify == "failed") alert("회원정보 수정 실패.");
</script>
</body>

</html>
