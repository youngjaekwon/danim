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
