package com.danim.files.util;

import com.danim.files.beans.FilesEntity;

public class FilesParser {
    public FilesDTO parse(FilesEntity filesEntity){
        FilesDTO filesDTO = new FilesDTO();

        filesDTO.setFnum(filesEntity.getFnum());
        filesDTO.setItemnum(filesEntity.getItemnum());
    }
}
