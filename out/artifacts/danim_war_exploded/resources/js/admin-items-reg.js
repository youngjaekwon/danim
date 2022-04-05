const handler = {
  init() {
    const fileInput = document.getElementById("file-input");
    const fileInputCopy = document.getElementById("file-input-copy");
    const preview = document.getElementById("preview");
    fileInput.addEventListener("change", () => {
      console.dir(fileInput);
      let files = Array.from(fileInput.files);
      const filesCopy = Array.from(fileInputCopy.files);
      const dataTransfer = new DataTransfer();

      files.forEach((file) => {
        if (checkImgFile(file.name)){
          dataTransfer.items.add(file);
        } else {
          alert("이미지 파일만 업로드 가능합니다.");
        }
      });

      files = dataTransfer.files;

      filesCopy.forEach((file) => {
        dataTransfer.items.add(file);
      });

      fileInputCopy.files = dataTransfer.files;

      const imgboxArr = document.getElementsByClassName("imgbox");
      const lastOne = imgboxArr[imgboxArr.length - 1];
      let lastIndex = -1;
      if (typeof lastOne != undefined && lastOne != null) {
        lastIndex =
          imgboxArr[imgboxArr.length - 1].id.replace("imgbox", "") * 1;
      }

      const imgboxIndexList = new Array(files.length);
      const fileNameList = new Array(files.length);
      const imgBinList = new Array(files.length);
      let onloadCount = 0;

      for (let i = 0; i < files.length; i++) {
        const boxIndex = i + lastIndex + 1;
        const file = files[i];
        const reader = new FileReader();
        let img = "";
        reader.onload = function () {
          img = reader.result;
          imgboxIndexList[i] = boxIndex;
          fileNameList[i] = file.name;
          imgBinList[i] = img;
          onloadCount++;

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
        reader.readAsDataURL(file);
      }

      const emptyDataTransfer = new DataTransfer();

      fileInput.files = emptyDataTransfer.files;
    });
  },

  removeFile: () => {
    document.addEventListener("click", (e) => {
      if (e.target.className !== "file-remove") return;
      const removeTargetId = e.target.dataset.index;
      const removeTargetNum = removeTargetId.replace("imgbox", "") * 1;
      let targetIndex = -1;
      const imgboxArr = document.getElementsByClassName("imgbox");

      let imgboxNumArr = new Array();

      Array.prototype.forEach.call(imgboxArr, (imgbox) => {
        console.log(imgbox);
        imgboxNumArr.push(imgbox.id.replace("imgbox", "") * 1);
      });

      imgboxNumArr.sort();

      for (let i = 0; i < imgboxNumArr.length; i++) {
        if (removeTargetNum == imgboxNumArr[i]) {
          targetIndex = i;
        }
      }

      const removeTarget = document.getElementById(removeTargetId);
      const files = document.getElementById("file-input-copy").files;

      const dataTranster = new DataTransfer();

      for (let i = 0; i < files.length; i++) {
        if (i == targetIndex) continue;
        dataTranster.items.add(files[i]);
      }

      document.getElementById("file-input-copy").files = dataTranster.files;

      // console.log(document.getElementById("file-input-copy").files);

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
  document.regItem.submit();
}
