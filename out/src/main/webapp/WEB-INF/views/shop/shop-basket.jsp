<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/shop-basket.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="/resources/js/shop-basket.js" defer></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <script>
    if ('${loginCheck}' == 'true') window.location.reload();
  </script>
  <div class="main-inner">
    <div class="main-left">
      <span>Basket</span>
    </div>
    <div class="main-right">
      <div class="basket-title">
        <span class="title-unit">Unit</span>
        <span class="title-quantity">Quantity</span>
        <span class="title-price">Price</span>
      </div>
      <div class="basket-items">
        <%
          List<ItemsDTO> itemList = (List<ItemsDTO>) request.getAttribute("itemList");
          if (itemList.isEmpty()) {
        %>
        <div class="item">
          <div class="item-info">
           <span>장바구니가 비었습니다.</span>
          </div>
        </div>
        <%
          }
          else {
            for(ItemsDTO item : itemList){
              String itemnum = item.getItemnum();
        %>
        <div class="item">
          <div class="item-info">
            <a href="/shop/item?item=<%=itemnum%>" class="item-img" id="img<%=itemnum%>"></a>
            <script>$('#img<%=itemnum%>').css("background-image", "url('<%=item.getThumbnail()%>')")</script>
            <a href="/shop/item?item=<%=itemnum%>"><%=item.getMfr()%> <%=item.getName()%></a>
          </div>
          <div class="item-quantity">
            <a href="javascript:quantityDown('<%=itemnum%>')" class="quantity-down">
              <i class="fas fa-angle-double-left"></i>
            </a>
            <input type="text" name="quantity" class="quantity" id="quantity<%=itemnum%>" value="<%=item.getQuantity()%>" readonly>
            <a href="javascript:quantityUp('<%=itemnum%>')" class="quantity-op">
              <i class="fas fa-angle-double-right"></i>
            </a>
          </div>
          <div class="item-price"><%=item.getFormattedPrice()%></div>
          <a href="javascript:deleteItem('<%=itemnum%>')"><i class="far fa-times-circle"></i></a>
        </div>
        <%
            }
          }
        %>
      </div>
      <div class="summary">
        <div class="subtotal">Subtotal <span></span></div>
        <script>
          if ('${formattedTotalCost}' != ''){
            $('.subtotal > span').text('${formattedTotalCost}');
          } else $('.subtotal > span').text('0');
        </script>
        <div class="shipping">Shipping <span>0</span></div>
        <div class="total">Total <span>${formattedTotalCost}</span></div>
        <script>
          if ('${formattedTotalCost}' != ''){
            $('.total > span').text('${formattedTotalCost}');
          } else $('.total > span').text('0');
        </script>
        <div id="checkout">
          <a href="/shop/checkout">Proceed to Checkout</a> <a href="#">Continue Shopping</a>
        </div>
      </div>
    </div>
  </div>
</section>
<script>
  if('${param.emptyBasket}' == 'true'){
    alert("장바구니가 비어있습니다.");
  }
</script>
</body>

</html>
