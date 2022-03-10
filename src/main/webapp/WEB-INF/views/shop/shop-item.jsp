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
            <a href="#">ACC</a>
        </div>
        <div class="item1-top-right">
            <span>TEKET Winner’s Mug Cup</span>
        </div>
    </div>
</section>
<section class="main">
    <div class="main-inner">
        <div class="item-info">
            <div class="item-brand">
                TEKET
            </div>
            <div class="item-title">TEKET Winner’s Mug Cup "White"</div>
            <div class="item-price">
                22,000
            </div>
            <div class="item-detail">
                Diameter 7.9 Height 9.4<br />
                - 330ml 11oz<br />
                - 100% Ceramic<br />
                - Be careful not to break it!<br />
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
                <a href="#" class="buy-now">Buy Now</a>
                <a href="#" class="add-to-basket">Add to Basket</a>
                <a href="#" class="wish-list">Wish List</a>
            </div>
        </div>
        <div class="item-img-slider">
            <div class="img-slider-box">
                <div class="img-slider-track" id="imgSliderTrack">
                    <img src="http://vaska.kr/web/product/extra/big/202112/09b7202ef745ae1b1ed8c360b556b287.jpeg" />
                    <img src="http://vaska.kr/web/product/small/202112/3011b0c5e3fb7fc36b87660390572ce9.jpeg" />
                    <img src="http://vaska.kr/web/product/extra/big/202112/edd52daec6c8728bda63d6d32417f8e2.jpeg" />
                    <img src="http://vaska.kr/web/product/extra/big/202112/e99dc5fddcdcfba5f04ab89e39c227a3.jpeg" />
                    <img src="http://vaska.kr/web/product/extra/big/202112/27f0674fdc171b36e9b46170e0236a7d.jpeg" />
                    <img src="http://vaska.kr/web/product/extra/big/202112/3c4c31b8ce8871f765b43d9267aef1d9.jpeg" />
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
    </div>
</section>
</body>

</html>
