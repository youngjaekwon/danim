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