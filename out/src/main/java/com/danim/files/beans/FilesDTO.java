package com.danim.files.beans;

public class FilesDTO {
    private String fnum; // 파일 번호
    private String itemnum; // 상품 번호(상품 이미지 저장시)
    private String boardnum; // 게시글 번호(게시글 이미지 저장시)
    private String qnanum; // 1:1 문의 번호(1 : 1 문의 이미지 저장시)
    private String originalFilename; // 파일 이름
    private String storedFileName; // 저장된 파일 이름
    private String ftype; // 파일의 타입
    private String fsize; // 파일 사이즈
    private String path; // 파일 경로

    public String getFnum() {
        return fnum;
    }

    public void setFnum(String fnum) {
        this.fnum = fnum;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getBoardnum() {
        return boardnum;
    }

    public void setBoardnum(String boardnum) {
        this.boardnum = boardnum;
    }

    public String getQnanum() {
        return qnanum;
    }

    public void setQnanum(String qnanum) {
        this.qnanum = qnanum;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FilesDTO{" +
                "fnum='" + fnum + '\'' +
                ", itemnum='" + itemnum + '\'' +
                ", boardnum='" + boardnum + '\'' +
                ", qnanum='" + qnanum + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                ", ftype='" + ftype + '\'' +
                ", fsize='" + fsize + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
