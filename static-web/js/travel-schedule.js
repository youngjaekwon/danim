
$(document).ready(function(){  // 웹문서 로딩 완료 후  
    $(".Place-btn").hide(); // <p> 요소를 일단 먼저 숨긴 후
        $("#suggestion-btn-Place").click(function(){ // <button> 요소 클릭하면
            $(".Place-btn").fadeToggle();  // <p> 요소를 보이기/숨기기 상호 전환시켜라.
    });
});

