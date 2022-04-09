// 페이지 이동 함수
function paging(page){
    $('[name=filter] [name=page]').val(page);
    $('[name=filter]').submit();
}

function listAll(){
    document.getElementById('FC').checked = false;
    document.getElementById('Film').checked = false;
    $('[name=filter] [name=sort]').val('ITEMNUM').prop("selected", true);
    $('[name=filter]').submit();
}

function filtering(){
   if ($('#FC').is(':checked') || $('#Film').is(':checked')){
       document.getElementById('ALL').checked = false;
   }
    $('[name=filter] [name=sort]').val('ITEMNUM').prop("selected", true);
    $('[name=filter]').submit();
}

$('[name=filter] [name=sort]').change(() => {
    $('[name=filter]').submit();
})