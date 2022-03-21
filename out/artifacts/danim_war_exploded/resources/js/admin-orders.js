function toggleOrderModal(orderNum){
    $('#order_modal' + orderNum).toggle();
}

// 페이지 이동 함수
function paging(page){
    $('[name=filter] [name=page]').val(page);
    $('[name=filter]').submit();
}

$('[name=filter] [name=state]').change(() => {
    $('[name=filter]').submit();
})
$('[name=filter] [name=qna]').change(() => {
    $('[name=filter]').submit();
})
$('[name=filter] [name=sort]').change(() => {
    $('[name=filter]').submit();
})