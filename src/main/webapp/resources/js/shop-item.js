// 수량 증가
function quantityUp(){
    const quantity = $('input[name = quantity]'); // 수량 입력 input
    let val = quantity.val(); // 현재 수량
    val *= 1; // number로 형변환
    val += 1; // +1
    quantity.val(val); // 값 등록
}
// 수량 감소
function quantityDown(){
    const quantity = $('input[name = quantity]'); // 수량 입력 input
    let val = quantity.val(); // 현재 수량
    val *= 1; // number로 형변환
    val -= 1; // -1
    if (val != 0) quantity.val(val); // 현재 수량이 1이 아니면(-1 했을때 0이 아니면) 값 등록
}

// 슬라이드 이미지 기능
let track = $('#imgSliderTrack'); // 슬라이더 트랙

// 이전 기능
function prv(){
    let lastChild = track.children().last(); // 슬라이더의 마지막 이미지
    track.prepend(lastChild); // 마지막 이미지를 맨 앞으로 추가
}
// 다음 기능
function next(){
    let lastChild = track.children().first(); // 슬라이더의 첫번째 이미지
    track.append(lastChild); // 첫번째 이미지를 맨 뒤로 추가
}

// 바로 구매
function buyNow(itemnum, quantity){
    document.buyNow.action = '/shop/checkout'; // URI 패턴
    let itemList = [{"itemnum":itemnum, "quantity":quantity}]; // 제품번호, 수량 리스트로 생성
    let jsonItemList = JSON.stringify(itemList); // JSON String으로 변환
    document.buyNow.items.value = jsonItemList; // JSON String 전달
    document.buyNow.submit();
}

// 장바구니 추가

// addBasketModal
const addBasketModal = $('#addBasketModal');

function addtoBasket(itemnum, quantity) {
    let item = {"itemnum":itemnum, "quantity":quantity}; // 제품번호, 수량 리스트로 생성
    let jsonItem = JSON.stringify(item); // JSON String으로 변환
    $.ajax({
        url: "/shop/addtoBasket",
        type: "POST",
        data: {"item" : jsonItem}, // 제품번호, 제품수량 값을 map 형태로 전달
        success: function(data){
            if (data == 'passed'){ // 정상적으로 추가되었을 경우
                addBasketModal.toggle(); // 모달 열기
                document.addEventListener("click", closeAddBasketModalbyClick); // 장바구니 추가 모달 이외의 부분 클릭시 모달 닫는 함수 추가
            } else { // 추가에 실패했을 경우
                alert("요청이 잘못되었습니다.")
            }
        }
    });
}

// 장바구니 추가 모달 닫는 함수
function closeAddBasketModal() {
    addBasketModal.toggle();
    document.removeEventListener("click", closeAddBasketModalbyClick);
}

// 장바구니 추가 모달 이외의 부분 클릭시 모달 닫는 함수
const closeAddBasketModalbyClick = (event) => {
    // 타겟 확인
    const target = event.target;
    // 타겟이 모달 내에 있는지 확인
    if (target.classList.value == 'addBasketModal') return;
    // 이외의 부분일 경우 닫는 함수 호출
    closeAddBasketModal()
}
