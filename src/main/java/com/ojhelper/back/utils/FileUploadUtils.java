package com.ojhelper.back.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileUploadUtils {
    /**
     * 设置保存文件路径名
     *
     * @param pathName
     * @return
     */
    public static FileUploadUtils.Builder setPathName(String pathName) {
        return builder().setPathName(pathName);
    }

    /**
     * 设置允许上传文件大小
     *
     * @param maxSize
     * @return
     */
    public static FileUploadUtils.Builder setMaxSize(int maxSize) {
        return builder().setMaxSize(maxSize);
    }

    /**
     * 设置最大文件名长度
     *
     * @param maxFileNameLength
     * @return
     */
    public static FileUploadUtils.Builder setMaxFileNameLength(int maxFileNameLength) {
        return builder().setMaxFileNameLength(maxFileNameLength);
    }

    /**
     * 设置允许上传文件路径
     *
     * @param allowedExtension
     * @return
     */
    public static FileUploadUtils.Builder setAllowedExtension(String[] allowedExtension) {
        return builder().setAllowedExtension(allowedExtension);
    }

    public static FileUploadUtils.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        /**
         * 默认大小 50M
         */
        private long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;
        /**
         * 默认的文件名最大长度 100
         */
        private int DEFAULT_FILE_NAME_LENGTH = 100;
        /**
         * 允许上传文件的后缀名
         */
        private String[] DEFAULT_ALLOWED_EXTENSION = {};
        /**
         * 保存文件路径名
         */
        private String PATH_NAME;

        public FileUploadUtils.Builder setMaxSize(int maxSize) {
            this.DEFAULT_MAX_SIZE = maxSize * 1024 * 1024;
            return this;
        }

        public FileUploadUtils.Builder setMaxFileNameLength(int maxFileNameLength) {
            this.DEFAULT_FILE_NAME_LENGTH = maxFileNameLength;
            return this;
        }

        public FileUploadUtils.Builder setAllowedExtension(String[] allowedExtension) {
            this.DEFAULT_ALLOWED_EXTENSION = allowedExtension;
            return this;
        }

        public FileUploadUtils.Builder setPathName(String pathName) {
            this.PATH_NAME = pathName;
            return this;
        }

        /**
         * 上传
         * @param file
         * @return
         * @throws IOException
         */
        public String upload(MultipartFile file, String fileName) throws IOException {
            //校验
            preCheck(file);
            //上传
            fileName = new StringBuilder().append(fileName).append(".").append(getExtension(file)).toString();
            File desc = getAbsoluteFile(PATH_NAME, fileName);
            file.transferTo(desc);
            return fileName;
        }

        public String upload(MultipartFile file) throws IOException {
            //校验
            preCheck(file);
            //上传
            String fileName = extractFilename(file);
            File desc = getAbsoluteFile(PATH_NAME, fileName);
            file.transferTo(desc);
            return fileName;
        }

        private void preCheck(MultipartFile file) throws IOException{
            if (StringUtils.trimToNull(PATH_NAME) == null)
                throw new IOException("文件保存路径为空");
            String extensionFilename = getExtension(file);
            if (!ArrayUtils.isEmpty(DEFAULT_ALLOWED_EXTENSION) && !ArrayUtils.contains(DEFAULT_ALLOWED_EXTENSION, extensionFilename))
                throw new IOException("只允许" + ArrayUtils.toString(DEFAULT_ALLOWED_EXTENSION) + "文件上传");
            if (file.getSize() > DEFAULT_MAX_SIZE) throw new IOException("文件过大");
            if (file.getOriginalFilename().length() > DEFAULT_FILE_NAME_LENGTH) throw new IOException("文件名过长");
        }

        /**
         * 编码文件名
         */
        private final String extractFilename(MultipartFile file) {
            String filename = file.getOriginalFilename();
            String extension = getExtension(file);
            filename = DateFormatUtils.format(new Date(), "/yyyy/MM/dd") + "/" + encodingFilename(filename) + "." + extension;
            return filename;
        }

        /**
         * 编码文件名
         */
        private final String encodingFilename(String filename) {
            filename = filename.replace("_", " ");
            filename = DigestUtils.md5Hex(filename + System.nanoTime() + RandomStringUtils.randomNumeric(6));
            return filename;
        }

        /**
         * 获取文件名的后缀
         *
         * @param file 表单文件
         * @return 后缀名
         */
        public final String getExtension(MultipartFile file) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            return extension;
        }

        /**
         * 创建文件
         *
         * @param uploadDir
         * @param filename
         * @return
         * @throws IOException
         */
        private final File getAbsoluteFile(String uploadDir, String filename) throws IOException {
            File desc = new File(uploadDir + File.separator + filename);
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
            if (!desc.exists()) {
                desc.createNewFile();
            }
            return desc;
        }
    }
}
