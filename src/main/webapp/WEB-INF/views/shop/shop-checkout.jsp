<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/shop-checkout.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="/resources/js/shop-checkout.js" defer></script>
  <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <div class="main-inner">
    <div class="main-left">
      <span>Your Basket</span>
      <div class="basket-title">
        <span class="title-unit">Unit</span>
        <span class="title-quantity">Quantity</span>
        <span class="title-price">Price</span>
      </div>
      <div class="basket-items">
        <%
          List<ItemsDTO> itemList = (List<ItemsDTO>) request.getAttribute("itemList");
          for(ItemsDTO item : itemList){
            String itemnum = item.getItemnum();
        %>
        <div class="item">
          <div class="item-info">
            <a href="/shop/item?item=<%=itemnum%>" class="item-img" id="img<%=itemnum%>"></a>
            <script>$('#img<%=itemnum%>').css("background-image", "url('<%=item.getThumbnail()%>')")</script>
            <a href="/shop/item?item=<%=itemnum%>" class="itemNames"><%=item.getMfr()%> <%=item.getName()%></a>
          </div>
          <div class="item-quantity">
            <input type="text" name="quantity" id="quantity" value="<%=item.getQuantity()%>">
          </div>
          <div class="item-price"><%=item.getFormattedPrice()%></div>
        </div>
        <%
          }
        %>
      </div>
      <div class="summary">
        <div class="subtotal">Subtotal <span>${formattedTotalCost}</span></div>
        <div class="shipping">Shipping <span>0</span></div>
        <div class="total">Total <span>${formattedTotalCost}</span></div>
      </div>
    </div>
    <div class="main-right">
      <span>Check out</span>
      <form action="/order/doRegOrder" name="checkOutForm" method="post">
        <div class="customer-info">
          <p>Customer</p>
          <label for="customerInfoName">Name</label>
          <input type="text" name="name" class="checkOutName" id="customerInfoName" value="${userInfo.name}" readonly/>
          <label for="customerInfoZipcode">Zipcode</label>
          <input type="text" name="zipcode" class="checkOutZipcode" id="customerInfoZipcode" value="${userInfo.zipcode}" readonly>
          <label for="customerInfoAddrDetail">Address</label>
          <input type="text" name="addr" class="checkOutAddr" id="customerInfoAddr" value="${userInfo.addr}" readonly>
          <input type="text" name="addrDetail" class="checkOutAddrDetail" id="customerInfoAddrDetail" value="${userInfo.addrDetail}" readonly>
          <label for="customerInfoMobile2">Mobile</label>
          <select name="mobile1" class="checkOutMobile1" id="customerInfoMobile1" disabled>
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
          </select>
          <script>
            $('#customerInfoMobile1').val('${userInfo.mobile1}').prop("selected", true);
          </script>
          <span>-</span>
          <input type="text" name="mobile2" class="checkOutMobile2" id="customerInfoMobile2" value="${userInfo.mobile2}" readonly>
          <span>-</span>
          <input type="text" name="mobile3" class="checkOutMobile3" id="customerInfoMobile3" value="${userInfo.mobile3}" readonly>
          <label for="customerInfoEmail">Email</label>
          <input type="text" name="email" class="checkOutEmail" id="customerInfoEmail" value="${userInfo.email}" readonly>
        </div>
        <div class="customer-info">
          <p>Shipping</p>
          <button type="button" onclick="sameWithCustomer()">> Same with Customer</button>
          <label for="shippingName">Name</label>
          <input type="text" name="shippingName" class="checkOutName" id="shippingName"/>
          <label for="shippingZipcode">Zipcode</label>
          <input type="text" name="shippingZipcode" class="checkOutZipcode" id="shippingZipcode" readonly>
          <button type="button" id="searchAddr">SEARCH</button>
          <label for="shippingAddrDetail">Address</label>
          <input type="text" name="shippingAddr" class="checkOutAddr" id="shippingAddr" readonly>
          <input type="text" name="shippingAddrDetail" class="checkOutAddrDetail" id="shippingAddrDetail">
          <label for="shippingMobile2">Mobile</label>
          <select name="shippingMobile1" class="checkOutMobile1" id="shippingMobile1">
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
          </select>
          <span>-</span>
          <input type="text" name="shippingMobile2" class="checkOutMobile2" id="shippingMobile2" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
          <span>-</span>
          <input type="text" name="shippingMobile3" class="checkOutMobile3" id="shippingMobile3" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
          <label for="msg">Message</label>
          <textarea name="request" id="msg" cols="65" rows="8" maxlength="300" placeholder="요청사항을 입력해 주세요. (최대 300자)"></textarea>
        </div>
        <div class="payment">
          <p>Payment</p>
          <div class="payment-inner">
            <div class="total-cost">
              <span>Total Cost</span>
              <span>${formattedTotalCost}</span>
              <input type="hidden" name="totalCost" value="${totalCost}">
            </div>
            <div class="payment-type">
              <span>Type</span>
              <input type="radio" name="payment" id="card" value="card" checked><label for="card">카드 결제</label>
              <input type="radio" name="payment" id="naver-pay" value="naver-pay"><label for="naver-pay">네이버
              페이</label>
              <input type="radio" name="payment" id="escrow" value="escrow"><label for="escrow">에스크로(무통장
              입금)</label>
            </div>
          </div>
          <input type="hidden" name="itemlist" value='${jsonStringItemList}'>
          <!-결제정보 전달->
          <input type="hidden" name="impuid">
          <input type="hidden" name="merchantuid">
          <input type="hidden" name="paidamount">
          <input type="hidden" name="applynum">
          <input type="button" onclick="inicisPayment()" id="checkout-submit" value="Checkout Now">
        </div>
      </form>
    </div>

  </div>
</section>
</body>

</html>
