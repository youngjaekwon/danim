const textBox = $('#textBox');

textBox.keyup(function (e) {
  let content = $(this).val();

  // 글자수 세기
  if (content.length == 0 || content == '') {
    $('.textCount').text('0자');
  } else {
    $('.textCount').text(content.length + '자');
  }

  // 글자수 제한
  if (content.length > 100) {
    // 100자 부터는 타이핑 되지 않도록
    $(this).val($(this).val().substring(0, 100));
    // 100자 넘으면 알림창 뜨도록
    alert('글자수는 100자까지 입력 가능합니다.');
  };
});

function doRegComment(){
  let commentObj = {
    "txt" : textBox.val(),
    "memnum" : $('[name=regComment] [name=memnum]').val(),
    "role" : $('[name=regComment] [name=role]').val(),
    "qnanum" : $('[name=regComment] [name=qnanum]').val(),
  }

  $.ajax({
    url: "/comments/doReg",
    type: "POST",
    contentType: "application/json; charset=utf-8",
    data: JSON.stringify(commentObj), // comment 객체를 JSON String 형태로 전달
    dataType: "json",
    success: function(commentVO){
      $('.comments').append(
          '<div class="comment" id="' + commentVO.cnum + '">\n' +
          '          <div class="comment_name">' + commentVO.name + '</div>\n' +
          '          <div class="comment_txt">' + commentVO.txt + '</div>\n' +
          '          <div class="comment_date">' + commentVO.date + '</div>\n' +
          '          <div class="comment_delete">\n' +
          '            <button data-index="'+ commentVO.cnum +'" onclick="doDelComment(this.dataset.index)">삭제</button>\n' +
          '          </div>\n' +
          '        </div>'
      );
      textBox.val('');
      $('.textCount').text('0자');
      alert("댓글등록에 성공했습니다.");
    }
  }).fail(function (error){
    alert(error);
  });
  return false;
}

function doDelComment(index){
  console.log(index);
  if (confirm("댓글을 삭제하시겠습니까?")){
    $.ajax({
      url: "/comments/doDel",
      type: "GET",
      data: {"cnum" : index},
      dataType: "json",
      success: function(data){
        console.log(data);
        if (data){
          $('#' + index).remove();
          alert("댓글삭제에 성공했습니다.");
        }
      }
    });
  }
}

function delToggle(){
  $('#delQ').toggle();
  $('#doDel').toggle();
  $('#delCan').toggle();
}