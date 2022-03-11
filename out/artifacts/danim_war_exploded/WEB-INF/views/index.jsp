<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/index.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
</head>

<body>
<%@include file ="common/header.jsp" %>
<section>
  <div class="inner_wrap">
    <div class="img_slide"></div>
    <div class="search">
      <input class="search_input" id="searchInput" type="text" placeholder="회원들이 촬영한 사진과 카메라, 여행 관련 상품들을 검색해 보세요.">
      <script>
        const searchInput = $('#searchInput');
        searchInput.focus(() => {searchInput.prop("placeholder", '');});
        searchInput.blur(()=>{searchInput.prop("placeholder", '회원들이 촬영한 사진과 카메라, 여행 관련 상품들을 검색해 보세요.')})
      </script>
    </div>
  </div>
</section>
</body>

</html>
