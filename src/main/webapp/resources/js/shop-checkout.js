// 회원정보 복사
function sameWithCustomer(){
    $('#shippingName').val($('#customerInfoName').val());
    $('#shippingZipcode').val($('#customerInfoZipcode').val());
    $('#shippingAddr').val($('#customerInfoAddr').val());
    $('#shippingAddrDetail').val($('#customerInfoAddrDetail').val());
    $('#shippingMobile1').val($('#customerInfoMobile1').val()).prop("selected", true);
    $('#shippingMobile2').val($('#customerInfoMobile2').val());
    $('#shippingMobile3').val($('#customerInfoMobile3').val());
}

// 전화번호 중간자리
const mobileMiddle = document.getElementById("shippingMobile2");
// 중간자리에 숫자 4개를 입력하면 뒷자리로 포커스하는 함수
mobileMiddle.onfocus = () => {
    mobileMiddle.addEventListener("keyup", () => {
        if (mobileMiddle.value.length == 4){
            document.getElementById("shippingMobile3").focus();
        }
    })
}

// 카카오 지도 api 실행 함수
function findAddr() {
    new daum.Postcode({
        oncomplete: function (data) {
            const roadAddr = data.roadAddress; // 도로명 주소
            const jibunAddr = data.jibunAddress; // 지번 주소

            document.getElementById('shippingZipcode').value = data.zonecode; // 우편번호 할당
            if (roadAddr !== '') {
                document.getElementById("shippingAddr").value = roadAddr; // 도로명주소 선택시 할당
            } else if (jibunAddr !== '') {
                document.getElementById("shippingAddrDetail").value = jibunAddr; // 지번주소 선택시 할당
            }
        }
    }).open();
}

// 주소 검색 시, 상세주소 입력란에 placeholder 추가
document.getElementById("searchAddr").onclick = () => {
    findAddr();
    document.getElementById("shippingAddrDetail").placeholder = "상세주소를 입력해주세요.";
}

// 아임포트 (결제 시스템)
function inicisPayment() {
    IMP.init('imp90488912');

    // 상품명 생성
    let itemName = $('.itemNames').first().text();
    if ($('.itemNames').length > 1) itemName += ' 외 ' + ($('.itemNames').length - 1);

    // 결제방법 확인
    const payment = $('[name = checkOutForm] [name = payment]').val();
    if (payment == 'naver-pay' || payment == 'escrow') {
        alert('미구현 결제수단, 카드 결제 부탁드립니다.');
        return;
    }

    //결제시 전달되는 정보
    IMP.request_pay({
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: 'merchant_' + new Date().getTime(),
        name:  itemName /*상품명*/,
        // amount: $('[name = checkOutForm] [name = totalCost]').val() * 1/*상품 가격*/,
        amount: 1000/*테스트용 상품가격*/,
        buyer_email: $('[name = checkOutForm] [name = email]').val()/*구매자 이메일*/,
        buyer_name: $('[name = checkOutForm] [name = name]').val()/*구매자 이름*/,
        buyer_tel: $('#customerInfoMobile1 option:selected').val() + '-' + $('#customerInfoMobile2').val() + '-' + $('#customerInfoMobile3').val()/*구매자 연락처*/,
        buyer_addr: $('#shippingAddr').val() + ' ' + $('#shippingAddrDetail').val()/*구매자 주소*/,
        buyer_postcode: $('#shippingZipcode').val()/*구매자 우편번호*/
    }, function (rsp) {
        var result = '';
        if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            $('[name = checkOutForm] [name = impuid]').val(rsp.imp_uid); // 고유 ID
            $('[name = checkOutForm] [name = merchantuid]').val(rsp.merchant_uid); // 상점 거래 ID
            $('[name = checkOutForm] [name = paidamount]').val(rsp.paid_amount); // 결제 금액
            $('[name = checkOutForm] [name = applynum]').val(rsp.apply_num); // 카드 승인번호
            result = '0';
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
            result = '1';
        }
        if (result == '0') {
            $('[name = checkOutForm]').submit();
        }
        alert(msg);
    });
}
