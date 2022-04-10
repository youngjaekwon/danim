const handler = {
  init() {
    // 보여지는 파일 업로드 input
    const fileInput = document.getElementById("file-input");
    // 숨겨져 있는 파일 업로드 input
    const fileInputCopy = document.getElementById("file-input-copy");
    // 사진이 preview될 div
    const preview = document.getElementById("preview");

    // 파일 업로드 input이 변경될때 실행 (파일 업로드 하는 함수)
    fileInput.addEventListener("change", () => {
      console.dir(fileInput);

      // 저장 요청된 파일들
      let files = Array.from(fileInput.files);
      // 숨겨진 파일 업로드 input에 있는 파일들
      const filesCopy = Array.from(fileInputCopy.files);

      const dataTransfer = new DataTransfer();

      // 저장 요청된 파일들을 dataTransfer에 등록
      files.forEach((file) => {
        if (checkImgFile(file.name)){
          dataTransfer.items.add(file);
        }
        // 이미지가 아닐경우 alert + dataTransfer에 등록하지 않음
        else {
          alert("이미지 파일만 업로드 가능합니다.");
        }
      });

      // 이미지가 아닌 파일을 제외한 파일들로 요청된 파일 목록을 갱신
      files = dataTransfer.files;

      // 숨겨진 파일 업로드 input에 있는 파일들을 dataTrasfer에 등록
      filesCopy.forEach((file) => {
        dataTransfer.items.add(file);
      });

      // 숨겨진 파일 업로드 input의 파일 리스트를 갱신
      fileInputCopy.files = dataTransfer.files;

      // preview에 있는 이미지들을 모두 가져옴
      const imgboxArr = document.getElementsByClassName("imgbox");
      // 마지막 element
      const lastOne = imgboxArr[imgboxArr.length - 1];
      // imgbox의 마지막 인덱스 (imgbox id는 imgbox + index 로 되어있음)
      let lastIndex = -1;

      // 마지막 element가 존재할 경우
      if (typeof lastOne != undefined && lastOne != null) {
        // 마지막 index 갱신
        lastIndex =
          imgboxArr[imgboxArr.length - 1].id.replace("imgbox", "") * 1;
      }

      // imgbox index 들을 저장할 리스트
      const imgboxIndexList = new Array(files.length);
      // 파일 이름들을 저장할 리스트
      const fileNameList = new Array(files.length);
      // preview할 binary 데이터를 저장할 리스트
      const imgBinList = new Array(files.length);
      // 이미지 로드를 count하는 변수
      let onloadCount = 0;

      // 추가 요청된 파일들을 for문을 통해 반복
      for (let i = 0; i < files.length; i++) {
        // imgbox index
        const boxIndex = i + lastIndex + 1;
        // 추가될 파일
        const file = files[i];

        const reader = new FileReader();

        // 이미지의 binary 데이터
        let img = "";

        // FileReader가 로드될때 실행될 callback 함수
        reader.onload = function () {
          // 이미지의 binary 데이터를 읽어온 데이터로 갱신
          img = reader.result;
          // imgbox의 인덱스를 저장
          imgboxIndexList[i] = boxIndex;
          // file의 이름을 저장
          fileNameList[i] = file.name;
          // 이미지의 binary 데이터를 저장
          imgBinList[i] = img;
          // 이미지 로드를 count 추가
          onloadCount++;

          // 이미지가 모두 로드되면 preview에 추가
          if (onloadCount == files.length) {
            for (let j = 0; j < files.length; j++) {
              preview.innerHTML += `<div id="imgbox${imgboxIndexList[j]}" class="imgbox">
                  <div>
                    <img src="${imgBinList[j]}" style="width: 150px;">
                  </div>
                  <div>
                    ${fileNameList[j]}
                    <button data-index='imgbox${imgboxIndexList[j]}'  class='file-remove'>X</button>
                  </div>    
                        </div>`;
            }
          }
        };
        // FileReader로 이미지 파일 리드
        reader.readAsDataURL(file);
      }

      // 빈 DataTransfer를 생성
      const emptyDataTransfer = new DataTransfer();

      /*
      * 보여지는 파일 업로드 input의 파일 리스트를 갱신
      * 파일리스트를 비워놓아 다음 파일 업로드시 change 이벤트가 발생하도록 만듦
      * */
      fileInput.files = emptyDataTransfer.files;
    });
  },

  // 파일 삭제시 사용하는 함수
  removeFile: () => {
    // 클릭 이벤트 발생시
    document.addEventListener("click", (e) => {
      // file-remove 를 class로 가지는지 확인
      if (e.target.className !== "file-remove") return;

      // 클릭된 버튼의 index 가져옴 (imgbox의 index와 같음)
      const removeTargetId = e.target.dataset.index;
      /* removeTargetId에서 "imgbox"를 제외한 순수 index
      * 삭제할 imgbox의 index
      * */
      const removeTargetNum = removeTargetId.replace("imgbox", "") * 1;

      let targetIndex = -1;

      // imgbox elements
      const imgboxArr = document.getElementsByClassName("imgbox");

      // imgbox들의 index를 저장할 array
      let imgboxNumArr = new Array();

      // array에 imgbox의 index들을 저장
      Array.prototype.forEach.call(imgboxArr, (imgbox) => {
        imgboxNumArr.push(imgbox.id.replace("imgbox", "") * 1);
      });

      imgboxNumArr.sort();

      // imgboxNumArr를 순회하며 removeTargetNum와 일치하는 box를 찾아 index를 갱신
      for (let i = 0; i < imgboxNumArr.length; i++) {
        if (removeTargetNum == imgboxNumArr[i]) {
          targetIndex = i;
        }
      }

      // 삭제할 imgbox
      const removeTarget = document.getElementById(removeTargetId);
      // 숨겨진 file input에 저장된 파일 리스트
      const files = document.getElementById("file-input-copy").files;

      const dataTranster = new DataTransfer();

      // 삭제 대상인 파일을 제외하고 DataTransfer에 등록
      for (let i = 0; i < files.length; i++) {
        if (i == targetIndex) continue;
        dataTranster.items.add(files[i]);
      }

      // 숨겨진 file input의 파일 리스트를 갱신
      document.getElementById("file-input-copy").files = dataTranster.files;

      // imgbox 삭제
      removeTarget.remove();
    });
  },
};

window.onload = () => {
  handler.init();
  handler.removeFile();
};

function checkImgFile(fileName){
  let ext = fileName.substring(fileName.lastIndexOf('.') + 1);
  if (ext == null || ext === "" || typeof ext === undefined) return false;
  const imgs = ['gif', 'jpg', 'jpeg', 'png'];
  ext = ext.toLocaleLowerCase();
  for (let i = 0; i < imgs.length; i++){
    if (ext === imgs[i]) return true;
  }
  return false;
}

function isValidForm(){
  document.regQNA.submit();
}

$('#textBox').keyup(function (e) {
  let content = $(this).val();

  // 글자수 세기
  if (content.length == 0 || content == '') {
    $('.textCount').text('0자');
  } else {
    $('.textCount').text(content.length + '자');
  }

  // 글자수 제한
  if (content.length > 500) {
    // 500자 부터는 타이핑 되지 않도록
    $(this).val($(this).val().substring(0, 500));
    // 500자 넘으면 알림창 뜨도록
    alert('글자수는 500자까지 입력 가능합니다.');
  };
});
