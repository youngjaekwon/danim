<%@ page import="com.danim.shop.beans.ItemsDTO" %>
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
          DecimalFormat formatter = new DecimalFormat("#,###,###");
          int totalPrice = 0;
          for(ItemsDTO item : itemList){
            String itemnum = item.getItemnum();
            String thumbnail = item.getPic().split("\\$")[0];
            totalPrice += item.getPrice();
        %>
        <div class="item">
          <div class="item-info">
            <a href="/shop/item?item=<%=itemnum%>" class="item-img" id="img<%=itemnum%>"></a>
            <script>$('#img<%=itemnum%>').css("background-image", "url('<%=thumbnail%>')")</script>
            <a href="/shop/item?item=<%=itemnum%>"><%=item.getMfr()%> <%=item.getName()%></a>
          </div>
          <div class="item-quantity">
            <input type="text" name="quantity" id="quantity" value="<%=item.getQuantity()%>">
          </div>
          <div class="item-price"><%=formatter.format(item.getPrice())%></div>
        </div>
        <%
          }
        %>
      </div>
      <div class="summary">
        <div class="subtotal">Subtotal <span><%=formatter.format(totalPrice)%></span></div>
        <div class="shipping">Shipping <span>0</span></div>
        <div class="total">Total <span><%=formatter.format(totalPrice)%></span></div>
      </div>
    </div>
    <div class="main-right">
      <span>Check out</span>
      <form action="" method="post">
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
          <input type="text" name="shipping_name" class="checkOutName" id="shippingName"/>
          <label for="shippingZipcode">Zipcode</label>
          <input type="text" name="shipping_zipcode" class="checkOutZipcode" id="shippingZipcode" readonly>
          <button type="button" id="searchAddr">SEARCH</button>
          <label for="shippingAddrDetail">Address</label>
          <input type="text" name="shipping_addr" class="checkOutAddr" id="shippingAddr" readonly>
          <input type="text" name="shipping_addrDetail" class="checkOutAddrDetail" id="shippingAddrDetail">
          <label for="shippingMobile2">Mobile</label>
          <select name="shipping_mobile1" class="checkOutMobile1" id="shippingMobile1">
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
          </select>
          <span>-</span>
          <input type="text" name="shipping_mobile2" class="checkOutMobile2" id="shippingMobile2" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
          <span>-</span>
          <input type="text" name="shipping_mobile3" class="checkOutMobile3" id="shippingMobile3" maxlength="4" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
          <label for="msg">Message</label>
          <textarea name="msg" id="msg" cols="65" rows="8" placeholder="요청사항을 입력해 주세요."></textarea>
        </div>
        <div class="payment">
          <p>Payment</p>
          <div class="payment-inner">
            <div class="total-cost">
              <span>Total Cost</span>
              <span><%=formatter.format(totalPrice)%></span>
              <input type="hidden" name="totalCost" value="<%=formatter.format(totalPrice)%>">
            </div>
            <div class="payment-type">
              <span>Type</span>
              <input type="radio" name="payment-type" id="card" value="card" checked><label for="card">카드 결제</label>
              <input type="radio" name="payment-type" id="naver-pay" value="naver-pay"><label for="naver-pay">네이버
              페이</label>
              <input type="radio" name="payment-type" id="escrow" value="escrow"><label for="escrow">에스크로(무통장
              입금)</label>
            </div>
          </div>
          <input type="submit" id="checkout-submit" value="Checkout Now">
        </div>
      </form>
    </div>

  </div>
</section>
</body>

</html>
