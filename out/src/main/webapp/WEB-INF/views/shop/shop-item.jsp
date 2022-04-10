<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/resources/css/shop-item.css">
    <!-- fontawesome v5 cdn -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

    <title>다님 : 여행을 다니다</title>
    <script src="/resources/js/shop-item.js" defer></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="item1">
    <div class="item1-top">
        <div class="item1-top-left">
            <a href="#">SHOP</a>
            <span> > </span>
            <a href="#">${itemVO.category}</a>
        </div>
        <div class="item1-top-right">
            <span>${itemVO.name}</span>
        </div>
    </div>
</section>
<section class="main">
    <div class="main-inner">
        <div class="item-info">
            <div class="item-brand">
                ${itemVO.mfr}
            </div>
            <div class="item-title">${itemVO.name}</div>
            <div class="item-price">
                ${itemVO.formattedPrice}
            </div>
            <div class="item-detail">
                ${itemVO.info}
            </div>
            <div class="quantity-label-box">
                <label for="quantity" class="quantity-label">QUANTITY</label>
            </div>
            <div class="quantity">
                <input type="text" name="quantity" id="quantity" value="1">
                <button class="quantity-up" onclick="quantityUp()">UP</button>
                <button class="quantity-down" onclick="quantityDown()">DOWN</button>
            </div>
            <div class="submits">
                <a href="javascript:buyNow('${itemVO.itemnum}', $('input[name = quantity]').val())" class="buy-now">Buy Now</a>
                <a href="javascript:addtoBasket('${itemVO.itemnum}', $('input[name = quantity]').val())" class="add-to-basket">Add to Basket</a>
                <a href="#" class="wish-list">Wish List</a>
            </div>
        </div>
        <div class="item-img-slider">
            <div class="img-slider-box">
                <%
                    ItemsDTO item = (ItemsDTO)request.getAttribute("itemVO"); // item 객체
                    String[] pics = item.getPic().split("\\$"); // 사진 String들, $로 나뉘어 있음
                    int trackWidth = 710 * pics.length; // 슬라이더 트랙의 width 지정 (전체 사진 * 710px)
                %>
                <div class="img-slider-track" id="imgSliderTrack" style="width: <%=trackWidth%>px">
                    <%
                        // 사진 출력
                        for (String pic : pics){
                            if (!pic.startsWith("https")) pic = "/resources/upload/" + pic;
                    %>
                    <img src="<%=pic%>" />
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="slide-btn">
                <a href="javascript:prv()" class="slide-prv-btn">
                    <i class="fas fa-angle-double-left"></i>
                </a>
                <a href="javascript:next()" class="slide-next-btn">
                    <i class="fas fa-angle-double-right"></i>
                </a>
            </div>
        </div>
        <div id="addBasketModal" class="addBasketModal" style="display: none">
            <div class="addBasketModal">장바구니 등록이 완료되었습니다.</div>
            <div class="addBasketModal">
                <a href="javascript:closeAddBasketModal()" class="addBasketModal">계속 쇼핑</a>
                <a href="/shop/basket" class="addBasketModal">장바구니로 이동</a>
            </div>
        </div>
    </div>
    <!-- 자바스크립트 함수에서 사용을 하기 위해 선언된 폼
	화면에는 보이지 않는 hidden 타입으로 전부 선언함 -->
    <!-- 바로 구매 -->
    <form name="buyNow" method="post">
        <input type="hidden" name="item">
    </form>
    <!-- 장바구니 추가 -->
    <form name="addToBasket" method="post">
        <input type="hidden" name="items">
    </form>
</section>
</body>
</html>
