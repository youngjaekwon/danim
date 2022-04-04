function toggleItemModal(orderNum){
    $('#item_modal' + orderNum).toggle();
}

// 페이지 이동 함수
function paging(page){
    $('[name=filter] [name=page]').val(page);
    $('[name=filter]').submit();
}

$('[name=filter] [name=category]').change(() => {
    $('[name=filter] [name=sort]').val('ITEMNUM').prop("selected", true);
    $('[name=filter]').submit();
})
$('[name=filter] [name=stock]').change(() => {
    $('[name=filter] [name=sort]').val('ITEMNUM').prop("selected", true);
    $('[name=filter]').submit();
})
$('[name=filter] [name=sort]').change(() => {
    $('[name=filter]').submit();
})