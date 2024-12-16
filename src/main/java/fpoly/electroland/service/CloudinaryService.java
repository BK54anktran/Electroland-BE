package fpoly.electroland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class to handle file uploads to Cloudinary.
 */
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    /**
     * Cloudinary object to interact with the Cloudinary API.
     */
    private final Cloudinary cloudinary;

    /**
     * Uploads multiple files to Cloudinary.
     *
     * @param files List of files to upload
     * @return List of URLs of the uploaded files
     * @throws IOException If an error occurs while uploading the files
     */
    @SuppressWarnings("unchecked")
    public List<String> uploadMultipleFiles(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.emptyMap());
                String url = uploadResult.get("url").toString();
                urls.add(url);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + file.getOriginalFilename());
                e.printStackTrace();
            }
        }
        return urls;
    }

    /**
     * Uploads a single file to Cloudinary.
     *
     * @param file File to upload
     * @return URL of the uploaded file
     * @throws IOException If an error occurs while uploading the file
     */
    @SuppressWarnings("rawtypes")
    public String uploadMultipleFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty. Please provide a valid file.");
        }

        try {
            // Upload file to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            // Extract and return the URL
            return StringUtils.defaultIfBlank(uploadResult.get("url").toString(), StringUtils.EMPTY);
        } catch (IOException e) {
            System.err.println("Error uploading file: " + file.getOriginalFilename());
            throw new IOException("Failed to upload file: " + e.getMessage(), e);
        }
    }
}