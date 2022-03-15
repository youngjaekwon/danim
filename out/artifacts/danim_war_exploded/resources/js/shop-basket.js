// 장바구니 품목 수량 감소 함수
function quantityDown(itemnum){
    const quantity = $('#quantity' + itemnum); // 기존에 입력된 수량
    if (quantity.val() == 1) return;
    let item = {"itemnum":itemnum, "quantity": -1}; // 제품번호, 수량 리스트로 생성
    let jsonItem = JSON.stringify(item); // JSON String으로 변환
    $.ajax({
        url: "/shop/addtoBasket",
        type: "POST",
        data: {"item" : jsonItem}, // 제품번호, 제품수량 값을 map 형태로 전달
        success: function(data){
            if (data == 'passed'){ // 정상적으로 추가되었을 경우
                quantity.val(quantity.val() * 1 - 1);
            } else { // 추가에 실패했을 경우
                alert("요청이 잘못되었습니다.")
            }
        }
    });
}

// 장바구니 품목 수량 증가 함수
function quantityUp(itemnum){
    const quantity = $('#quantity' + itemnum); // 기존에 입력된 수량
    if (quantity.val() == 1) return;
    let item = {"itemnum":itemnum, "quantity": 1}; // 제품번호, 수량 리스트로 생성
    let jsonItem = JSON.stringify(item); // JSON String으로 변환
    $.ajax({
        url: "/shop/addtoBasket",
        type: "POST",
        data: {"item" : jsonItem}, // 제품번호, 제품수량 값을 map 형태로 전달
        success: function(data){
            if (data == 'passed'){ // 정상적으로 추가되었을 경우
                quantity.val(quantity.val() * 1 + 1);
            } else { // 추가에 실패했을 경우
                alert("요청이 잘못되었습니다.")
            }
        }
    });
}

function deleteItem(itemnum){
    $.ajax({
        url: "/shop/deleteItemfromBasket",
        type: "POST",
        data: {"itemnum" : itemnum}, // 삭제할 상품번호 전달
        success: function(data){
            if (data == 'passed'){ // 정상적으로 삭제되었을 경우
                window.location.reload();
            } else { // 추가에 실패했을 경우
                alert("요청이 잘못되었습니다.")
            }
        }
    });
}