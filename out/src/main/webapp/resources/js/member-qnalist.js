
// 페이지 이동 함수
function paging(page){
    $('[name=filter] [name=page]').val(page);
    $('[name=filter]').submit();
}